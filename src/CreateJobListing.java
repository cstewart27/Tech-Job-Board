import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CreateJobListing extends JDialog{
    private JButton backButton;
    private JTextField jobTitleTextField;
    private JTextArea jobDescriptionTextArea;
    private JTextField jobLocationTextField;
    private JTextField jobSalaryTextField;
    private JTextField jobCategoryTextField;
    private JTextField jobUrgencyTextField;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JButton createPostingButton;
    private JPanel panel1;

    public CreateJobListing(JFrame parent) {

        super(parent);
        setTitle("Account Details");
        setContentPane(panel1);
        setMinimumSize(new Dimension(600,400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CompanyHomePageGUI companyHomePageGUI = new CompanyHomePageGUI();
            }
        });
        createPostingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JobListing jobListing = new JobListing();
                try{
                    Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
                    String sql = "SELECT Max(JobListingID) FROM JobListing";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                       jobListing.jobListingID = rs.getInt(1) + 1;
                    }
                    rs.close();
                    stmt.close();
                    conn.close();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

                jobListing.title = jobTitleTextField.getText().trim();
                jobListing.description = jobDescriptionTextArea.getText().trim();
                jobListing.location = jobLocationTextField.getText().trim();
                try{
                    jobListing.salary = Integer.parseInt(jobSalaryTextField.getText().trim());
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Salary must be an integer");
                    ex.printStackTrace();
                    return;
                }
                jobListing.category = jobCategoryTextField.getText().trim();
                jobListing.Urgency = jobUrgencyTextField.getText().trim();
                jobListing.companyID = Company.getCurrentCompanyID();
                jobListing.skills_required[0] = textField1.getText().trim();
                jobListing.skills_required[1] = textField2.getText().trim();
                jobListing.skills_required[2] = textField3.getText().trim();
                jobListing.skills_required[3] = textField4.getText().trim();
                jobListing.skills_required[4] = textField5.getText().trim();
                jobListing.skills_required[5] = textField6.getText().trim();
                jobListing.skills_required[6] = textField7.getText().trim();
                jobListing.skills_required[7] = textField8.getText().trim();
                String insertSkills = "";
                for(int i = 0; i < jobListing.skills_required.length; i++){
                    if(jobListing.skills_required[i].equals("")){

                    }
                    else{
                        insertSkills += jobListing.skills_required[i] + ",";
                    }
                }

                try{
                    Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
                    String sql = "INSERT INTO JobListing VALUES(?,?,?,?,?,?,?,?,?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, jobListing.jobListingID);
                    stmt.setInt(2, jobListing.companyID);
                    stmt.setString(3, jobListing.title);
                    stmt.setString(4, jobListing.description);
                    stmt.setString(5, jobListing.location);
                    stmt.setInt(6, jobListing.salary);
                    stmt.setString(7, jobListing.category);
                    stmt.setString(8, jobListing.Urgency);
                    stmt.setString(9, insertSkills);
                    stmt.executeUpdate();
                    stmt.close();
                    conn.close();
                    JOptionPane.showMessageDialog(null, "Job Listing Created");
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Job Listing Creation Failed");
                    ex.printStackTrace();
                }

            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        CreateJobListing createJobListing = new CreateJobListing(null);
    }
}
