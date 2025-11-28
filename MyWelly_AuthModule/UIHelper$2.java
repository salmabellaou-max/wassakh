import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;

// 
// Decompiled by Procyon v0.6.0
// 

class UIHelper$2 extends MouseAdapter {
    final /* synthetic */ JButton val$button;
    
    @Override
    public void mouseEntered(final MouseEvent e) {
        this.val$button.setBackground(UIConstants.HOVER_GREEN);
    }
    
    @Override
    public void mouseExited(final MouseEvent e) {
        this.val$button.setBackground(UIConstants.LIGHT_GREEN);
    }
}