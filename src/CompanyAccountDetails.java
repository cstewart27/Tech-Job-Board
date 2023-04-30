import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompanyAccountDetails extends JDialog{

    int currentCompanyID = Company.getCurrentCompanyID();
    Company company = Company.RetrieveData(currentCompanyID);

    private JPanel panel1;
    private JLabel emailLabel;
    private JTextField defaultCompanyDescriptionTextField;
    private JButton updateAccountDetailsButton;
    private JButton backButton;
    private JTextField defaultPhoneNumberTextField;
    private JTextField defaultCompanyLocationTextField;
    private JButton deleteAccountButton;

    public CompanyAccountDetails(JFrame parent){
        super(parent);
        setTitle("Company Account Details");
        setContentPane(panel1);
        setMinimumSize(new Dimension(1440,1024));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //setting the email label to the company's current information
        emailLabel.setText(company.Email);

        //setting the phone number to the company's current information
        defaultPhoneNumberTextField.setText(company.Phone);

        //setting the company location to the company's current information
        defaultCompanyLocationTextField.setText(company.Location);

        //setting the company description to the company's current information
        defaultCompanyDescriptionTextField.setText(company.Description);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CompanyHomePageGUI CompanyHomePageGUI = new CompanyHomePageGUI();
            }
        });

        updateAccountDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //update the company's information
                company.Phone = defaultPhoneNumberTextField.getText().trim();
                company.Location = defaultCompanyLocationTextField.getText().trim();
                company.Description = defaultCompanyDescriptionTextField.getText().trim();
                //validate phone number
                if(!validatePhoneNumber(company.Phone)){
                    JOptionPane.showMessageDialog(null, "Invalid phone number");
                    return;
                }
                UpdateData(company);

            }
        });

        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Company.deleteAccount(Company.getCurrentCompanyID());
            }
        });

        setVisible(true);

    }

    public Company UpdateData(Company company){
        //update the company's information
        company.Phone = defaultPhoneNumberTextField.getText().trim();
        company.Location = defaultCompanyLocationTextField.getText().trim();
        company.Description = defaultCompanyDescriptionTextField.getText().trim();

        try{
            Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
            Statement stmt = conn.createStatement();
            String sql = "UPDATE Companies SET Phone = ?, Location = ?, Description = ? WHERE CompanyID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, company.Phone);
            pst.setString(2, company.Location);
            pst.setString(3, company.Description);
            pst.setInt(4, company.CompanyID);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Company Account Details Updated");
            System.out.println("Company Account Details Updated");

            conn.close();
            stmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }


        return company;
    }

    //validates the user's phone number
    public boolean validatePhoneNumber(String phoneNumber){

        String PHONE_REGEX = "^\\(?([2-9][0-8][0-9])\\)?[-.●]?([2-9][0-9]{2})[-.●]?([0-9]{4})$";
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    public static void main(String[] args) {
        CompanyAccountDetails CompanyAccountDetails = new CompanyAccountDetails(null);
    }

}
