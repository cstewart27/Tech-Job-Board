import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AccountDetails extends JDialog {

    int currentUserID = User.getCurrentUserID();
    User user = User.RetrieveData(currentUserID);


    private JTextField skill0TextField;
    private JTextField skill1TextField;
    private JTextField skill2TextField;
    private JTextField skill3TextField;
    private JTextField skill4TextField;
    private JTextField skill5TextField;
    private JTextField skill6TextField;
    private JTextField skill7TextField;
    private JButton updateAccountDetailsButton;
    private JTextField defaultDesiredSalaryTextField;
    private JButton backButton;
    private JTextField defaultJobLocationTextField;
    private JPanel panel1;
    private JTextField defaultPhoneNumberTextField;
    private JLabel emailLabel;

    public AccountDetails(JFrame parent) {

        super(parent);
        setTitle("Account Details");
        setContentPane(panel1);
        setMinimumSize(new Dimension(1440,1024));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //setting the email label to the user's current information
        emailLabel.setText(user.Email);


        //setting the desired salary to the user's current information
        defaultDesiredSalaryTextField.setText(Integer.toString(user.Desired_Salary));

        //setting the job location to the user's current information
        defaultJobLocationTextField.setText(user.Location);

        //setting the phone number to the user's current information
        defaultPhoneNumberTextField.setText(user.Phone);

        //Setting skills textfields
        //note the user can edit them if they want, but must press update account details button for it to be saved
        skill0TextField.setText(user.Skills[0]);
        skill1TextField.setText(user.Skills[1]);
        skill2TextField.setText(user.Skills[2]);
        skill3TextField.setText(user.Skills[3]);
        skill4TextField.setText(user.Skills[4]);
        skill5TextField.setText(user.Skills[5]);
        skill6TextField.setText(user.Skills[6]);
        skill7TextField.setText(user.Skills[7]);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                HomePageGUI homePageGUI = new HomePageGUI();
            }
        });
        updateAccountDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //update the user's desired salary
                user.Desired_Salary = Integer.parseInt(defaultDesiredSalaryTextField.getText().trim());

                //update the user's job location
                user.Location = defaultJobLocationTextField.getText().trim();

                //update the user's skills
                user.Skills[0] = skill0TextField.getText().trim();
                user.Skills[1] = skill1TextField.getText().trim();
                user.Skills[2] = skill2TextField.getText().trim();
                user.Skills[3] = skill3TextField.getText().trim();
                user.Skills[4] = skill4TextField.getText().trim();
                user.Skills[5] = skill5TextField.getText().trim();
                user.Skills[6] = skill6TextField.getText().trim();
                user.Skills[7] = skill7TextField.getText().trim();

                //update the user's phone number
                user.Phone = defaultPhoneNumberTextField.getText().trim();

                //update the user's information in the database
                UpdateData(user);

            }
        });

        setVisible(true);

    }

    public User UpdateData(User user) {
        String insertSkills = "";
        for(int i = 0; i < user.Skills.length; i++){
            insertSkills += user.Skills[i] + ",";
        }


        try{
            Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
            Statement stmt = conn.createStatement();
            String sql = "update Candidates set Desired_Salary = ?, Location = ?, Skills = ?, Phone = ? where CandidateID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, user.Desired_Salary);
            preparedStatement.setString(2, user.Location);
            preparedStatement.setString(3, insertSkills);
            preparedStatement.setString(4, user.Phone);
            preparedStatement.setInt(5, user.CandidateID);

            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Updated user information");
            System.out.println("Updated user information");

            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }



        return user;
    }
    public static void main(String[] args) {
        AccountDetails accountDetails = new AccountDetails(null);
    }
}
