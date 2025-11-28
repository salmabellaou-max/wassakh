import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.Box;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JFrame;
import java.util.List;

// 
// Decompiled by Procyon v0.6.0
// 

class SearchDoctorsWindow extends JFrame
{
    private JTextField searchField;
    private JComboBox<String> cityCombo;
    private JComboBox<String> specialtyCombo;
    private JComboBox<String> certificateCombo;
    private JCheckBox acceptingPatientsCheck;
    private JPanel resultsPanel;
    
    public SearchDoctorsWindow() {
        this.setTitle("MyWelly - Search Doctors");
        this.setSize(1300, 850);
        this.setLocationRelativeTo(null);
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIConstants.SOFT_GRAY);
        mainPanel.add(this.createSearchPanel(), "North");
        (this.resultsPanel = new JPanel()).setLayout(new BoxLayout(this.resultsPanel, 1));
        this.resultsPanel.setBackground(UIConstants.SOFT_GRAY);
        this.resultsPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        final JScrollPane scrollPane = new JScrollPane(this.resultsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, "Center");
        this.setContentPane(mainPanel);
        this.showSampleResults();
    }
    
    private JPanel createSearchPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 230, 230)), BorderFactory.createEmptyBorder(35, 35, 35, 35)));
        final JLabel titleLabel = new JLabel("Find Your Perfect Doctor");
        titleLabel.setFont(new Font("Segoe UI", 1, 32));
        titleLabel.setForeground(UIConstants.PRIMARY_GREEN);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        final JLabel subtitleLabel = new JLabel("Search by name, specialty, location, or certificates");
        subtitleLabel.setFont(new Font("Segoe UI", 0, 15));
        subtitleLabel.setForeground(Color.GRAY);
        panel.add(subtitleLabel);
        panel.add(Box.createVerticalStrut(28));
        final JPanel searchBoxPanel = new JPanel(new BorderLayout(15, 0));
        searchBoxPanel.setBackground(Color.WHITE);
        searchBoxPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        (this.searchField = new JTextField()).setFont(new Font("Segoe UI", 0, 16));
        this.searchField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIConstants.LIGHT_GREEN, 2, true), BorderFactory.createEmptyBorder(12, 20, 12, 20)));
        searchBoxPanel.add(this.searchField, "Center");
        final JButton searchButton = UIHelper.createStyledButton("\ud83d\udd0d Search", UIConstants.PRIMARY_GREEN, Color.WHITE);
        searchButton.setPreferredSize(new Dimension(140, 55));
        searchButton.setFont(new Font("Segoe UI", 1, 16));
        searchButton.addActionListener(e -> this.performSearch());
        searchBoxPanel.add(searchButton, "East");
        panel.add(searchBoxPanel);
        panel.add(Box.createVerticalStrut(22));
        final JPanel filterPanel = new JPanel(new FlowLayout(0, 18, 0));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        (this.cityCombo = new JComboBox<String>(new String[] { "All Cities", "Casablanca", "Rabat", "Marrakech", "Fes", "Tangier", "Agadir" })).setFont(new Font("Segoe UI", 0, 14));
        this.cityCombo.setPreferredSize(new Dimension(160, 45));
        filterPanel.add(new JLabel("City:"));
        filterPanel.add(this.cityCombo);
        (this.specialtyCombo = new JComboBox<String>(new String[] { "All Specialties", "Cardiology", "Dermatology", "Pediatrics", "Orthopedics", "Neurology" })).setFont(new Font("Segoe UI", 0, 14));
        this.specialtyCombo.setPreferredSize(new Dimension(180, 45));
        filterPanel.add(new JLabel("Specialty:"));
        filterPanel.add(this.specialtyCombo);
        (this.certificateCombo = new JComboBox<String>(new String[] { "Any Certificate", "Board Certified", "Fellowship", "PhD", "Master's" })).setFont(new Font("Segoe UI", 0, 14));
        this.certificateCombo.setPreferredSize(new Dimension(180, 45));
        filterPanel.add(new JLabel("Certificate:"));
        filterPanel.add(this.certificateCombo);
        (this.acceptingPatientsCheck = new JCheckBox("Accepting new patients")).setFont(new Font("Segoe UI", 0, 14));
        this.acceptingPatientsCheck.setBackground(Color.WHITE);
        filterPanel.add(this.acceptingPatientsCheck);
        panel.add(filterPanel);
        return panel;
    }
    
    private void performSearch() {
        // Build search filters from UI components
        SearchFilters filters = new SearchFilters();

        String searchText = this.searchField.getText().trim();
        if (!searchText.isEmpty()) {
            filters.setSearchQuery(searchText);
            filters.setSearchType("specialty");  // Can be extended to switch between name/specialty
        }

        String selectedCity = (String) this.cityCombo.getSelectedItem();
        if (selectedCity != null && !selectedCity.equals("All Cities")) {
            filters.setCity(selectedCity);
        }

        String selectedCertificate = (String) this.certificateCombo.getSelectedItem();
        if (selectedCertificate != null && !selectedCertificate.equals("Any Certificate")) {
            filters.addCertificateName(selectedCertificate);
        }

        // Set other filters as needed
        filters.setAcceptingPatients(this.acceptingPatientsCheck.isSelected());

        // Perform search in background thread
        new Thread(() -> {
            try {
                SearchService searchService = new SearchService();
                List<Doctor> doctors = searchService.searchDoctors(filters);

                // Update UI on EDT
                javax.swing.SwingUtilities.invokeLater(() -> this.showSearchResults(doctors));
            } catch (Exception e) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    this.resultsPanel.removeAll();
                    JLabel errorLabel = new JLabel("Error searching doctors: " + e.getMessage());
                    errorLabel.setFont(new Font("Segoe UI", 0, 16));
                    errorLabel.setForeground(Color.RED);
                    this.resultsPanel.add(errorLabel);
                    this.resultsPanel.revalidate();
                    this.resultsPanel.repaint();
                });
            }
        }).start();
    }

    private void showSearchResults(List<Doctor> doctors) {
        this.resultsPanel.removeAll();

        if (doctors.isEmpty()) {
            JLabel noResultsLabel = new JLabel("No doctors found matching your criteria");
            noResultsLabel.setFont(new Font("Segoe UI", 0, 18));
            noResultsLabel.setForeground(Color.GRAY);
            this.resultsPanel.add(noResultsLabel);
        } else {
            JLabel resultsLabel = new JLabel("Showing " + doctors.size() + " doctor" + (doctors.size() > 1 ? "s" : ""));
            resultsLabel.setFont(new Font("Segoe UI", 1, 16));
            resultsLabel.setForeground(Color.GRAY);
            this.resultsPanel.add(resultsLabel);
            this.resultsPanel.add(Box.createVerticalStrut(20));

            for (Doctor doctor : doctors) {
                String certificateText = "";
                if (doctor.getCertificates() != null && !doctor.getCertificates().isEmpty()) {
                    certificateText = doctor.getCertificates().get(0).getName();
                } else {
                    certificateText = "Medical Degree";
                }

                this.resultsPanel.add(this.createModernDoctorCard(
                    doctor.getFullName(),
                    doctor.getSpecialty(),
                    doctor.getClinicName() + " \u2022 " + doctor.getCity(),
                    String.format("%.1f", doctor.getRating()),
                    String.format("%.0f MAD", doctor.getConsultationFees()),
                    doctor.getYearsOfExperience() + " years",
                    certificateText
                ));
                this.resultsPanel.add(Box.createVerticalStrut(18));
            }
        }

        this.resultsPanel.revalidate();
        this.resultsPanel.repaint();
    }

    private void showSampleResults() {
        this.resultsPanel.removeAll();
        final JLabel resultsLabel = new JLabel("Showing 15 doctors");
        resultsLabel.setFont(new Font("Segoe UI", 1, 16));
        resultsLabel.setForeground(Color.GRAY);
        this.resultsPanel.add(resultsLabel);
        this.resultsPanel.add(Box.createVerticalStrut(20));
        final String[][] array;
        final String[][] doctors = array = new String[][] { { "Dr. Ahmed Benani", "Cardiology", "Heart Care Clinic \u2022 Casablanca", "4.8", "500 MAD", "15 years", "Board Certified in Cardiology" }, { "Dr. Fatima El Ouardi", "Dermatology", "Skin Health Center \u2022 Rabat", "4.9", "400 MAD", "12 years", "Fellowship in Dermatology" }, { "Dr. Mohammed Alami", "Pediatrics", "Children's Hospital \u2022 Casablanca", "4.7", "350 MAD", "10 years", "Pediatrics Specialist" }, { "Dr. Sara Benjelloun", "Neurology", "Neuro Clinic \u2022 Marrakech", "4.9", "600 MAD", "18 years", "PhD in Neuroscience" }, { "Dr. Youssef Tazi", "Orthopedics", "Bone & Joint Center \u2022 Fes", "4.6", "450 MAD", "8 years", "Orthopedic Surgery Certificate" } };
        for (final String[] doctor : array) {
            this.resultsPanel.add(this.createModernDoctorCard(doctor[0], doctor[1], doctor[2], doctor[3], doctor[4], doctor[5], doctor[6]));
            this.resultsPanel.add(Box.createVerticalStrut(18));
        }
        this.resultsPanel.revalidate();
        this.resultsPanel.repaint();
    }
    
    private JPanel createModernDoctorCard(final String name, final String specialty, final String clinic, final String rating, final String fees, final String experience, final String certificate) {
        final JPanel card = new JPanel(new BorderLayout(25, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true), BorderFactory.createEmptyBorder(25, 25, 25, 25)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        final JPanel leftPanel = new JPanel(new BorderLayout(20, 0));
        leftPanel.setBackground(Color.WHITE);
        final JPanel avatarPanel = new JPanel();
        avatarPanel.setLayout(new BoxLayout(avatarPanel, 1));
        avatarPanel.setBackground(UIConstants.VERY_LIGHT_GREEN);
        avatarPanel.setPreferredSize(new Dimension(90, 90));
        avatarPanel.setBorder(BorderFactory.createLineBorder(UIConstants.LIGHT_GREEN, 2, true));
        final JLabel avatarLabel = new JLabel("\ud83d\udc68\u200d\u2695\ufe0f");
        avatarLabel.setFont(new Font("Segoe UI Emoji", 0, 45));
        avatarLabel.setAlignmentX(0.5f);
        avatarPanel.add(Box.createVerticalGlue());
        avatarPanel.add(avatarLabel);
        avatarPanel.add(Box.createVerticalGlue());
        leftPanel.add(avatarPanel, "West");
        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, 1));
        infoPanel.setBackground(Color.WHITE);
        final JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", 1, 20));
        nameLabel.setForeground(UIConstants.PRIMARY_GREEN);
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(6));
        final JLabel specialtyLabel = new JLabel("\ud83e\ude7a " + specialty);
        specialtyLabel.setFont(new Font("Segoe UI", 0, 15));
        specialtyLabel.setForeground(Color.BLACK);
        infoPanel.add(specialtyLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        final JLabel clinicLabel = new JLabel("\ud83d\udccd " + clinic);
        clinicLabel.setFont(new Font("Segoe UI", 0, 14));
        clinicLabel.setForeground(Color.GRAY);
        infoPanel.add(clinicLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        final JLabel certLabel = new JLabel("\ud83c\udf93 " + certificate);
        certLabel.setFont(new Font("Segoe UI", 2, 13));
        certLabel.setForeground(UIConstants.SUCCESS_GREEN);
        infoPanel.add(certLabel);
        leftPanel.add(infoPanel, "Center");
        card.add(leftPanel, "Center");
        final JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, 1));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(200, 90));
        final JPanel statsPanel = new JPanel(new FlowLayout(2, 15, 0));
        statsPanel.setBackground(Color.WHITE);
        final JLabel ratingLabel = new JLabel("\u2b50 " + rating);
        ratingLabel.setFont(new Font("Segoe UI", 1, 15));
        ratingLabel.setForeground(new Color(255, 193, 7));
        statsPanel.add(ratingLabel);
        final JLabel feesLabel = new JLabel("\ud83d\udcb0 " + fees);
        feesLabel.setFont(new Font("Segoe UI", 1, 15));
        feesLabel.setForeground(UIConstants.PRIMARY_GREEN);
        statsPanel.add(feesLabel);
        final JLabel expLabel = new JLabel("\ud83d\udcc5 " + experience);
        expLabel.setFont(new Font("Segoe UI", 0, 14));
        expLabel.setForeground(Color.GRAY);
        statsPanel.add(expLabel);
        rightPanel.add(statsPanel);
        rightPanel.add(Box.createVerticalStrut(20));
        final JButton viewButton = UIHelper.createStyledButton("View Profile", UIConstants.LIGHT_GREEN, Color.WHITE);
        viewButton.setMaximumSize(new Dimension(180, 42));
        viewButton.setAlignmentX(1.0f);
        rightPanel.add(viewButton);
        card.add(rightPanel, "East");
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIConstants.LIGHT_GREEN, 2, true), BorderFactory.createEmptyBorder(24, 24, 24, 24)));
            }

            @Override
            public void mouseExited(final MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true), BorderFactory.createEmptyBorder(25, 25, 25, 25)));
            }
        });
        return card;
    }
}
