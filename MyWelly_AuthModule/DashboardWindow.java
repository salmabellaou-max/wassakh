import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.FlowLayout;
import javax.swing.border.Border;
import java.util.Iterator;
import java.awt.event.ActionListener;
import java.awt.Cursor;
import java.awt.Color;
import javax.swing.Box;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JButton;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.6.0
// 

class DashboardWindow extends JFrame
{
    private String userType;
    private String username;
    private JPanel contentArea;
    private List<JButton> menuButtons;
    
    public DashboardWindow(final String userType, final String username) {
        this.menuButtons = new ArrayList<JButton>();
        this.userType = userType;
        this.username = username;
        this.setTitle("MyWelly - Dashboard");
        this.setSize(1450, 900);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setExtendedState(6);
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(this.createSidebar(), "West");
        final JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UIConstants.SOFT_GRAY);
        contentPanel.add(this.createTopBar(), "North");
        (this.contentArea = new JPanel(new BorderLayout())).setBackground(UIConstants.SOFT_GRAY);
        this.showDashboardContent();
        contentPanel.add(this.contentArea, "Center");
        mainPanel.add(contentPanel, "Center");
        this.setContentPane(mainPanel);
    }
    
    private JPanel createSidebar() {
        final JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, 1));
        sidebar.setBackground(UIConstants.PRIMARY_GREEN);
        sidebar.setPreferredSize(new Dimension(290, this.getHeight()));
        sidebar.setBorder(BorderFactory.createEmptyBorder(35, 25, 35, 25));
        final JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, 1));
        logoPanel.setBackground(UIConstants.PRIMARY_GREEN);
        final JLabel logoLabel = new JLabel("\ud83d\udc9a");
        logoLabel.setFont(new Font("Segoe UI Emoji", 0, 40));
        logoLabel.setAlignmentX(0.5f);
        logoPanel.add(logoLabel);
        logoPanel.add(Box.createVerticalStrut(8));
        final JLabel appLabel = new JLabel("MyWelly");
        appLabel.setFont(new Font("Segoe Script", 1, 28));
        appLabel.setForeground(Color.WHITE);
        appLabel.setAlignmentX(0.5f);
        logoPanel.add(appLabel);
        sidebar.add(logoPanel);
        sidebar.add(Box.createVerticalStrut(50));
        if ("Patient".equals(this.userType)) {
            this.addMenuItem(sidebar, "\ud83c\udfe0", "Dashboard", true, e -> this.showDashboardContent());
            this.addMenuItem(sidebar, "\ud83d\udd0d", "Search Doctors", false, e -> this.showSearchDoctors());
            this.addMenuItem(sidebar, "\ud83d\udcc5", "My Appointments", false, e -> this.showAppointments());
            this.addMenuItem(sidebar, "\ud83d\udccb", "Medical Records", false, e -> this.showMedicalRecords());
            this.addMenuItem(sidebar, "\u2b50", "My Reviews", false, e -> this.showReviews());
            this.addMenuItem(sidebar, "\ud83d\udc64", "My Profile", false, e -> this.showProfile());
        }
        else if ("Doctor".equals(this.userType)) {
            this.addMenuItem(sidebar, "\ud83c\udfe0", "Dashboard", true, e -> this.showDashboardContent());
            this.addMenuItem(sidebar, "\ud83d\udcc5", "Appointments", false, e -> this.showAppointments());
            this.addMenuItem(sidebar, "\ud83d\udc65", "Patients", false, e -> this.showPatients());
            this.addMenuItem(sidebar, "\ud83c\udf93", "Certificates", false, e -> this.showCertificates());
            this.addMenuItem(sidebar, "\u2b50", "Reviews", false, e -> this.showReviews());
            this.addMenuItem(sidebar, "\ud83d\udc64", "My Profile", false, e -> this.showProfile());
        }
        sidebar.add(Box.createVerticalGlue());
        final JButton logoutBtn = new JButton("\ud83d\udeaa  Logout");
        logoutBtn.setFont(new Font("Segoe UI", 1, 15));
        logoutBtn.setForeground(UIConstants.PRIMARY_GREEN);
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setMaximumSize(new Dimension(240, 50));
        logoutBtn.setCursor(new Cursor(12));
        logoutBtn.addActionListener(e -> {
            this.dispose();
            new LoginWindow().setVisible(true);
            return;
        });
        sidebar.add(logoutBtn);
        return sidebar;
    }
    
    private void addMenuItem(final JPanel sidebar, final String icon, final String text, final boolean selected, final ActionListener action) {
        final JButton menuItem = new JButton(icon + "  " + text);
        menuItem.setFont(new Font("Segoe UI", 1, 16));
        menuItem.setForeground(Color.WHITE);
        menuItem.setBackground(selected ? UIConstants.LIGHT_GREEN : UIConstants.PRIMARY_GREEN);
        menuItem.setHorizontalAlignment(2);
        menuItem.setBorderPainted(false);
        menuItem.setFocusPainted(false);
        menuItem.setMaximumSize(new Dimension(240, 50));
        menuItem.setCursor(new Cursor(12));
        menuItem.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        menuItem.addActionListener(e -> {
            for (final JButton btn : this.menuButtons) {
                btn.setBackground(UIConstants.PRIMARY_GREEN);
            }
            menuItem.setBackground(UIConstants.LIGHT_GREEN);
            action.actionPerformed(e);
            return;
        });
        this.menuButtons.add(menuItem);
        sidebar.add(menuItem);
        sidebar.add(Box.createVerticalStrut(12));
    }
    
    private JPanel createTopBar() {
        final JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)), BorderFactory.createEmptyBorder(20, 35, 20, 35)));
        final JPanel leftPanel = new JPanel(new FlowLayout(0, 15, 0));
        leftPanel.setBackground(Color.WHITE);
        final JLabel welcomeLabel = new JLabel("Welcome back, " + this.username);
        welcomeLabel.setFont(new Font("Segoe UI", 1, 22));
        welcomeLabel.setForeground(UIConstants.PRIMARY_GREEN);
        leftPanel.add(welcomeLabel);
        topBar.add(leftPanel, "West");
        final JPanel rightPanel = new JPanel(new FlowLayout(2, 20, 0));
        rightPanel.setBackground(Color.WHITE);
        final JLabel dateLabel = new JLabel("\ud83d\udcc5 " + new SimpleDateFormat("EEEE, MMMM dd, yyyy").format(new Date()));
        dateLabel.setFont(new Font("Segoe UI", 0, 15));
        dateLabel.setForeground(Color.GRAY);
        rightPanel.add(dateLabel);
        final JLabel notifLabel = new JLabel("\ud83d\udd14");
        notifLabel.setFont(new Font("Segoe UI Emoji", 0, 22));
        notifLabel.setCursor(new Cursor(12));
        rightPanel.add(notifLabel);
        topBar.add(rightPanel, "East");
        return topBar;
    }
    
    private void showDashboardContent() {
        this.contentArea.removeAll();
        final JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, 1));
        content.setBackground(UIConstants.SOFT_GRAY);
        content.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));
        final JPanel statsPanel = new JPanel(new GridLayout(1, 4, 25, 0));
        statsPanel.setBackground(UIConstants.SOFT_GRAY);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        if ("Patient".equals(this.userType)) {
            statsPanel.add(UIHelper.createModernStatCard("Upcoming", "3", "Appointments", "\ud83d\udcc5", UIConstants.PRIMARY_GREEN));
            statsPanel.add(UIHelper.createModernStatCard("Completed", "12", "Visits", "\u2705", UIConstants.SUCCESS_GREEN));
            statsPanel.add(UIHelper.createModernStatCard("Doctors", "5", "Visited", "\ud83d\udc68\u200d\u2695\ufe0f", UIConstants.LIGHT_GREEN));
            statsPanel.add(UIHelper.createModernStatCard("Records", "8", "Files", "\ud83d\udccb", new Color(33, 150, 243)));
        }
        content.add(statsPanel);
        content.add(Box.createVerticalStrut(35));
        final JLabel quickActionsLabel = new JLabel("Quick Actions");
        quickActionsLabel.setFont(UIConstants.HEADER_FONT);
        quickActionsLabel.setForeground(UIConstants.PRIMARY_GREEN);
        content.add(quickActionsLabel);
        content.add(Box.createVerticalStrut(20));
        final JPanel actionsPanel = new JPanel(new FlowLayout(0, 20, 20));
        actionsPanel.setBackground(UIConstants.SOFT_GRAY);
        actionsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        actionsPanel.add(UIHelper.createActionButton("\ud83d\udd0d  Find a Doctor", e -> this.showSearchDoctors()));
        actionsPanel.add(UIHelper.createActionButton("\ud83d\udcc5  Book Appointment", e -> this.showBookAppointment()));
        actionsPanel.add(UIHelper.createActionButton("\ud83d\udccb  View Records", e -> this.showMedicalRecords()));
        actionsPanel.add(UIHelper.createActionButton("\ud83d\udcac  Contact Support", e -> this.showContactSupport()));
        content.add(actionsPanel);
        content.add(Box.createVerticalStrut(35));
        final JLabel recentLabel = new JLabel("Recent Activity");
        recentLabel.setFont(UIConstants.HEADER_FONT);
        recentLabel.setForeground(UIConstants.PRIMARY_GREEN);
        content.add(recentLabel);
        content.add(Box.createVerticalStrut(20));
        final JPanel activityPanel = this.createActivityPanel();
        content.add(activityPanel);
        content.add(Box.createVerticalGlue());
        final JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.contentArea.add(scrollPane, "Center");
        this.contentArea.revalidate();
        this.contentArea.repaint();
    }
    
    private JPanel createActivityPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true), BorderFactory.createEmptyBorder(25, 25, 25, 25)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        final String[][] array;
        final String[][] activities = array = new String[][] { { "Appointment with Dr. Ahmed", "Tomorrow, 10:00 AM", "\ud83d\udcc5" }, { "Lab results received", "2 hours ago", "\ud83d\udccb" }, { "Review submitted", "Yesterday", "\u2b50" } };
        for (final String[] activity : array) {
            final JPanel activityItem = new JPanel(new BorderLayout(15, 0));
            activityItem.setBackground(Color.WHITE);
            activityItem.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
            final JLabel icon = new JLabel(activity[2]);
            icon.setFont(new Font("Segoe UI Emoji", 0, 28));
            activityItem.add(icon, "West");
            final JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, 1));
            textPanel.setBackground(Color.WHITE);
            final JLabel titleLabel = new JLabel(activity[0]);
            titleLabel.setFont(new Font("Segoe UI", 1, 15));
            titleLabel.setForeground(Color.BLACK);
            textPanel.add(titleLabel);
            final JLabel timeLabel = new JLabel(activity[1]);
            timeLabel.setFont(new Font("Segoe UI", 0, 13));
            timeLabel.setForeground(Color.GRAY);
            textPanel.add(timeLabel);
            activityItem.add(textPanel, "Center");
            panel.add(activityItem);
            if (!activity.equals(activities[activities.length - 1])) {
                final JSeparator sep = new JSeparator();
                sep.setForeground(new Color(240, 240, 240));
                panel.add(sep);
            }
        }
        return panel;
    }
    
    private void showSearchDoctors() {
        new SearchDoctorsWindow().setVisible(true);
    }
    
    private void showAppointments() {
        this.contentArea.removeAll();
        final JLabel label = new JLabel("Appointments - Coming Soon");
        label.setFont(UIConstants.HEADER_FONT);
        label.setForeground(UIConstants.PRIMARY_GREEN);
        final JPanel panel = new JPanel();
        panel.setBackground(UIConstants.SOFT_GRAY);
        panel.add(label);
        this.contentArea.add(panel, "Center");
        this.contentArea.revalidate();
        this.contentArea.repaint();
    }
    
    private void showMedicalRecords() {
        this.contentArea.removeAll();
        final JLabel label = new JLabel("Medical Records - Coming Soon");
        label.setFont(UIConstants.HEADER_FONT);
        label.setForeground(UIConstants.PRIMARY_GREEN);
        final JPanel panel = new JPanel();
        panel.setBackground(UIConstants.SOFT_GRAY);
        panel.add(label);
        this.contentArea.add(panel, "Center");
        this.contentArea.revalidate();
        this.contentArea.repaint();
    }
    
    private void showReviews() {
        this.contentArea.removeAll();
        final JLabel label = new JLabel("Reviews - Coming Soon");
        label.setFont(UIConstants.HEADER_FONT);
        label.setForeground(UIConstants.PRIMARY_GREEN);
        final JPanel panel = new JPanel();
        panel.setBackground(UIConstants.SOFT_GRAY);
        panel.add(label);
        this.contentArea.add(panel, "Center");
        this.contentArea.revalidate();
        this.contentArea.repaint();
    }
    
    private void showProfile() {
        this.contentArea.removeAll();
        final JLabel label = new JLabel("Profile - Coming Soon");
        label.setFont(UIConstants.HEADER_FONT);
        label.setForeground(UIConstants.PRIMARY_GREEN);
        final JPanel panel = new JPanel();
        panel.setBackground(UIConstants.SOFT_GRAY);
        panel.add(label);
        this.contentArea.add(panel, "Center");
        this.contentArea.revalidate();
        this.contentArea.repaint();
    }
    
    private void showPatients() {
        this.contentArea.removeAll();
        final JLabel label = new JLabel("Patients - Coming Soon");
        label.setFont(UIConstants.HEADER_FONT);
        label.setForeground(UIConstants.PRIMARY_GREEN);
        final JPanel panel = new JPanel();
        panel.setBackground(UIConstants.SOFT_GRAY);
        panel.add(label);
        this.contentArea.add(panel, "Center");
        this.contentArea.revalidate();
        this.contentArea.repaint();
    }
    
    private void showCertificates() {
        this.contentArea.removeAll();
        final JLabel label = new JLabel("Certificates - Coming Soon");
        label.setFont(UIConstants.HEADER_FONT);
        label.setForeground(UIConstants.PRIMARY_GREEN);
        final JPanel panel = new JPanel();
        panel.setBackground(UIConstants.SOFT_GRAY);
        panel.add(label);
        this.contentArea.add(panel, "Center");
        this.contentArea.revalidate();
        this.contentArea.repaint();
    }

    private void showBookAppointment() {
        this.contentArea.removeAll();
        final JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, 1));
        content.setBackground(UIConstants.SOFT_GRAY);
        content.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));

        final JLabel titleLabel = new JLabel("Book an Appointment");
        titleLabel.setFont(UIConstants.HEADER_FONT);
        titleLabel.setForeground(UIConstants.PRIMARY_GREEN);
        content.add(titleLabel);
        content.add(Box.createVerticalStrut(20));

        final JLabel infoLabel = new JLabel("<html>To book an appointment:<br><br>" +
                "1. Use 'Search Doctors' to find a doctor<br>" +
                "2. Click 'View Profile' on a doctor card<br>" +
                "3. Select an available time slot<br><br>" +
                "This feature is being implemented.</html>");
        infoLabel.setFont(new Font("Segoe UI", 0, 16));
        infoLabel.setForeground(Color.GRAY);
        content.add(infoLabel);
        content.add(Box.createVerticalStrut(30));

        final JButton searchButton = UIHelper.createStyledButton("Search Doctors Now", UIConstants.PRIMARY_GREEN, Color.WHITE);
        searchButton.setMaximumSize(new Dimension(250, 50));
        searchButton.setAlignmentX(0.0f);
        searchButton.addActionListener(e -> this.showSearchDoctors());
        content.add(searchButton);

        content.add(Box.createVerticalGlue());

        final JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.contentArea.add(scrollPane, "Center");
        this.contentArea.revalidate();
        this.contentArea.repaint();
    }

    private void showContactSupport() {
        this.contentArea.removeAll();
        final JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, 1));
        content.setBackground(UIConstants.SOFT_GRAY);
        content.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));

        final JLabel titleLabel = new JLabel("Contact Support");
        titleLabel.setFont(UIConstants.HEADER_FONT);
        titleLabel.setForeground(UIConstants.PRIMARY_GREEN);
        content.add(titleLabel);
        content.add(Box.createVerticalStrut(30));

        final JPanel contactPanel = new JPanel();
        contactPanel.setLayout(new BoxLayout(contactPanel, 1));
        contactPanel.setBackground(Color.WHITE);
        contactPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        contactPanel.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));
        contactPanel.setAlignmentX(0.0f);

        final JLabel emailLabel = new JLabel("\ud83d\udce7 Email: support@mywelly.ma");
        emailLabel.setFont(new Font("Segoe UI", 0, 18));
        emailLabel.setForeground(UIConstants.PRIMARY_GREEN);
        contactPanel.add(emailLabel);
        contactPanel.add(Box.createVerticalStrut(20));

        final JLabel phoneLabel = new JLabel("\ud83d\udcde Phone: +212 5XX-XXXXXX");
        phoneLabel.setFont(new Font("Segoe UI", 0, 18));
        phoneLabel.setForeground(UIConstants.PRIMARY_GREEN);
        contactPanel.add(phoneLabel);
        contactPanel.add(Box.createVerticalStrut(20));

        final JLabel hoursLabel = new JLabel("\ud83d\udd52 Hours: Mon-Fri, 9:00 AM - 6:00 PM");
        hoursLabel.setFont(new Font("Segoe UI", 0, 18));
        hoursLabel.setForeground(UIConstants.PRIMARY_GREEN);
        contactPanel.add(hoursLabel);
        contactPanel.add(Box.createVerticalStrut(30));

        final JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        contactPanel.add(separator);
        contactPanel.add(Box.createVerticalStrut(30));

        final JLabel messageLabel = new JLabel("Send us a message:");
        messageLabel.setFont(new Font("Segoe UI", 1, 16));
        messageLabel.setForeground(UIConstants.PRIMARY_GREEN);
        contactPanel.add(messageLabel);
        contactPanel.add(Box.createVerticalStrut(15));

        final JLabel noteLabel = new JLabel("<html>Contact form feature is under development.<br>Please use email or phone for now.</html>");
        noteLabel.setFont(new Font("Segoe UI", 2, 14));
        noteLabel.setForeground(Color.GRAY);
        contactPanel.add(noteLabel);

        content.add(contactPanel);
        content.add(Box.createVerticalGlue());

        final JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.contentArea.add(scrollPane, "Center");
        this.contentArea.revalidate();
        this.contentArea.repaint();
    }
}
