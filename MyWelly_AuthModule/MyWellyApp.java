import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.6.0
// 

class MyWellyApp extends JFrame
{
    public MyWellyApp() {
        this.setTitle("MyWelly - Healthcare Management");
        this.setDefaultCloseOperation(3);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        final JLabel label = new JLabel("MyWelly App Loading...", 0);
        label.setFont(new Font("Segoe UI", 1, 32));
        label.setForeground(new Color(0, 102, 68));
        this.add(label);
    }
}
