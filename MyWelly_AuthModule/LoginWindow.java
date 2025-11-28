import java.awt.event.ActionEvent;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.awt.event.MouseAdapter;
import java.awt.Cursor;
import java.awt.BorderLayout;
import javax.swing.border.Border;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import java.awt.Container;
import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.6.0
// 

class LoginWindow extends JFrame
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberMeCheckbox;
    
    public LoginWindow() {
        this.setTitle("MyWelly - Login");
        this.setSize(1100, 750);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        final JPanel mainPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(this.createWelcomePanel());
        mainPanel.add(this.createLoginPanel());
        this.setContentPane(mainPanel);
    }
    
    private JPanel createWelcomePanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 1));
        panel.setBackground(UIConstants.VERY_LIGHT_GREEN);
        panel.setBorder(BorderFactory.createEmptyBorder(80, 50, 80, 50));
        final JLabel iconLabel = new JLabel("\ud83d\udc9a");
        iconLabel.setFont(new Font("Segoe UI Emoji", 0, 100));
        iconLabel.setAlignmentX(0.5f);
        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(25));
        final JLabel welcomeLabel = new JLabel("Welcome to");
        welcomeLabel.setFont(new Font("Segoe UI", 0, 26));
        welcomeLabel.setForeground(UIConstants.LIGHT_GREEN);
        welcomeLabel.setAlignmentX(0.5f);
        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(8));
        final JLabel appNameLabel = new JLabel("MyWelly");
        appNameLabel.setFont(new Font("Segoe Script", 1, 56));
        appNameLabel.setForeground(UIConstants.PRIMARY_GREEN);
        appNameLabel.setAlignmentX(0.5f);
        panel.add(appNameLabel);
        panel.add(Box.createVerticalStrut(15));
        final JLabel descLabel = new JLabel("<html><center>Your Trusted Healthcare<br>Companion in Morocco</center></html>");
        descLabel.setFont(new Font("Segoe UI", 0, 17));
        descLabel.setForeground(Color.GRAY);
        descLabel.setAlignmentX(0.5f);
        descLabel.setHorizontalAlignment(0);
        panel.add(descLabel);
        panel.add(Box.createVerticalStrut(50));
        final String[][] array;
        final String[][] features = array = new String[][] { { "\ud83d\udd0d", "Find Certified Doctors" }, { "\ud83d\udcc5", "Easy Appointment Booking" }, { "\u2b50", "Patient Reviews & Ratings" }, { "\ud83d\udccb", "Digital Medical Records" } };
        for (final String[] feature : array) {
            final JPanel featurePanel = new JPanel(new FlowLayout(0, 10, 5));
            featurePanel.setBackground(UIConstants.VERY_LIGHT_GREEN);
            final JLabel icon = new JLabel(feature[0]);
            icon.setFont(new Font("Segoe UI Emoji", 0, 22));
            featurePanel.add(icon);
            final JLabel text = new JLabel(feature[1]);
            text.setFont(new Font("Segoe UI", 0, 15));
            text.setForeground(UIConstants.PRIMARY_GREEN);
            featurePanel.add(text);
            panel.add(featurePanel);
            panel.add(Box.createVerticalStrut(8));
        }
        panel.add(Box.createVerticalGlue());
        return panel;
    }
    
    private JPanel createLoginPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(100, 70, 100, 70));
        final JLabel titleLabel = new JLabel("Sign In");
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(UIConstants.PRIMARY_GREEN);
        titleLabel.setAlignmentX(0.0f);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(8));
        final JLabel subtitleLabel = new JLabel("Welcome back! Please enter your details");
        subtitleLabel.setFont(new Font("Segoe UI", 0, 15));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setAlignmentX(0.0f);
        panel.add(subtitleLabel);
        panel.add(Box.createVerticalStrut(45));
        final JLabel usernameLabel = new JLabel("Username or Email");
        usernameLabel.setFont(new Font("Segoe UI", 1, 14));
        usernameLabel.setForeground(UIConstants.PRIMARY_GREEN);
        usernameLabel.setAlignmentX(0.0f);
        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(10));
        (this.usernameField = new JTextField()).setFont(new Font("Segoe UI", 0, 16));
        this.usernameField.setMaximumSize(new Dimension(400, 50));
        this.usernameField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIConstants.LIGHT_GREEN, 2, true), BorderFactory.createEmptyBorder(12, 18, 12, 18)));
        this.usernameField.setAlignmentX(0.0f);
        panel.add(this.usernameField);
        panel.add(Box.createVerticalStrut(25));
        final JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", 1, 14));
        passwordLabel.setForeground(UIConstants.PRIMARY_GREEN);
        passwordLabel.setAlignmentX(0.0f);
        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(10));
        (this.passwordField = new JPasswordField()).setFont(new Font("Segoe UI", 0, 16));
        this.passwordField.setMaximumSize(new Dimension(400, 50));
        this.passwordField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIConstants.LIGHT_GREEN, 2, true), BorderFactory.createEmptyBorder(12, 18, 12, 18)));
        this.passwordField.setAlignmentX(0.0f);
        panel.add(this.passwordField);
        panel.add(Box.createVerticalStrut(20));
        final JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setBackground(Color.WHITE);
        optionsPanel.setMaximumSize(new Dimension(400, 35));
        optionsPanel.setAlignmentX(0.0f);
        (this.rememberMeCheckbox = new JCheckBox("Remember me")).setFont(new Font("Segoe UI", 0, 14));
        this.rememberMeCheckbox.setBackground(Color.WHITE);
        this.rememberMeCheckbox.setForeground(Color.GRAY);
        optionsPanel.add(this.rememberMeCheckbox, "West");
        final JLabel forgotLabel = new JLabel("<html><u>Forgot Password?</u></html>");
        forgotLabel.setFont(new Font("Segoe UI", 0, 14));
        forgotLabel.setForeground(UIConstants.LIGHT_GREEN);
        forgotLabel.setCursor(new Cursor(12));
        forgotLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                LoginWindow.this.showForgotPasswordDialog();
            }
            
            @Override
            public void mouseEntered(final MouseEvent e) {
                forgotLabel.setForeground(UIConstants.HOVER_GREEN);
            }
            
            @Override
            public void mouseExited(final MouseEvent e) {
                forgotLabel.setForeground(UIConstants.LIGHT_GREEN);
            }
        });
        optionsPanel.add(forgotLabel, "East");
        panel.add(optionsPanel);
        panel.add(Box.createVerticalStrut(35));
        final JButton loginButton = UIHelper.createStyledButton("Sign In", UIConstants.PRIMARY_GREEN, Color.WHITE);
        loginButton.setMaximumSize(new Dimension(400, 55));
        loginButton.setAlignmentX(0.0f);
        loginButton.setFont(new Font("Segoe UI", 1, 17));
        loginButton.addActionListener(e -> this.handleLogin());
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(20));
        final JPanel signupPanel = new JPanel(new FlowLayout(1, 8, 0));
        signupPanel.setBackground(Color.WHITE);
        signupPanel.setMaximumSize(new Dimension(400, 40));
        signupPanel.setAlignmentX(0.0f);
        final JLabel noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setFont(new Font("Segoe UI", 0, 15));
        noAccountLabel.setForeground(Color.GRAY);
        signupPanel.add(noAccountLabel);
        final JLabel signupLabel = new JLabel("<html><u>Sign Up</u></html>");
        signupLabel.setFont(new Font("Segoe UI", 1, 15));
        signupLabel.setForeground(UIConstants.LIGHT_GREEN);
        signupLabel.setCursor(new Cursor(12));
        signupLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                LoginWindow.this.dispose();
                new SignUpWindow().setVisible(true);
            }
            
            @Override
            public void mouseEntered(final MouseEvent e) {
                signupLabel.setForeground(UIConstants.HOVER_GREEN);
            }
            
            @Override
            public void mouseExited(final MouseEvent e) {
                signupLabel.setForeground(UIConstants.LIGHT_GREEN);
            }
        });
        signupPanel.add(signupLabel);
        panel.add(signupPanel);
        return panel;
    }
    
    private void handleLogin() {
        final String username = this.usernameField.getText().trim();
        final String password = new String(this.passwordField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            UIHelper.showError(this, "Please enter both username and password");
            return;
        }
        final JDialog loadingDialog = UIHelper.showLoadingDialog(this, "Signing in...");
        new Thread(() -> {
            try {
                // Authenticate user with database
                AuthenticationService authService = new AuthenticationService();
                UserEntity user = authService.login(username, password);

                // Login successful, navigate to dashboard
                SwingUtilities.invokeLater(() -> {
                    loadingDialog.dispose();
                    this.dispose();
                    new DashboardWindow(user.getUserType().name(), user.getUserName()).setVisible(true);
                });
            }
            catch (final Exception e) {
                SwingUtilities.invokeLater(() -> {
                    loadingDialog.dispose();
                    UIHelper.showError((JFrame)this, "Login failed: " + e.getMessage());
                });
            }
        }).start();
    }
    
    private void showForgotPasswordDialog() {
        final JDialog dialog = new JDialog(this, "Reset Password", true);
        dialog.setSize(450, 280);
        dialog.setLocationRelativeTo(this);
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));
        panel.setBackground(Color.WHITE);
        final JLabel titleLabel = new JLabel("Reset Your Password");
        titleLabel.setFont(UIConstants.SUBHEADER_FONT);
        titleLabel.setForeground(UIConstants.PRIMARY_GREEN);
        titleLabel.setAlignmentX(0.5f);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(12));
        final JLabel infoLabel = new JLabel("<html><center>Enter your email address and we'll send<br>you a verification code to reset password</center></html>");
        infoLabel.setFont(new Font("Segoe UI", 0, 14));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setAlignmentX(0.5f);
        infoLabel.setHorizontalAlignment(0);
        panel.add(infoLabel);
        panel.add(Box.createVerticalStrut(28));
        final JTextField emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", 0, 15));
        emailField.setMaximumSize(new Dimension(360, 45));
        emailField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIConstants.LIGHT_GREEN, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        emailField.setAlignmentX(0.5f);
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(28));
        final JButton sendButton = UIHelper.createStyledButton("Send Reset Code", UIConstants.PRIMARY_GREEN, Color.WHITE);
        sendButton.setMaximumSize(new Dimension(360, 45));
        sendButton.setAlignmentX(0.5f);
        sendButton.addActionListener(e -> {
            dialog.dispose();
            UIHelper.showSuccess(this, "Password reset code sent to your email!");
            return;
        });
        panel.add(sendButton);
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
}
