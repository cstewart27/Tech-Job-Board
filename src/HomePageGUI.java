import javax.swing.*;
import java.awt.*;

public class HomePageGUI extends JFrame {

   int currentUserID = User.getCurrentUserID();
    User user = User.RetrieveData(currentUserID);

    // Constructor
    public HomePageGUI() {
        // Set the title and size of the frame
        setTitle("Tech Job Portal");
        setSize(600, 400);

        // Create a panel for the welcome message

        JPanel welcomePanel = new JPanel(new GridBagLayout());
        // Create a new GridBagConstraints object to specify the layout parameters
        GridBagConstraints c = new GridBagConstraints();
        // Create a new JLabel to display the welcome message
        JLabel welcomeLabel = new JLabel("Welcome to Tech Job Portal");
        // Set the font for the welcome message
        welcomeLabel.setFont(new Font("Ariel Black", Font.BOLD,30));
        // Set the color for the welcome message text
        welcomeLabel.setForeground(Color.BLACK);
        c.gridx = 0; // Set the column index for the welcome message in the grid
        c.gridy = 1; // Set the row index for the welcome message in the grid
        c.weightx = 1; // Set the horizontal weight for the welcome message
        c.weighty = 1; // Set the vertical weight for the welcome message
        c.anchor = GridBagConstraints.NORTH;// Set the anchor position for the welcome message
        // Add the welcome message to the panel with the specified constraints
        welcomePanel.add(welcomeLabel, c);
        // Set the background color for the panel
        welcomePanel.setBackground(new Color(149,197,198));



        // Create a panel for the menu bar and buttons
        JPanel menuPanel = new JPanel(new GridLayout(1, 3));
        JButton logOutButton = new JButton("Log Out");
        logOutButton.setFont(new Font("Courier New",Font.BOLD,12));
        logOutButton.setBackground(new Color(105,188,255));
        JButton refreshButton = new JButton("Refresh Feed");
        refreshButton.setFont(new Font("Courier New",Font.BOLD,12));
        refreshButton.setBackground(new Color(105,188,255));
        JButton accountButton = new JButton("Account");
        accountButton.setFont(new Font("Courier New",Font.BOLD,12));
        accountButton.setBackground(new Color(105,188,255));
        menuPanel.add(logOutButton);
        menuPanel.add(refreshButton);
        menuPanel.add(accountButton);

        logOutButton.addActionListener(e -> {
            User.setCurrentUserID(1);
            dispose();
            SignIn signIn = new SignIn(null);
        });

        accountButton.addActionListener(e -> {
            System.out.println(user.FirstName + " " + user.LastName);
            System.out.println(user.Email);
            System.out.println(user.Password);
            System.out.println(user.Phone);
            System.out.println(user.Desired_Salary);
            System.out.println(user.Location);
            for(int i = 0; i < user.Skills.length; i++){
                System.out.println("Job Skills " + (i+1) + ": " + user.Skills[i]);
            }
            dispose();
            AccountDetails accountDetails = new AccountDetails(null);
        });

        // Add the panels to the frame
        add(welcomePanel, BorderLayout.CENTER);
        add(menuPanel, BorderLayout.NORTH);

        // Set the background color of the welcome panel


        //note its not DISPOSE_ON_CLOSE, but EXIT_ON_CLOSE since the HomePageGUI is made differently from other GUIs
        setDefaultCloseOperation(EXIT_ON_CLOSE);
setVisible(true);

    }

    // Main method to run the program
    public static void main(String[] args) {

        HomePageGUI homepage = new HomePageGUI();

    }
}