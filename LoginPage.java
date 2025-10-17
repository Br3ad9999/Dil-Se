import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {
    public LoginPage() {
        setTitle("Login - DIL SE");
        setSize(AppConfig.WINDOW_WIDTH, AppConfig.WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        mainPanel.setBackground(new Color(255, 247, 248));

        // Back button at top left
        JButton topBackBtn = new JButton("← Back");
        topBackBtn.setBackground(Color.white);
        topBackBtn.setFont(new Font("Montserrat", Font.BOLD, 14));
        topBackBtn.setFocusPainted(false);
        topBackBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        topBackBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        topBackBtn.addActionListener(e -> {
            dispose();
            new IndexPage();
        });

        JLabel titleLabel = new JLabel("USER LOGIN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Playfair Display", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(300, 35)); // Wider field
        JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(300, 35)); // Wider field
        styleField(emailField);
        styleField(passField);

        JButton login = createButton("Log In");

        login.addActionListener(e -> {
            String email = emailField.getText().trim();
            String pw = new String(passField.getPassword());
            if (email.equals("test@mail.com") && pw.equals("12345")) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose(); // Close the LoginPage
                new DashboardPage();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }
        });

        // Create a form panel for both fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 247, 248));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Playfair Display", Font.PLAIN, 16));
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(emailField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Playfair Display", Font.PLAIN, 16));
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(passLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(passField, gbc);

        mainPanel.add(topBackBtn);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(login);
        mainPanel.add(Box.createVerticalStrut(10));

        add(mainPanel);
        setVisible(true);
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Montserrat", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createLineBorder(new Color(242, 198, 201), 2, true));
        // Removed setMaximumSize to allow preferred size to take effect
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(242, 198, 201));
        button.setFont(new Font("Montserrat", Font.BOLD, 16));
        button.setForeground(Color.black);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}
