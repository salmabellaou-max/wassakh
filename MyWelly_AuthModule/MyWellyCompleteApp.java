import javax.swing.SwingUtilities;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;

// 
// Decompiled by Procyon v0.6.0
// 

public class MyWellyCompleteApp
{
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel((LookAndFeel)new FlatLightLaf());
            UIManager.put("Button.arc", 15);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new SplashScreen().showSplash());
    }
}
