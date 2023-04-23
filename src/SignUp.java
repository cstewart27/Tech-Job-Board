import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignUp extends JDialog {
    private JPanel panel1;
    private JTextField textFieldEmail;
    private JPasswordField passwordField1;
    private JPasswordField passwordConfirm;
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;
    private JButton registerButton;
    private JButton signInButton;


    public SignUp(JFrame parent) {
        super(parent);
        setTitle("Sign Up");
        setContentPane(panel1);
        setMinimumSize(new Dimension(1440,1024));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        setVisible(true);

    }

    private void registerUser() {
        String email = textFieldEmail.getText();
        String password = passwordField1.getText();
        String passwordConfirm2 = passwordConfirm.getText();
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();

        if (email.equals("") || password.equals("") || passwordConfirm2.equals("") || firstName.equals("") || lastName.equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields");
        } else {
            if (password.equals(passwordConfirm2)) {
                //check if username already exists
                //check if email already exists
                //check if phone already exists
                //if all are false, then create account
                //else, display error message

                String url = "jdbc:mysql://localhost:3306/JobListingDatabase";
                String usernameToDatabase = "root";
                String passwordToDatabase = "1723";
                try {
                    DatabaseConnectionManager.connect(url, usernameToDatabase, passwordToDatabase);
                    Connection con = DatabaseConnectionManager.getConnection();
                    Statement stmt = con.createStatement();
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

    public User user;

    private User addUsertoDatabase(int id, String FirstName, String LastName, String email, String password) {
        //add user to database
        User user = null;
        final String url = "jdbc:mysql://localhost:3306/JobListingDatabase";
        final String usernameToDatabase = "root";
        final String passwordToDatabase = "1723";
        try {
            DatabaseConnectionManager.connect(url, usernameToDatabase, passwordToDatabase);
            Connection con = DatabaseConnectionManager.getConnection();
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO Candidates (CandidateID, FirstName, LastName, Email, Password)" + "VALUES( ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            preparedStmt.setInt(1, id);
            preparedStmt.setString(2, FirstName);
            preparedStmt.setString(3, LastName);
            preparedStmt.setString(4, email);
            preparedStmt.setString(5, password);
            int addedRows = preparedStmt.executeUpdate();
            if (addedRows > 0) {
                System.out.println("User added to database");
                user = new User();
                user.CandidateID = id;
                user.FirstName = FirstName;
                user.LastName = LastName;
                user.Email = email;
                user.Password = password;
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
