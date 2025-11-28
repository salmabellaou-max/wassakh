import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.awt.event.MouseAdapter;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.Cursor;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.HashMap;
import javax.swing.JComponent;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.6.0
// 

class SignUpWindow extends JFrame
{
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField fullNameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> userTypeCombo;
    private JPanel dynamicFieldsPanel;
    private Map<String, JComponent> dynamicFields;
    
    public SignUpWindow() {
        this.dynamicFields = new HashMap<String, JComponent>();
        this.setTitle("MyWelly - Sign Up");
        this.setSize(1150, 800);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(this.createHeaderPanel(), "North");
        final JScrollPane scrollPane = new JScrollPane(this.createFormPanel());
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, "Center");
        this.setContentPane(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(UIConstants.PRIMARY_GREEN);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 45, 30, 45));
        final JLabel titleLabel = new JLabel("Create Your Account");
        titleLabel.setFont(new Font("Segoe UI", 1, 34));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, "West");
        final JButton backButton = new JButton("\u2190 Back to Login");
        backButton.setFont(new Font("Segoe UI", 0, 15));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(UIConstants.LIGHT_GREEN);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(12));
        backButton.addActionListener(e -> {
            this.dispose();
            new LoginWindow().setVisible(true);
            return;
        });
        panel.add(backButton, "East");
        return panel;
    }
    
    private JPanel createFormPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(45, 90, 45, 90));
        UIHelper.addFormField(panel, "I am registering as", this.userTypeCombo = new JComboBox<String>(new String[] { "Patient", "Doctor", "Laboratory" }));
        this.userTypeCombo.setMaximumSize(new Dimension(550, 50));
        this.userTypeCombo.setFont(new Font("Segoe UI", 0, 16));
        this.userTypeCombo.addActionListener(e -> this.updateDynamicFields());
        panel.add(Box.createVerticalStrut(25));
        UIHelper.addFormField(panel, "Full Name", this.fullNameField = UIHelper.createTextField());
        UIHelper.addFormField(panel, "Username", this.usernameField = UIHelper.createTextField());
        UIHelper.addFormField(panel, "Email Address", this.emailField = UIHelper.createTextField());
        UIHelper.addFormField(panel, "Phone Number", this.phoneField = UIHelper.createTextField());
        UIHelper.addFormField(panel, "Password", this.passwordField = UIHelper.createPasswordField());
        UIHelper.addFormField(panel, "Confirm Password", this.confirmPasswordField = UIHelper.createPasswordField());
        (this.dynamicFieldsPanel = new JPanel()).setLayout(new BoxLayout(this.dynamicFieldsPanel, 1));
        this.dynamicFieldsPanel.setBackground(Color.WHITE);
        panel.add(this.dynamicFieldsPanel);
        this.updateDynamicFields();
        panel.add(Box.createVerticalStrut(30));
        final JCheckBox termsCheckbox = new JCheckBox("<html>I agree to the <u>Terms and Conditions</u> and <u>Privacy Policy</u></html>");
        termsCheckbox.setFont(new Font("Segoe UI", 0, 14));
        termsCheckbox.setBackground(Color.WHITE);
        panel.add(termsCheckbox);
        panel.add(Box.createVerticalStrut(30));
        final JButton signUpButton = UIHelper.createStyledButton("Create Account", UIConstants.PRIMARY_GREEN, Color.WHITE);
        signUpButton.setMaximumSize(new Dimension(550, 55));
        signUpButton.setFont(new Font("Segoe UI", 1, 17));
        signUpButton.addActionListener(e -> this.handleSignUp());
        panel.add(signUpButton);
        panel.add(Box.createVerticalStrut(15));
        final JPanel loginPanel = new JPanel(new FlowLayout(1, 8, 0));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setMaximumSize(new Dimension(550, 40));
        final JLabel haveAccountLabel = new JLabel("Already have an account?");
        haveAccountLabel.setFont(new Font("Segoe UI", 0, 15));
        haveAccountLabel.setForeground(Color.GRAY);
        loginPanel.add(haveAccountLabel);
        final JLabel loginLabel = new JLabel("<html><u>Sign In</u></html>");
        loginLabel.setFont(new Font("Segoe UI", 1, 15));
        loginLabel.setForeground(UIConstants.LIGHT_GREEN);
        loginLabel.setCursor(new Cursor(12));
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                SignUpWindow.this.dispose();
                new LoginWindow().setVisible(true);
            }
        });
        loginPanel.add(loginLabel);
        panel.add(loginPanel);
        return panel;
    }
    
    private void updateDynamicFields() {
        this.dynamicFieldsPanel.removeAll();
        this.dynamicFields.clear();
        final String userType = (String)this.userTypeCombo.getSelectedItem();
        this.dynamicFieldsPanel.add(Box.createVerticalStrut(25));
        final JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(550, 1));
        separator.setForeground(UIConstants.LIGHT_GREEN);
        this.dynamicFieldsPanel.add(separator);
        this.dynamicFieldsPanel.add(Box.createVerticalStrut(25));
        final JLabel additionalLabel = new JLabel("Additional Information");
        additionalLabel.setFont(new Font("Segoe UI", 1, 18));
        additionalLabel.setForeground(UIConstants.PRIMARY_GREEN);
        this.dynamicFieldsPanel.add(additionalLabel);
        this.dynamicFieldsPanel.add(Box.createVerticalStrut(20));
        if ("Patient".equals(userType)) {
            final JTextField dobField = UIHelper.createTextField();
            UIHelper.addFormField(this.dynamicFieldsPanel, "Date of Birth (YYYY-MM-DD)", dobField);
            this.dynamicFields.put("dob", dobField);
            final JComboBox<String> genderCombo = new JComboBox<String>(new String[] { "Male", "Female", "Other" });
            genderCombo.setFont(new Font("Segoe UI", 0, 16));
            genderCombo.setMaximumSize(new Dimension(550, 50));
            UIHelper.addFormField(this.dynamicFieldsPanel, "Gender", genderCombo);
            this.dynamicFields.put("gender", genderCombo);
            final JTextField idField = UIHelper.createTextField();
            UIHelper.addFormField(this.dynamicFieldsPanel, "ID Number (CIN)", idField);
            this.dynamicFields.put("idNumber", idField);
        }
        else if ("Doctor".equals(userType)) {
            final JTextField specialtyField = UIHelper.createTextField();
            UIHelper.addFormField(this.dynamicFieldsPanel, "Medical Specialty", specialtyField);
            this.dynamicFields.put("specialty", specialtyField);
            final JTextField clinicField = UIHelper.createTextField();
            UIHelper.addFormField(this.dynamicFieldsPanel, "Clinic/Hospital Name", clinicField);
            this.dynamicFields.put("clinicName", clinicField);
            final JTextField addressField = UIHelper.createTextField();
            UIHelper.addFormField(this.dynamicFieldsPanel, "Clinic Address", addressField);
            this.dynamicFields.put("address", addressField);
            final JTextField cityField = UIHelper.createTextField();
            UIHelper.addFormField(this.dynamicFieldsPanel, "City", cityField);
            this.dynamicFields.put("city", cityField);
            final JTextField feesField = UIHelper.createTextField();
            UIHelper.addFormField(this.dynamicFieldsPanel, "Consultation Fees (MAD)", feesField);
            this.dynamicFields.put("fees", feesField);
            final JTextField experienceField = UIHelper.createTextField();
            UIHelper.addFormField(this.dynamicFieldsPanel, "Years of Experience", experienceField);
            this.dynamicFields.put("experience", experienceField);
        }
        else if ("Laboratory".equals(userType)) {
            final JTextField labNameField = UIHelper.createTextField();
            UIHelper.addFormField(this.dynamicFieldsPanel, "Laboratory Name", labNameField);
            this.dynamicFields.put("labName", labNameField);
            final JTextField locationField = UIHelper.createTextField();
            UIHelper.addFormField(this.dynamicFieldsPanel, "Complete Address", locationField);
            this.dynamicFields.put("location", locationField);
            final JTextField cityField2 = UIHelper.createTextField();
            UIHelper.addFormField(this.dynamicFieldsPanel, "City", cityField2);
            this.dynamicFields.put("city", cityField2);
            final JTextArea servicesArea = UIHelper.createTextArea();
            UIHelper.addFormField(this.dynamicFieldsPanel, "Services Offered", servicesArea);
            this.dynamicFields.put("services", servicesArea);
        }
        this.dynamicFieldsPanel.revalidate();
        this.dynamicFieldsPanel.repaint();
    }
    
    private void handleSignUp() {
        UIHelper.showSuccess(this, "Account created successfully!\nPlease check your email for verification code.");
        this.dispose();
        new LoginWindow().setVisible(true);
    }
}
