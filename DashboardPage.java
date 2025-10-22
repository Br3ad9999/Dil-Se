import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardPage extends JFrame {
    
    // 1. ADD FIELD FOR USER ID
    private final int userId; 

    // ORIGINAL CONSTRUCTOR (kept for backward compatibility, but won't work correctly with login flow)
    public DashboardPage() {
        // Default to a test user ID (1) if called without the login flow
        this(1); 
    }

    // 2. NEW CONSTRUCTOR for Session Management
    public DashboardPage(int userId) {
        this.userId = userId; // Store the authenticated user ID

        setTitle("Dashboard - DIL SE (User ID: " + userId + ")"); // Show ID for debugging
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 247, 248)); // --bg approx

        // Navbar panel (similar to other pages)
        JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navbar.setBackground(Color.white);
        JLabel logo = new JLabel("DIL ");
        logo.setFont(new Font("Playfair Display", Font.BOLD, 28));
        JLabel accentSE = new JLabel("SE");
        accentSE.setForeground(new Color(242, 198, 201)); // --accent color from your CSS
        accentSE.setFont(new Font("Playfair Display", Font.BOLD, 28));
        navbar.add(logo);
        navbar.add(accentSE);

        JButton homeBtn = new JButton("Home");
        homeBtn.setFocusPainted(false);
        homeBtn.setBackground(Color.white);
        homeBtn.setBorderPainted(false);
        navbar.add(homeBtn);

        JButton dashboardBtn = new JButton("Dashboard");
        dashboardBtn.setFocusPainted(false);
        dashboardBtn.setBackground(Color.white);
        dashboardBtn.setBorderPainted(false);
        navbar.add(dashboardBtn);

        JButton chatBtn = new JButton("Chat");
        chatBtn.setFocusPainted(false);
        chatBtn.setBackground(Color.white);
        chatBtn.setBorderPainted(false);
        navbar.add(chatBtn);

        // 3. CHANGE 'Login' BUTTON TEXT TO 'Log Out'
        JButton loginBtn = new JButton("Log Out"); // Renamed text for visual consistency
        loginBtn.setFocusPainted(false);
        loginBtn.setBackground(Color.white);
        loginBtn.setBorderPainted(false);
        navbar.add(loginBtn);

        // Add Matchmaker button to navbar
        JButton matchmakerBtn = new JButton("Matchmaker");
        matchmakerBtn.setFocusPainted(false);
        matchmakerBtn.setBackground(Color.white);
        matchmakerBtn.setBorderPainted(false);
        navbar.add(matchmakerBtn);

        // 4. ACTION LISTENERS (MODIFIED TO PASS userId or Implement Logout)
        homeBtn.addActionListener(e -> { dispose(); /* new IndexPage().setVisible(true); */ });
        
        // CHAT BUTTON: Pass userId to ChatPage
        chatBtn.addActionListener(e -> { dispose(); /* new ChatPage(this.userId).setVisible(true); */ });
        
        // LOGOUT BUTTON (Original loginBtn variable): Implements logout
        loginBtn.addActionListener(e -> { dispose(); /* new LoginPage().setVisible(true); */ }); 
        
        // MATCHMAKER BUTTON: Pass userId to MatchmakerPage (assuming it needs the ID)
        matchmakerBtn.addActionListener(e -> {
            dispose();
            /* new MatchmakerPage(this.userId).setVisible(true); */ // Pass userId instead of 'this'
        });

        // Main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Dashboard header 
        JLabel header = new JLabel("Welcome to Your Dashboard");
        header.setFont(new Font("Playfair Display", Font.BOLD, 32));
        header.setForeground(new Color(183, 25, 76)); // approximate --accent
        header.setHorizontalAlignment(SwingConstants.CENTER);

        // Sample content pane with cards or sections (simulate dashboard widgets)
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new GridLayout(2, 2, 20, 20));
        cardsPanel.setBackground(Color.white);

        // Create some panels as example dashboard cards
        JPanel profileCard = createCard("Profile", "View and edit your profile information");
        JPanel messagesCard = createCard("Messages", "Check your messages and notifications");
        JPanel settingsCard = createCard("Settings", "Update your preferences and settings");
        JPanel helpCard = createCard("Help", "Get assistance and support");
        
        // 5. PROFILE CARD CLICK LISTENER (MODIFIED TO PASS userId)
        profileCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profileCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Pass the stored userId to the UserProfilePage
                new UserProfilePage(DashboardPage.this.userId).setVisible(true);
            }
        });


        cardsPanel.add(profileCard);
        cardsPanel.add(messagesCard);
        cardsPanel.add(settingsCard);
        cardsPanel.add(helpCard);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(cardsPanel, BorderLayout.CENTER);

        // Add navbar and main panel to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(navbar, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // 6. ORIGINAL DUPLICATE ACTION LISTENERS (Kept as requested, though redundant)
        homeBtn.addActionListener(e -> {
            new IndexPage();
            this.dispose();
        });
        loginBtn.addActionListener(e -> { // loginBtn is now logoutBtn
            new LoginPage();
            this.dispose();
        });
        chatBtn.addActionListener(e -> {
            new ChatPage();
            this.dispose();
        });
        // Inside DashboardPage.java
    matchmakerBtn.addActionListener(e -> {
        dispose();
    // Use the new constructor: pass the stored userId and the DashboardPage instance ('this')
     new MatchmakerPage(this.userId, this).setVisible(true);
    } );

        setVisible(true);
    }
    
    // ... (createCard method remains the same) ...

    private JPanel createCard(String title, String description) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 183, 189)); // like --blush
        panel.setBorder(BorderFactory.createLineBorder(new Color(242, 198, 201), 2));
        panel.setLayout(new BorderLayout(10, 10));
        panel.setPreferredSize(new Dimension(180, 120));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Playfair Display", Font.BOLD, 20));
        titleLabel.setForeground(new Color(183, 25, 76)); // accent color

        JLabel descLabel = new JLabel("<html><p style=\"width: 150px;\">" + description + "</p></html>");
        descLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
        descLabel.setForeground(new Color(139, 139, 139)); // muted text

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(descLabel, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        // Run with a test ID for a quick launch
        SwingUtilities.invokeLater(() -> new DashboardPage(1)); 
    }
}