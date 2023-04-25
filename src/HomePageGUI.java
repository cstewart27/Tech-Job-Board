import javax.swing.*;
import java.awt.*;

public class HomePageGUI extends JFrame {

    // Constructor
    public HomePageGUI() {

        // Set the title and size of the frame
        setTitle("Tech Job Portal");
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
        JButton jobListingButton = new JButton("Your Job Listing");
        JButton refreshButton = new JButton("Refresh Feed");
        JButton createPostingButton = new JButton("Create Job Posting");
        JButton accountButton = new JButton("Account");
        menuPanel.add(jobListingButton);
        menuPanel.add(refreshButton);
        menuPanel.add(createPostingButton);
        menuPanel.add(accountButton);

        // Add the panels to the frame
        add(welcomePanel, BorderLayout.CENTER);
        add(menuPanel, BorderLayout.NORTH);

        // Set the background color of the welcome panel
        welcomePanel.setBackground(Color.yellow);
        repaint();
        // Set the frame to be visible
        setVisible(true);
    }

    // Main method to run the program
    public static void main(String[] args) {

        HomePageGUI homepage = new HomePageGUI();
    }
}