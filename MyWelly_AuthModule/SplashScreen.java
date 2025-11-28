import javax.swing.SwingUtilities;
import javax.swing.Box;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

// 
// Decompiled by Procyon v0.6.0
// 

class SplashScreen extends JWindow
{
    private DrawingLogoPanel logoPanel;
    private JProgressBar progressBar;
    
    public SplashScreen() {
        this.setSize(700, 800);
        this.setLocationRelativeTo(null);
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIConstants.PRIMARY_GREEN, 4), BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        (this.logoPanel = new DrawingLogoPanel()).setPreferredSize(new Dimension(660, 600));
        mainPanel.add(this.logoPanel, "Center");
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, 1));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        final JLabel tagline = new JLabel("Smart Medical Appointment Platform");
        tagline.setFont(new Font("Segoe UI", 2, 18));
        tagline.setForeground(UIConstants.LIGHT_GREEN);
        tagline.setAlignmentX(0.5f);
        bottomPanel.add(tagline);
        bottomPanel.add(Box.createVerticalStrut(20));
        final JLabel subtitle = new JLabel("For Morocco Healthcare");
        subtitle.setFont(new Font("Segoe UI", 0, 15));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(0.5f);
        bottomPanel.add(subtitle);
        bottomPanel.add(Box.createVerticalStrut(30));
        (this.progressBar = new JProgressBar(0, 100)).setStringPainted(true);
        this.progressBar.setForeground(UIConstants.PRIMARY_GREEN);
        this.progressBar.setBackground(UIConstants.VERY_LIGHT_GREEN);
        this.progressBar.setPreferredSize(new Dimension(580, 30));
        this.progressBar.setMaximumSize(new Dimension(580, 30));
        this.progressBar.setFont(new Font("Segoe UI", 1, 13));
        this.progressBar.setAlignmentX(0.5f);
        bottomPanel.add(this.progressBar);
        bottomPanel.add(Box.createVerticalStrut(15));
        final JLabel loadingText = new JLabel("Initializing...");
        loadingText.setFont(new Font("Segoe UI", 2, 14));
        loadingText.setForeground(Color.GRAY);
        loadingText.setAlignmentX(0.5f);
        bottomPanel.add(loadingText);
        mainPanel.add(bottomPanel, "South");
        this.setContentPane(mainPanel);
    }
    
    public void showSplash() {
        this.setVisible(true);
        // Logo is now displayed immediately without animation
        new Thread(() -> {
            try {
                for (int i = 0; i <= 100; ++i) {
                    final int progress = i;
                    SwingUtilities.invokeLater(() -> {
                        this.progressBar.setValue(progress);
                        this.progressBar.setString("Loading " + progress + "%");
                        return;
                    });
                    Thread.sleep(35L);
                }
                Thread.sleep(800L);
                SwingUtilities.invokeLater(() -> {
                    this.dispose();
                    new LoginWindow().setVisible(true);
                });
            }
            catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
