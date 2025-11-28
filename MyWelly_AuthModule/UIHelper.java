import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;

// 
// Decompiled by Procyon v0.6.0
// 

class UIHelper
{
    public static JButton createStyledButton(final String text, final Color bgColor, final Color fgColor) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", 1, 16));
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(12));
        button.addMouseListener(new MouseAdapter() {
            Color originalBg = bgColor;
            
            @Override
            public void mouseEntered(final MouseEvent e) {
                button.setBackground(UIConstants.HOVER_GREEN);
            }
            
            @Override
            public void mouseExited(final MouseEvent e) {
                button.setBackground(this.originalBg);
            }
        });
        return button;
    }
    
    public static JTextField createTextField() {
        final JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", 0, 16));
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIConstants.LIGHT_GREEN, 2, true), BorderFactory.createEmptyBorder(12, 18, 12, 18)));
        field.setMaximumSize(new Dimension(550, 50));
        return field;
    }
    
    public static JPasswordField createPasswordField() {
        final JPasswordField field = new JPasswordField();
        field.setFont(new Font("Segoe UI", 0, 16));
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIConstants.LIGHT_GREEN, 2, true), BorderFactory.createEmptyBorder(12, 18, 12, 18)));
        field.setMaximumSize(new Dimension(550, 50));
        return field;
    }
    
    public static JTextArea createTextArea() {
        final JTextArea area = new JTextArea(3, 20);
        area.setFont(new Font("Segoe UI", 0, 16));
        area.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIConstants.LIGHT_GREEN, 2, true), BorderFactory.createEmptyBorder(12, 18, 12, 18)));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMaximumSize(new Dimension(550, 100));
        return area;
    }
    
    public static void addFormField(final JPanel panel, final String label, final JComponent field) {
        final JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("Segoe UI", 1, 15));
        fieldLabel.setForeground(UIConstants.PRIMARY_GREEN);
        panel.add(fieldLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(field);
        panel.add(Box.createVerticalStrut(22));
    }
    
    public static JPanel createModernStatCard(final String label, final String value, final String subtitle, final String emoji, final Color color) {
        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, 1));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true), BorderFactory.createEmptyBorder(28, 28, 28, 28)));
        final JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        final JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("Segoe UI Emoji", 0, 40));
        topPanel.add(emojiLabel, "West");
        card.add(topPanel);
        card.add(Box.createVerticalStrut(18));
        final JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", 1, 42));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(0.0f);
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(8));
        final JLabel labelLabel = new JLabel(label + " " + subtitle);
        labelLabel.setFont(new Font("Segoe UI", 0, 15));
        labelLabel.setForeground(Color.GRAY);
        labelLabel.setAlignmentX(0.0f);
        card.add(labelLabel);
        return card;
    }
    
    public static JButton createActionButton(final String text, final ActionListener action) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", 1, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(UIConstants.LIGHT_GREEN);
        button.setPreferredSize(new Dimension(220, 55));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(12));
        if (action != null) {
            button.addActionListener(action);
        }
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                button.setBackground(UIConstants.HOVER_GREEN);
            }
            
            @Override
            public void mouseExited(final MouseEvent e) {
                button.setBackground(UIConstants.LIGHT_GREEN);
            }
        });
        return button;
    }
    
    public static JDialog showLoadingDialog(final JFrame parent, final String message) {
        final JDialog dialog = new JDialog(parent, "Please Wait", false);
        dialog.setSize(320, 140);
        dialog.setLocationRelativeTo(parent);
        dialog.setUndecorated(true);
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIConstants.PRIMARY_GREEN, 3), BorderFactory.createEmptyBorder(25, 25, 25, 25)));
        final JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setForeground(UIConstants.PRIMARY_GREEN);
        progressBar.setPreferredSize(new Dimension(270, 12));
        progressBar.setMaximumSize(new Dimension(270, 12));
        panel.add(progressBar);
        panel.add(Box.createVerticalStrut(20));
        final JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", 1, 15));
        messageLabel.setAlignmentX(0.5f);
        panel.add(messageLabel);
        dialog.setContentPane(panel);
        dialog.setVisible(true);
        return dialog;
    }
    
    public static void showError(final JFrame parent, final String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", 0);
    }
    
    public static void showSuccess(final JFrame parent, final String message) {
        JOptionPane.showMessageDialog(parent, message, "Success", 1);
    }
}
