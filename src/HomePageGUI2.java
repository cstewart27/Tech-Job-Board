import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePageGUI2 extends JDialog{
    private JPanel panel1;

    private JButton refreshButton;
    private JButton accountDetailsButton;
    private JButton logOutButton;
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

    public HomePageGUI2(JFrame parent) {
        super(parent);
        setTitle("Account Details");
        setContentPane(panel1);
        setMinimumSize(new Dimension(1440,1024));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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

        setVisible(true);
    }

    public static void main(String[] args) {
        HomePageGUI2 homePageGUI2 = new HomePageGUI2(null);
    }

}
