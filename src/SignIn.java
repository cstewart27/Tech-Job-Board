import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignIn extends JDialog{
    private JPasswordField passwordField1;
    private JButton signInButton;
    private JButton signUpButton;
    private JTextField textFieldEmail;
    private JPanel loginPanel;
    private JButton companySignInButton;

    public User user;


    public SignIn(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(1440,1024));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        signInButton.addActionListener(new ActionListener() {

            //getting the user's email and password from the text fields
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = textFieldEmail.getText();
                String password = String.valueOf(passwordField1.getPassword());

                //getting the user from the database
                user = getAuthenticatedUser(email, password);

                //if the user is not null, then the user is authenticated
                if(user != null){
                    dispose();
                    User.setCurrentUserID(user.CandidateID);
                    HomePageGUI homepage = new HomePageGUI();
                }
                else{
                    JOptionPane.showMessageDialog(SignIn.this, "Email or Password Invalid","Try Again", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //if the user clicks the sign up button, then the sign up window will open
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                SignUp signUp = new SignUp(null);

            }
        });

        companySignInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CompanySignIn companySignIn = new CompanySignIn(null);
            }
        });
        setVisible(true);

    }


    private User getAuthenticatedUser(String email, String password){
        User user = null;

        try{
            Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);

            Statement stmt = conn.createStatement();
            String sql = "select * from Candidates where Email=? AND Password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = new User();
                user.CandidateID = resultSet.getInt("CandidateID");
                user.FirstName = resultSet.getString("FirstName");
                user.LastName = resultSet.getString("LastName");
                user.Email = resultSet.getString("Email");
                user.Password = resultSet.getString("Password");
                user.Phone = resultSet.getString("Phone");
                user.Desired_Salary = resultSet.getInt("Desired_Salary");
                user.Location = resultSet.getString("Location");
            }

            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
        SignIn signIn = new SignIn(null);
        User user = signIn.user;

        if(user != null){
            System.out.println("Successfull authentication of: " + user.FirstName + " " + user.LastName);
        }
        else{
            System.out.println("Authentication cancelled");
        }


    }
}

