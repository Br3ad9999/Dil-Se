import javax.swing.*;
import java.awt.*;

public class IndexPage extends JFrame {
    public IndexPage() {
        setTitle("DIL SE Home");
        setSize(AppConfig.WINDOW_WIDTH, AppConfig.WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel navbar = createNavbar();
        JPanel heroPanel = createHeroPanel();
        JPanel callButtons = createCallButtons();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(navbar, BorderLayout.NORTH);
        getContentPane().add(heroPanel, BorderLayout.CENTER);
        getContentPane().add(callButtons, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createNavbar() {
        JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        navbar.setBackground(Color.white);
        JLabel logo = new JLabel("DIL ");
        logo.setFont(new Font("Playfair Display", Font.BOLD, 28));
        JLabel accentSE = new JLabel("SE");
        accentSE.setForeground(new Color(242, 198, 201));
        accentSE.setFont(new Font("Playfair Display", Font.BOLD, 28));
        navbar.add(logo);
        navbar.add(accentSE);
        return navbar;
    }

    private JPanel createHeroPanel() {
        JPanel heroPanel = new JPanel();
        heroPanel.setBackground(new Color(255, 247, 248));
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));
        heroPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        JLabel tagline = new JLabel("because every love story matters", SwingConstants.CENTER);
        tagline.setFont(new Font("Dancing Script", Font.PLAIN, 36));
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);
        heroPanel.add(tagline);
        return heroPanel;
    }

    private JPanel createCallButtons() {
        JPanel callButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        callButtons.setBackground(new Color(255, 247, 248));

        JButton loginBtn = new JButton("Log In");
        JButton signupBtn = new JButton("Sign Up");
        loginBtn.setBackground(new Color(242, 198, 201));
        signupBtn.setBackground(new Color(242, 198, 201));

        loginBtn.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        signupBtn.addActionListener(e -> {
            dispose();
            new SignupPage();
        });

        callButtons.add(loginBtn);
        callButtons.add(signupBtn);
        return callButtons;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(IndexPage::new);
    }
}
