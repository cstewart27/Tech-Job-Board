import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HomePageGUI2 extends JFrame{
    int curreentUserID = User.getCurrentUserID();
    User user = User.RetrieveData(curreentUserID);
   /* private JPanel panel1;

    private JButton refreshButton;
    private JButton accountDetailsButton;
    //private JButton logOutButton;
    private JButton checkOutThisJobButton;
    private JButton checkOutThisJobButton1;
    private JButton checkOutThisJobButton2;
    private JButton checkOutThisJobButton3;
    private JButton checkOutThisJobButton4;
    private JLabel jobTitle1;
    private JLabel jobTitle2;
    private JLabel jobTitle3;
    private JLabel jobTitle4;
    private JLabel jobTitle5;
    private JLabel jobCompany1;
    private JLabel jobCompany2;
    private JLabel jobCompany3;
    private JLabel jobCompany4;
    private JLabel jobCompany5;
*/
    public HomePageGUI2() {

        setTitle("Tech Jobs");
        setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);


/*
        JobListing jobListing1 = JobListing.RetrieveData(1);
        jobTitle1.setText(jobListing1.title);
        Company company1 = Company.RetrieveData(jobListing1.companyID);
        jobCompany1.setText(company1.Name);

        JobListing jobListing2 = JobListing.RetrieveData(2);
        jobTitle2.setText(jobListing2.title);
        Company company2 = Company.RetrieveData(jobListing2.companyID);
        jobCompany2.setText(company2.Name);

        JobListing jobListing3 = JobListing.RetrieveData(3);
        jobTitle3.setText(jobListing3.title);
        Company company3 = Company.RetrieveData(jobListing3.companyID);
        jobCompany3.setText(company3.Name);

        JobListing jobListing4 = JobListing.RetrieveData(4);
        jobTitle4.setText(jobListing4.title);
        Company company4 = Company.RetrieveData(jobListing4.companyID);
        jobCompany4.setText(company4.Name);

        JobListing jobListing5 = JobListing.RetrieveData(5);
        jobTitle5.setText(jobListing5.title);
        Company company5 = Company.RetrieveData(jobListing5.companyID);
        jobCompany5.setText(company5.Name);

        checkOutThisJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                JobListingGUI jobListingGUI = new JobListingGUI(null, jobListing1.jobListingID);

            }
        });


        checkOutThisJobButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                JobListingGUI jobListingGUI = new JobListingGUI(null, jobListing2.jobListingID);

            }
        });

        checkOutThisJobButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                JobListingGUI jobListingGUI = new JobListingGUI(null, jobListing3.jobListingID);

            }
        });

        checkOutThisJobButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                JobListingGUI jobListingGUI = new JobListingGUI(null, jobListing4.jobListingID);

            }
        });

        checkOutThisJobButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                JobListingGUI jobListingGUI = new JobListingGUI(null, jobListing5.jobListingID);

            }
        });


*/

        // Create a panel for the menu bar and buttons
        JPanel menuPanel = new JPanel(new GridLayout(1, 3));
        JButton logOutButton = new JButton("Log Out");
        JButton jobListingButton = new JButton("Your Job Listings");
        JButton accountDetailsButton = new JButton("Account");
        menuPanel.add(logOutButton);
        menuPanel.add(jobListingButton);
        menuPanel.add(accountDetailsButton);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(600, 400));
        panel.setMinimumSize(new Dimension(600, 400));
        panel.setMaximumSize(new Dimension(600, 400));


        jobListingButton.addActionListener(e -> {
            try{
                Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM JobListing");
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    String buttonText = rs.getString("Title");
                    int jobListingID = rs.getInt("JobListingID");
                    JButton jobListingButton1 = new JButton(buttonText);
                    jobListingButton1.addActionListener(e1 -> {
                        dispose();
                        JobListing.setCurrentJobListingID(jobListingID);
                        JobListingGUI jobListingGUI = new JobListingGUI(null, jobListingID);
                    });

                    jobListingButton1.setPreferredSize(new Dimension(150, 40));
                    jobListingButton1.setMinimumSize(new Dimension(150, 40));
                    jobListingButton1.setMaximumSize(new Dimension(150, 40));
                    jobListingButton1.setMargin(new Insets(20, 20, 20, 20));
                    jobListingButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
                    panel.add(jobListingButton1);
                    getContentPane().add(panel);
                    pack();
                    setLocationRelativeTo(null);
                    System.out.println("Added button " + buttonText);
                    setVisible(true);
                }

                rs.close();
                preparedStatement.close();
                conn.close();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });


        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SignIn signIn = new SignIn(null);
            }
        });

        accountDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountDetails accountDetails = new AccountDetails(null);
            }
        });


        // Add the panels to the frame
        //add(welcomePanel, BorderLayout.CENTER);
        add(menuPanel, BorderLayout.NORTH);

        // Set the background color of the welcome panel
        //welcomePanel.setBackground(Color.yellow);
        repaint();

        //note its not DISPOSE_ON_CLOSE, but EXIT_ON_CLOSE since the HomePageGUI is made differently from other GUIs
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Set the frame to be visible
        setVisible(true);
    }

    public static void main(String[] args) {
        HomePageGUI2 homePageGUI2 = new HomePageGUI2();
    }

}
