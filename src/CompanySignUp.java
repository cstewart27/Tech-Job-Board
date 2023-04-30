import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Locale;

public class CompanySignUp extends JDialog{
    private JTextField companyNameTextField;
    private JTextField companyEmailAddress;
    private JPasswordField confirmPasswordTextField;
    private JPasswordField passwordTextField;
    private JButton registerButton;
    private JButton signInButton;
    private JButton userRegistrationButton;
    private JPanel panel1;

    //initialize the company object will occur in addCompanytoDatabase method
    public Company company;


    public CompanySignUp(JFrame parent) {
        super(parent);
        setTitle("Company Sign Up");
        setContentPane(panel1);
        setMinimumSize(new Dimension(600,400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //if the user clicks the register button, then the registerUser method will be called
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerCompany();

            }
        });

        //if the user clicks the sign in button, then the sign in window will open
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CompanySignIn signIn = new CompanySignIn(null);

            }
        });

        //if the user clicks the register as company button, then the register as company window will open
        userRegistrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SignUp userSignUp = new SignUp(null);

            }
        });

        setVisible(true);

    }

    private void registerCompany() {
        String email = companyEmailAddress.getText().toLowerCase().trim();
        String password = passwordTextField.getText().trim();
        String confirmPassword = confirmPasswordTextField.getText().trim();
        String companyName = companyNameTextField.getText().trim();


        if(email.equals("") || password.equals("") || confirmPassword.equals("") || companyName.equals("")){
            JOptionPane.showMessageDialog(null, "Please fill out all fields");

        }else {
            if (password.equals(confirmPassword)){
                //check if email already exists in database

                try{
                    Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
                    Statement stmt = conn.createStatement();
                    String sql = "SELECT * FROM Companies WHERE Email = ?";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, email);
                    ResultSet rs = preparedStatement.executeQuery();
                    if(rs.next()){
                        JOptionPane.showMessageDialog(null, "Email already exists");
                        return;
                    }

                    rs.close();
                    stmt.close();
                    conn.close();

                }
                catch (SQLException e){
                    throw new RuntimeException(e);
                }

                try{
                    Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
                    Statement stmt = conn.createStatement();

                    String sql = "SELECT MAX(CompanyID) FROM Companies";
                    ResultSet rs = stmt.executeQuery(sql);
                    if(rs.next()){
                        int maxID = rs.getInt(1) + 1;
                        company = addCompanyToDatabase(maxID, companyName, email, password);
                    }

                    rs.close();
                    stmt.close();
                    conn.close();

                    dispose();
                    Company.setCurrentCompanyID(company.CompanyID);
                    //CompanyHomePageGUI companyHomePageGUI = new CompanyHomePageGUI(null);
                }
                catch (SQLException e){
                    throw new RuntimeException(e);
                }
                return;
            }
            else {
                JOptionPane.showMessageDialog(null, "Passwords do not match");
                return;
            }
        }
    }

    private Company addCompanyToDatabase(int maxID, String companyName, String email, String password) {
        Company company = null;

        try{
            Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Companies (CompanyID, Name, Email, Password) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, maxID);
            preparedStatement.setString(2, companyName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            int addRows = preparedStatement.executeUpdate();
            if(addRows > 0){
                //initialize company object
                //add company properties to company object
                System.out.println("Company added to database");
                company = new Company();
                company.CompanyID = maxID;
                company.Name = companyName;
                company.Email = email;
                company.Password = password;

                //add to default values to company object to avoid null pointer expection
                company.Phone = "Unassigned Phone";
                company.Location = "Unassigned Location";
                company.Description = "Unassigned Description";

                //update the database with the new company properties
                String sqlQuery = "UPDATE Companies SET Phone = ?, Location = ?, Description = ? WHERE CompanyID = ?";
                PreparedStatement preparedStatement2 = conn.prepareStatement(sqlQuery);
                preparedStatement2.setString(1, company.Phone);
                preparedStatement2.setString(2, company.Location);
                preparedStatement2.setString(3, company.Description);
                preparedStatement2.setInt(4, company.CompanyID);
                preparedStatement2.executeUpdate();

            }

            stmt.close();
            conn.close();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return company;

    }

    public static void main(String[] args) {
        CompanySignUp companySignUp = new CompanySignUp(null);
        Company company = companySignUp.company;
        if(company != null){
            System.out.println("Successfully registration of company");
        }
        else {
            System.out.println("Registration cancelled");
        }
    }
}
