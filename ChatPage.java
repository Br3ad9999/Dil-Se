import javax.swing.*;
import java.awt.*;

public class ChatPage extends JFrame {
    public ChatPage() {
        setTitle("Chat DIL SE");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 247, 248)); // --bg approx

        // Navbar panel simulation
        JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navbar.setBackground(Color.white);
        JLabel logo = new JLabel("CHAT ");
        logo.setFont(new Font("Playfair Display", Font.BOLD, 24));
        JLabel accentWithMe = new JLabel("WITH ME");
        accentWithMe.setForeground(new Color(242, 198, 201)); // --accent
        accentWithMe.setFont(new Font("Playfair Display", Font.BOLD, 24));
        navbar.add(logo);
        navbar.add(accentWithMe);

        JButton dashboardBtn = new JButton("Dashboard");
        dashboardBtn.setFocusPainted(false);
        dashboardBtn.setBackground(Color.white);
        dashboardBtn.setBorderPainted(false);
        navbar.add(dashboardBtn);
        dashboardBtn.addActionListener(e -> { dispose(); new DashboardPage(); });
        setLayout(new BorderLayout());
        add(navbar, BorderLayout.NORTH);

        // Chat List Panel (left side)
        JPanel chatListPanel = new JPanel();
        chatListPanel.setBackground(new Color(255, 255, 255));
        chatListPanel.setPreferredSize(new Dimension(220, 0));
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));

        // Sample chat items
        chatListPanel.add(createChatItem("Aisha", "Hey, how are you?"));
        chatListPanel.add(createChatItem("Rohan", "Are we meeting today?"));
        chatListPanel.add(createChatItem("Mia", "I'll send the files by evening."));

        // Chat main display panel (right side)
        JPanel chatMainPanel = new JPanel();
        chatMainPanel.setBackground(new Color(255, 247, 248));
        chatMainPanel.setLayout(new BorderLayout());

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Montserrat", Font.PLAIN, 14));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane chatScroll = new JScrollPane(chatArea);

        JTextField messageInput = new JTextField();
        messageInput.setFont(new Font("Montserrat", Font.PLAIN, 14));
        JButton sendBtn = new JButton("Send");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageInput, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);

        chatMainPanel.add(chatScroll, BorderLayout.CENTER);
        chatMainPanel.add(inputPanel, BorderLayout.SOUTH);

        // Add panels to frame
        add(chatListPanel, BorderLayout.WEST);
        add(chatMainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createChatItem(String name, String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(220, 60));
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        panel.setBackground(Color.white);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Playfair Display", Font.BOLD, 16));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 0));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Montserrat", Font.PLAIN, 12));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));
        messageLabel.setForeground(new Color(139, 139, 139));

        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(messageLabel, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatPage());
    }
}
