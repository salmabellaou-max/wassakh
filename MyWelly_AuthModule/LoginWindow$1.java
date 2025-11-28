import java.awt.event.MouseEvent;
import java.util.Objects;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;

// 
// Decompiled by Procyon v0.6.0
// 

class LoginWindow$1 extends MouseAdapter {
    final /* synthetic */ JLabel val$forgotLabel;
    
    @Override
    public void mouseClicked(final MouseEvent e) {
        LoginWindow.this.showForgotPasswordDialog();
    }
    
    @Override
    public void mouseEntered(final MouseEvent e) {
        this.val$forgotLabel.setForeground(UIConstants.HOVER_GREEN);
    }
    
    @Override
    public void mouseExited(final MouseEvent e) {
        this.val$forgotLabel.setForeground(UIConstants.LIGHT_GREEN);
    }
}