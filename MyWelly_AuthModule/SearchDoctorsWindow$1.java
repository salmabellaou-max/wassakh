import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.event.MouseEvent;
import java.util.Objects;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;

// 
// Decompiled by Procyon v0.6.0
// 

class SearchDoctorsWindow$1 extends MouseAdapter {
    final /* synthetic */ JPanel val$card;
    
    @Override
    public void mouseEntered(final MouseEvent e) {
        this.val$card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIConstants.LIGHT_GREEN, 2, true), BorderFactory.createEmptyBorder(24, 24, 24, 24)));
    }
    
    @Override
    public void mouseExited(final MouseEvent e) {
        this.val$card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true), BorderFactory.createEmptyBorder(25, 25, 25, 25)));
    }
}