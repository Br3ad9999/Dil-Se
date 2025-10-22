import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage extends JFrame {

    // --- JDBC CONSTANTS ---
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres"; 
    private static final String DB_USER = "postgres"; 
    private static final String DB_PASSWORD = "myuser"; // <-- USE YOUR ACTUAL DB PASSWORD
    // ----------------------

    private JTextField emailField;
    private JPasswordField passField;

    public LoginPage() {
        setTitle("Login - DIL SE");
        setSize(450, 500); 
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
            // Assuming IndexPage exists and you want to go back to it
            // new IndexPage().setVisible(true); 
        });

        JLabel titleLabel = new JLabel("USER LOGIN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Playfair Display", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(300, 35));
        passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(300, 35));
        styleField(emailField);
        styleField(passField);

        JButton login = createButton("Log In");

        // --- UPDATED DATABASE LOGIN LOGIC (FIXED) ---
        login.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passField.getPassword());
            
            // Call modified method, which returns the user ID (0 if failed)
            int userId = verifyLogin(email, password); 
            
            if (userId > 0) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose(); 
                // CRUCIAL FIX: Pass the retrieved userId and make the dashboard visible
                new DashboardPage(userId).setVisible(true); 
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        // --------------------------------------------

        // Create a form panel for both fields (GridBagLayout remains the same)
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
    
    private int verifyLogin(String email, String password) {
        String SQL = "SELECT id, password_hash FROM users WHERE email = ?";
        
        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(SQL)
        ) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                
                if (password.equals(storedHash)) { 
                    return rs.getInt("id"); 
                }
            }
            return 0;
        } catch (SQLException ex) {
            System.err.println("SQL Login Error: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "A database connection error occurred. Check DB credentials.", "Connection Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Montserrat", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createLineBorder(new Color(242, 198, 201), 2, true));
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