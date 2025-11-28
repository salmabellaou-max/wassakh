import java.awt.event.MouseEvent;
import java.util.Objects;
import java.awt.event.MouseAdapter;

// 
// Decompiled by Procyon v0.6.0
// 

class SignUpWindow$1 extends MouseAdapter {
    @Override
    public void mouseClicked(final MouseEvent e) {
        SignUpWindow.this.dispose();
        new LoginWindow().setVisible(true);
    }
}