import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupPage extends JFrame {

    // --- JDBC CONSTANTS ---
    // **MUST BE UPDATED FOR YOUR ENVIRONMENT**
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres"; 
    private static final String DB_USER = "postgres"; // Your PostgreSQL username
    private static final String DB_PASSWORD = "myuser"; // Your PostgreSQL password
    // ----------------------

    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public SignupPage() {
        setTitle("Sign Up - DIL SE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
        outerPanel.setBackground(new Color(255, 247, 248));

        // Top Back Button
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backPanel.setOpaque(false);
        JButton backBtn = new JButton("← Back");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        backBtn.setBackground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(7, 18, 7, 18));
        backBtn.addActionListener(e -> {
            dispose();
            new IndexPage();
        });
        backPanel.add(backBtn);
        outerPanel.add(backPanel);
        outerPanel.add(Box.createVerticalStrut(22));

        // Header
        JLabel header = new JLabel("Create an Account", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 27));
        header.setForeground(new Color(183, 25, 76));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        outerPanel.add(header);
        outerPanel.add(Box.createVerticalStrut(28));

        // Form
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));

        // Username
        usernameField = createFieldWithPrompt(formPanel, "Username");
        outerPanel.add(formPanel);
        formPanel.add(Box.createVerticalStrut(10));
        // Email
        emailField = createFieldWithPrompt(formPanel, "Email");
        formPanel.add(Box.createVerticalStrut(10));
        // Password
        passwordField = createPasswordFieldWithPrompt(formPanel, "Password");
        formPanel.add(Box.createVerticalStrut(10));
        // Confirm Password
        confirmPasswordField = createPasswordFieldWithPrompt(formPanel, "Confirm Password");
        formPanel.add(Box.createVerticalStrut(14));

        // Sign Up Button
        JButton signupBtn = new JButton("Sign Up");
        signupBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupBtn.setBackground(new Color(242, 198, 201));
        signupBtn.setFont(new Font("SansSerif", Font.BOLD, 19));
        signupBtn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupBtn.setFocusPainted(false);
        
        // --- DATABASE SIGNUP LOGIC ---
        signupBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (registerUser(username, email, password)) {
                JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new LoginPage();
            }
        });
        // -----------------------------
        
        outerPanel.add(Box.createVerticalStrut(20));
        outerPanel.add(signupBtn);

        // Login redirect
        JPanel loginPanel = new JPanel();
        loginPanel.setOpaque(false);
        JLabel loginRedirect = new JLabel("<html>Already have an account? <a href='#'>Log In</a></html>");
        loginRedirect.setFont(new Font("SansSerif", Font.PLAIN, 14));
        loginRedirect.setForeground(new Color(139, 139, 139));
        loginRedirect.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginRedirect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new LoginPage();
            }
        });
        loginPanel.add(loginRedirect);
        outerPanel.add(Box.createVerticalStrut(15));
        outerPanel.add(loginPanel);

        add(outerPanel);
        setVisible(true);
    }

    /**
     * Attempts to register the user by inserting a new record into the database.
     * @return true if the registration was successful, false otherwise.
     */
    private boolean registerUser(String username, String email, String password) {
        String SQL = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)";

        // IMPORTANT SECURITY NOTE: Use a proper hashing library (like BCrypt) here!
        String passwordHash = password; // PLACEHOLDER: Should be a hashed password

        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(SQL)
        ) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, passwordHash); 
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.err.println("SQL Registration Error: " + ex.getMessage());
            // Check for unique constraint violation (e.g., username/email already taken)
            if (ex.getSQLState().startsWith("23")) { // SQLState for Integrity Constraint Violation
                JOptionPane.showMessageDialog(this, "Registration Failed. Username or Email may already be in use.", "Database Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "A database error occurred during registration.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }

    // Helper to add a field to a parent panel
    private JTextField createFieldWithPrompt(JPanel parent, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        parent.add(label);
        parent.add(Box.createVerticalStrut(4));
        JTextField field = new JTextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 15));
        field.setMaximumSize(new Dimension(400, 34));
        field.setColumns(20);
        field.setBackground(Color.white);
        field.setBorder(BorderFactory.createLineBorder(new Color(242, 198, 201), 2));
        parent.add(field);
        return field;
    }

    private JPasswordField createPasswordFieldWithPrompt(JPanel parent, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        parent.add(label);
        parent.add(Box.createVerticalStrut(4));
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 15));
        field.setMaximumSize(new Dimension(400, 34));
        field.setColumns(20);
        field.setBackground(Color.white);
        field.setBorder(BorderFactory.createLineBorder(new Color(242, 198, 201), 2));
        parent.add(field);
        return field;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignupPage::new);
    }
}