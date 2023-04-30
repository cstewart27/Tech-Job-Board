import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class JobListingGUI extends JDialog{

    int currentJobListingID = JobListing.getCurrentJobListingID();
    JobListing jobListing = JobListing.RetrieveData(currentJobListingID);

    private JButton backButton;
    private JTextArea defaultJobDescriptionTextArea;
    private JPanel panel1;
    private JLabel defaultJobTitle;
    private JLabel defaultCompanyName;
    private JLabel defaultSalary;
    private JLabel DefaultJobLocation;
    private JLabel defaultSkill0;
    private JLabel defaultSkill1;
    private JLabel defaultSkill2;
    private JLabel defaultSkill3;
    private JLabel defaultSkill4;
    private JLabel defaultSkill5;
    private JLabel defaultSkill6;
    private JLabel defaultSkill7;
    private JButton deleteJobListingButton;


    public JobListingGUI(JFrame parent, int JobListingID) {
        super(parent);
        setTitle("Account Details");
        setContentPane(panel1);
        setMinimumSize(new Dimension(600,400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        defaultJobDescriptionTextArea.setEditable(false);

        //setting the job description to the user's current information
        defaultJobDescriptionTextArea.setText(jobListing.description);
        defaultJobTitle.setText(jobListing.title);

        try{
            Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
            Statement stmt = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Name FROM Companies WHERE CompanyID = ?");
            preparedStatement.setInt(1, jobListing.companyID);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                defaultCompanyName.setText(rs.getString("Name"));
            }

            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }

        defaultSalary.setText(String.valueOf(jobListing.salary));
        DefaultJobLocation.setText(jobListing.location);

        defaultSkill0.setText(jobListing.skills_required[0]);
        defaultSkill1.setText(jobListing.skills_required[1]);
        defaultSkill2.setText(jobListing.skills_required[2]);
        defaultSkill3.setText(jobListing.skills_required[3]);
        defaultSkill4.setText(jobListing.skills_required[4]);
        defaultSkill5.setText(jobListing.skills_required[5]);
        defaultSkill6.setText(jobListing.skills_required[6]);
        defaultSkill7.setText(jobListing.skills_required[7]);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //goes to user homepage
                if(Company.getCurrentCompanyID() == 1 && User.getCurrentUserID() > 1){
                    dispose();
                    HomePageGUI2 homePageGUI = new HomePageGUI2(null);
                }
                if(User.getCurrentUserID() == 1 && Company.getCurrentCompanyID() > 1){
                    dispose();
                    CompanyHomePageGUI companyHomePageGUI = new CompanyHomePageGUI();
                }
            }
        });

        deleteJobListingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(Company.getCurrentCompanyID() == jobListing.companyID && Company.getCurrentCompanyID() != 1){
                    dispose();
                    deleteJobListing(jobListing.jobListingID);
                }
                else{
                    JOptionPane.showMessageDialog(null, "You do not have permission to delete this job listing.");
                }

            }
        });

        setVisible(true);

    }

    public static void deleteJobListing(int currentJobListingID){
        try{
            Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
            Statement stmt = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM JobListing WHERE JobListingID = ?");
            preparedStatement.setInt(1, currentJobListingID);
            preparedStatement.executeUpdate();
            stmt.close();
            conn.close();
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }

        if(Company.getCurrentCompanyID() == 1 && User.getCurrentUserID() > 1){
            HomePageGUI2 homePageGUI = new HomePageGUI2(null);
        }
        if(User.getCurrentUserID() == 1 && Company.getCurrentCompanyID() > 1){
            CompanyHomePageGUI companyHomePageGUI = new CompanyHomePageGUI();
        }
    }
    public static void main(String[] args) {
        JobListingGUI jobListingGUI = new JobListingGUI(null, 1);


    }
}
