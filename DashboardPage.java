import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardPage extends JFrame {
    public DashboardPage() {
        setTitle("Dashboard - DIL SE");
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

        JButton loginBtn = new JButton("Log In");
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

        // Action listeners for navigation buttons
        homeBtn.addActionListener(e -> { dispose(); new IndexPage(); });
        chatBtn.addActionListener(e -> { dispose(); new ChatPage(); });
        loginBtn.addActionListener(e -> { dispose(); new LoginPage(); });
        matchmakerBtn.addActionListener(e -> {
            dispose();
            new MatchmakerPage(this); // Pass reference for back navigation
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
        cardsPanel.add(createCard("Profile", "View and edit your profile information"));
        cardsPanel.add(createCard("Messages", "Check your messages and notifications"));
        cardsPanel.add(createCard("Settings", "Update your preferences and settings"));
        cardsPanel.add(createCard("Help", "Get assistance and support"));

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(cardsPanel, BorderLayout.CENTER);

        // Add navbar and main panel to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(navbar, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Button navigation actions
        homeBtn.addActionListener(e -> {
            new IndexPage();
            this.dispose();
        });
        loginBtn.addActionListener(e -> {
            new LoginPage();
            this.dispose();
        });
        chatBtn.addActionListener(e -> {
            new ChatPage();
            this.dispose();
        });

        setVisible(true);
    }

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
        SwingUtilities.invokeLater(() -> new DashboardPage());
    }
}
