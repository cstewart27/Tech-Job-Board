import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Locale;

public class SignUp extends JDialog {
    private JPanel panel1;
    private JTextField textFieldEmail;
    private JPasswordField passwordField1;
    private JPasswordField passwordConfirm;
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;
    private JButton registerButton;
    private JButton signInButton;

    //initialize the user object will occur in addUsertoDatabase method
    public User user;



    public SignUp(JFrame parent) {
        super(parent);
        setTitle("Sign Up");
        setContentPane(panel1);
        setMinimumSize(new Dimension(1440,1024));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //if the user clicks the register button, then the registerUser method will be called
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();

            }
        });

        //if the user clicks the sign in button, then the sign in window will open
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SignIn signIn = new SignIn(null);

            }
        });

        setVisible(true);

    }

    private void registerUser() {
        String email = textFieldEmail.getText().toLowerCase();
        String password = passwordField1.getText();
        String passwordConfirm2 = passwordConfirm.getText();
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();

        //if any of the fields are empty, then display error message
        if (email.equals("") || password.equals("") || passwordConfirm2.equals("") || firstName.equals("") || lastName.equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields");
        } else {
            if (password.equals(passwordConfirm2)) {

                //check if the email already exists in the database
                try{
                    Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
                    Statement stmt = conn.createStatement();
                    String sql = "SELECT * FROM Candidates WHERE Email = ?";
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

                try {
                    Connection con = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
                    Statement stmt = con.createStatement();

                    //used to auto increment the primary key rather than editing the database
                    String sql = "SELECT MAX(CandidateID) FROM Candidates";
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        int maxId = rs.getInt(1) + 1;
                        System.out.println("The highest primary key value is: " + maxId);
                        user = addUsertoDatabase(maxId, firstName, lastName, email, password);

                    }
                    rs.close();
                    stmt.close();
                    con.close();

                    dispose();
                    User.setCurrentUserID(user.CandidateID);
                    HomePageGUI homepage = new HomePageGUI();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return;
            } else {
                JOptionPane.showMessageDialog(null, "Passwords do not match");
                return;
            }
        }

    }


    private User addUsertoDatabase(int id, String FirstName, String LastName, String email, String password) {
        //add user to database
        User user = null;

        try {
            Connection con = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO Candidates (CandidateID, FirstName, LastName, Email, Password)" + "VALUES( ?, ?, ?, ?, ?)";

            //prepared statement allows for the use of variables in the sql statement
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            preparedStmt.setInt(1, id);
            preparedStmt.setString(2, FirstName);
            preparedStmt.setString(3, LastName);
            preparedStmt.setString(4, email);
            preparedStmt.setString(5, password);
            int addedRows = preparedStmt.executeUpdate();
            if (addedRows > 0) {
                //initialise user object
                //add user properties to user object
                System.out.println("User added to database");
                user = new User();
                user.CandidateID = id;
                user.FirstName = FirstName;
                user.LastName = LastName;
                user.Email = email;
                user.Password = password;


                //add to default values to user object to avoid null pointer exception
                user.Location = "Unassigned Location";
                user.Desired_Salary = 0;
                user.Phone = "Unassigned Phone";
                user.Skills = new String[7];

                String skillString = "";
                for(int i = 0; i< user.Skills.length; i++){
                    user.Skills[i] = "Empty Skill";
                    skillString += user.Skills[i] + ",";
                }


                //update the database with the new user properties
                String sqlQuery = "update Candidates set Location = ?, Desired_Salary = ?, Phone = ?, Skills = ? where CandidateID = ?";
                PreparedStatement preparedStmt2 = con.prepareStatement(sqlQuery);
                preparedStmt2.setString(1, user.Location);
                preparedStmt2.setInt(2, user.Desired_Salary);
                preparedStmt2.setString(3, user.Phone);
                preparedStmt2.setString(4, skillString);
                preparedStmt2.setInt(5, user.CandidateID);
                preparedStmt2.executeUpdate();


            }

            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public static void main(String[] args) {
        SignUp signUp = new SignUp(null);
        User user = signUp.user;
        if (user != null) {
            System.out.println("Successfull registration of: " + user.FirstName + " " + user.LastName);
        }
        else{
            System.out.println("Registration cancelled");
        }


    }
}
