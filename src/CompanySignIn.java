import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Locale;

public class CompanySignIn extends JDialog{
    private JTextField textFieldEmail;
    private JPasswordField passwordTextField;
    private JButton signInButton;
    private JButton signUpButton;
    private JButton userSignInButton;
    private JPanel loginPanel;

    public Company company;

    public CompanySignIn(JFrame parent) {
        super(parent);
        setTitle("Company Sign In");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(1440,1024));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = textFieldEmail.getText().toLowerCase().trim();
                String password = String.valueOf(passwordTextField.getPassword()).trim();

                //getting the user from the database
                Company company = getAuthenticatedCompany(email, password);

                //if the user is not null, then the user is authenticated
                if(company != null){
                    dispose();
                    Company.setCurrentCompanyID(company.CompanyID);
                    CompanyHomePageGUI homepage = new CompanyHomePageGUI();
                }
                else{
                    JOptionPane.showMessageDialog(CompanySignIn.this, "Email or Password Invalid","Try Again", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CompanySignUp signUp = new CompanySignUp(null);
            }
        });

        userSignInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SignIn signIn = new SignIn(null);
            }
        });

        setVisible(true);
    }

    private Company getAuthenticatedCompany(String email, String password) {
        Company company = null;
        try{
            Connection con = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
            Statement stmt = con.createStatement();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Companies WHERE Email = ? AND Password = ?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                company = new Company();
                company.CompanyID = rs.getInt("CompanyID");
                company.Name = rs.getString("Name").trim();
                company.Phone = rs.getString("Phone").trim();
                company.Email = rs.getString("Email").trim();
                company.Password = rs.getString("Password").trim();
                company.Location = rs.getString("Location").trim();
                company.Description = rs.getString("Description").trim();
            }

            stmt.close();
            con.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return company;
    }

    public static void main(String[] args) {
        CompanySignIn companySignIn = new CompanySignIn(null);
        Company company = companySignIn.company;
        if(company != null){
            System.out.println("Successfull authentication of: " + company.Name);
        }
        else{
            System.out.println("Authentication cancelled");
        }

    }
}
