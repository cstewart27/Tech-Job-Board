import javax.swing.*;
import java.awt.*;

public class CompanyHomePageGUI extends JFrame {

    int currentCompanyID = Company.getCurrentCompanyID();
    Company company = Company.RetrieveData(currentCompanyID);


    // Constructor
    public CompanyHomePageGUI() {
        // Set the title and size of the frame
        setTitle("Tech Job Portal for Companies");
        setSize(600, 400);

        // Create a panel for the welcome message
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel welcomeLabel = new JLabel("Welcome to Tech Job Portal");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        welcomePanel.add(welcomeLabel, c);

        // Create a panel for the menu bar and buttons
        JPanel menuPanel = new JPanel(new GridLayout(1, 4));
        JButton logOutButton = new JButton("Log Out");
        JButton jobListingButton = new JButton("Your Job Listings");
        JButton createPostingButton = new JButton("Create Job Posting");
        JButton accountButton = new JButton("Account");
        menuPanel.add(logOutButton);
        menuPanel.add(jobListingButton);
        menuPanel.add(createPostingButton);
        menuPanel.add(accountButton);

        logOutButton.addActionListener(e -> {
            Company.setCurrentCompanyID(1);
            dispose();
            CompanySignIn companySignIn = new CompanySignIn(null);
        });

        accountButton.addActionListener(e -> {

            System.out.println(company.CompanyID);
            System.out.println(company.Email);
            System.out.println(company.Password);
            System.out.println(company.Phone);
            System.out.println(company.Description);
            System.out.println(company.Location);

            dispose();
            CompanyAccountDetails companyAccountDetails = new CompanyAccountDetails(null);
        });

        // Add the panels to the frame
        add(welcomePanel, BorderLayout.CENTER);
        add(menuPanel, BorderLayout.NORTH);

        // Set the background color of the welcome panel
        welcomePanel.setBackground(Color.yellow);
        repaint();

        //note its not DISPOSE_ON_CLOSE, but EXIT_ON_CLOSE since the HomePageGUI is made differently from other GUIs
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Set the frame to be visible
        setVisible(true);
    }

    // Main method to run the program
    public static void main(String[] args) {

        CompanyHomePageGUI homepage = new CompanyHomePageGUI();
    }
}