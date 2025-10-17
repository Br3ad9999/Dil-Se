import javax.swing.*;
import java.awt.*;

public class SignupPage extends JFrame {

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
        signupBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Account created successfully!");
            dispose();
            new LoginPage();
        });
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
