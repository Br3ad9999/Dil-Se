import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MatchmakerPage extends JFrame {
    private ArrayList<JPanel> cards = new ArrayList<>();
    private int current = 0;
    private JPanel cardContainer;
    private JFrame previousPage;
    
    // NEW FIELD TO STORE THE LOGGED-IN USER ID
    private final int userId; 
    
    // Original 0-arg constructor (now defaults to test ID 1)
    public MatchmakerPage() {
        this(1, null); 
    }
    
    // Original JFrame-arg constructor (now defaults to test ID 1)
    public MatchmakerPage(JFrame previousPage) {
        this(1, previousPage);
    }
    
    // NEW PRIMARY CONSTRUCTOR that accepts the User ID
    public MatchmakerPage(int userId, JFrame previousPage) {
        this.userId = userId; // Store the ID
        this.previousPage = previousPage;
        
        setTitle("Matchmaker - DIL SE (User: " + userId + ")");
        setSize(450, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changed from EXIT_ON_CLOSE
        
        // Background gradient mimic using JPanel with override paintComponent
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color c1 = new Color(255, 154, 158);
                Color c2 = new Color(250, 208, 196);
                GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(new BorderLayout(10, 10));
        background.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Navbar simulation
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setOpaque(false);
        JLabel logo = new JLabel("Matchmaker", SwingConstants.LEFT);
        logo.setFont(new Font("Playfair Display", Font.BOLD, 24));
        logo.setForeground(Color.white);
        navbar.add(logo, BorderLayout.WEST);
        
        // Card container (stacked cards)
        cardContainer = new JPanel(null);
        cardContainer.setPreferredSize(new Dimension(400, 450));
        cardContainer.setOpaque(false);
        background.add(cardContainer, BorderLayout.CENTER);
        
        // Create sample cards (replace details with real content as needed)
        // Here, you would use this.userId to load real match data from PostgreSQL
        addCard("Alice", "https://via.placeholder.com/400x300.png?text=Alice");
        addCard("Bob", "https://via.placeholder.com/400x300.png?text=Bob");
        addCard("Charlie", "https://via.placeholder.com/400x300.png?text=Charlie");
        
        showCard(current);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        buttonsPanel.setOpaque(false);
        
        JButton nopeBtn = new JButton("❌");
        nopeBtn.setFont(new Font("SansSerif", Font.PLAIN, 36));
        nopeBtn.setFocusPainted(false);
        nopeBtn.setBackground(Color.white);
        nopeBtn.setBorder(BorderFactory.createEmptyBorder());
        nopeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton loveBtn = new JButton("❤️");
        loveBtn.setFont(new Font("SansSerif", Font.PLAIN, 36));
        loveBtn.setFocusPainted(false);
        loveBtn.setBackground(Color.white);
        loveBtn.setBorder(BorderFactory.createEmptyBorder());
        loveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonsPanel.add(nopeBtn);
        buttonsPanel.add(loveBtn);
        
        background.add(buttonsPanel, BorderLayout.SOUTH);
        
        // Button actions
        nopeBtn.addActionListener(e -> swipe("left"));
        loveBtn.addActionListener(e -> swipe("right"));
        
        // Back button at top left
        JButton backBtn = new JButton("← Back");
        backBtn.setBackground(Color.white);
        backBtn.setFont(new Font("Montserrat", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        backBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        backBtn.addActionListener(e -> {
            dispose();
            if (previousPage != null) previousPage.setVisible(true);
            // new DashboardPage(this.userId).setVisible(true); // If previousPage is null, go back to dashboard
        });

        // Add backBtn to the top of background panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(backBtn, BorderLayout.WEST);
        
        // Combine topPanel (with backBtn) and original navbar elements
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(topPanel, BorderLayout.NORTH); // Put back button on top
        headerPanel.add(navbar, BorderLayout.CENTER); // Put "Matchmaker" logo below
        background.add(headerPanel, BorderLayout.NORTH);


        setContentPane(background);
        // pack(); // Removed pack() to keep the initial set size
        setVisible(true);
    }
    
    // ... (rest of the helper methods remain the same) ...

    private void addCard(String name, String imageUrl) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.white);
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 107, 129), 3, true));
        card.setBounds(0, 0, 400, 450);
        
        // Image label (placeholder)
        JLabel imgLabel = new JLabel(name, SwingConstants.CENTER);
        imgLabel.setFont(new Font("Playfair Display", Font.BOLD, 24));
        imgLabel.setPreferredSize(new Dimension(400, 300));
        imgLabel.setOpaque(true);
        imgLabel.setBackground(Color.lightGray);
        imgLabel.setForeground(Color.darkGray);
        
        // Text label
        JLabel descLabel = new JLabel("<html><center>Profile info about " + name + "</center></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font("Montserrat", Font.PLAIN, 16));
        descLabel.setForeground(new Color(85, 85, 85));
        descLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        card.add(imgLabel, BorderLayout.CENTER);
        card.add(descLabel, BorderLayout.SOUTH);
        
        cards.add(card);
        cardContainer.add(card);
    }
    
    private void showCard(int index) {
        for (int i = 0; i < cards.size(); i++) {
            JPanel card = cards.get(i);
            if (i == index) {
                card.setVisible(true);
                card.setLocation(0, 0);
                card.setSize(400, 450);
            } else {
                card.setVisible(false);
            }
        }
    }
    
    private void swipe(String direction) {
        if (cards.isEmpty()) return;
        JPanel card = cards.get(current);
        
        // Animate swipe: just move card off screen roughly (simplified)
        int offset = direction.equals("right") ? 400 : -400;
        Point location = card.getLocation();
        card.setLocation(location.x + offset, location.y);
        card.setVisible(false);
        
        current = (current + 1) % cards.size();
        showCard(current);
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MatchmakerPage(1, null));
    }
}