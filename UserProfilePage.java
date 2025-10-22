import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserProfilePage extends JFrame {

    // ===== DATABASE CONFIGURATION (UPDATE PASSWORD IF NECESSARY) =====
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "myuser"; // <-- USE YOUR ACTUAL DB PASSWORD
    // =================================================================

    // ===== INSTANCE FIELD FOR LOGGED-IN USER =====
    private final int loggedInUserId;
    // =============================================

    // ===== UI COMPONENTS =====
    private JTextField nameField, ageField, locationField, interestsField;
    private JComboBox<String> genderBox, preferenceBox;
    private JTextArea bioArea;
    // ==========================

    // MODIFIED CONSTRUCTOR: Requires the User ID
    public UserProfilePage(int userId) {
        this.loggedInUserId = userId; // Store the ID passed from the Dashboard
        
        setTitle("💖 User Profile - Dil Se 💖");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(255, 182, 193)); 
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // ===== TITLE =====
        JLabel title = new JLabel("✨ Create Your Profile ✨ (User ID: " + userId + ")", JLabel.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        mainPanel.add(title, BorderLayout.NORTH);

        // ===== FORM (Same as before) =====
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setOpaque(false);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        formPanel.add(ageField);

        formPanel.add(new JLabel("Gender:"));
        genderBox = new JComboBox<>(new String[]{"Select", "Male", "Female", "Other"});
        formPanel.add(genderBox);

        formPanel.add(new JLabel("Location:"));
        locationField = new JTextField();
        formPanel.add(locationField);

        formPanel.add(new JLabel("Interests:"));
        interestsField = new JTextField();
        formPanel.add(interestsField);

        formPanel.add(new JLabel("Preference:"));
        preferenceBox = new JComboBox<>(new String[]{"Select", "Friendship", "Casual", "Serious Relationship"});
        formPanel.add(preferenceBox);

        formPanel.add(new JLabel("Bio:"));
        bioArea = new JTextArea(3, 20);
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(bioArea);
        formPanel.add(scroll);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // ===== BUTTONS (Same as before) =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton saveBtn = new JButton("Save Profile 💌");
        JButton clearBtn = new JButton("Clear");
        saveBtn.setBackground(new Color(255, 105, 180));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        clearBtn.setBackground(Color.WHITE);
        clearBtn.setForeground(Color.PINK);
        clearBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        buttonPanel.add(saveBtn);
        buttonPanel.add(clearBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // ===== ACTION LISTENERS =====
        saveBtn.addActionListener(e -> handleSaveProfile());
        clearBtn.addActionListener(e -> clearFields());

        // Load profile data for THIS user ID
        loadProfileData();
        setVisible(true);
    }

    // =========================================================
    //                   EVENT HANDLERS
    // =========================================================
    private void handleSaveProfile() {
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String gender = (String) genderBox.getSelectedItem();
        String location = locationField.getText().trim();
        String interests = interestsField.getText().trim();
        String preference = (String) preferenceBox.getSelectedItem();
        String bio = bioArea.getText().trim();

        if (name.isEmpty() || ageText.isEmpty() || gender.equals("Select") || location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in Name, Age, Gender, and Location.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
            if (age < 18 || age > 120) {
                JOptionPane.showMessageDialog(this, "Age must be between 18 and 120.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = saveProfile(this.loggedInUserId, name, age, gender, location, interests, preference, bio);
        if (success) {
            JOptionPane.showMessageDialog(this, "💖 Profile Saved Successfully! 💖", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        genderBox.setSelectedIndex(0);
        locationField.setText("");
        interestsField.setText("");
        preferenceBox.setSelectedIndex(0);
        bioArea.setText("");
    }

    // =========================================================
    //                   DATABASE OPERATIONS
    // =========================================================
    private boolean saveProfile(int userId, String name, int age, String gender, String location,
                                String interests, String preference, String bio) {

        // PostgreSQL UPSERT statement
        String SQL = """
            INSERT INTO profiles (user_id, name, age, gender, location, interests, preference, bio)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT (user_id)
            DO UPDATE SET
                name = EXCLUDED.name,
                age = EXCLUDED.age,
                gender = EXCLUDED.gender,
                location = EXCLUDED.location,
                interests = EXCLUDED.interests,
                preference = EXCLUDED.preference,
                bio = EXCLUDED.bio,
                updated_at = CURRENT_TIMESTAMP;
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            pstmt.setString(4, gender);
            pstmt.setString(5, location);
            pstmt.setString(6, interests);
            pstmt.setString(7, preference);
            pstmt.setString(8, bio);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: Failed to save profile. Check DB credentials/table setup. " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void loadProfileData() {
        String SQL = "SELECT name, age, gender, location, interests, preference, bio FROM profiles WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, this.loggedInUserId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                ageField.setText(String.valueOf(rs.getInt("age")));
                genderBox.setSelectedItem(rs.getString("gender"));
                locationField.setText(rs.getString("location"));
                interestsField.setText(rs.getString("interests"));
                preferenceBox.setSelectedItem(rs.getString("preference"));
                bioArea.setText(rs.getString("bio"));
            }
        } catch (SQLException e) {
            // This is non-fatal: either no data exists, or the DB connection failed (which is handled by saveProfile error message).
            System.err.println("Load Profile Error (Non-Fatal): " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // For testing the profile page directly, use a test ID (e.g., 1)
        SwingUtilities.invokeLater(() -> new UserProfilePage(1));
    }
}