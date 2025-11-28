import java.awt.GraphicsEnvironment;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.util.Iterator;
import java.util.List;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.geom.Path2D;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.Objects;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.6.0
// 

public class MyWellySplashScreen extends JFrame
{
    private static final Color PRIMARY_GREEN;
    private static final Color WHITE;
    private DrawingPanel drawingPanel;
    
    public MyWellySplashScreen() {
        this.setTitle("MyWelly");
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(3);
        this.add(this.drawingPanel = new DrawingPanel());
        final Timer timer = new Timer(3500, e -> {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                final MyWellyApp app = new MyWellyApp();
                app.setVisible(true);
            });
            return;
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final MyWellySplashScreen splash = new MyWellySplashScreen();
            splash.setVisible(true);
        });
    }
    
    static {
        PRIMARY_GREEN = new Color(0, 102, 68);
        WHITE = Color.WHITE;
    }
    
    class DrawingPanel extends JPanel
    {
        private float progress;
        private Timer animationTimer;
        private boolean showText;
        
        public DrawingPanel() {
            Objects.requireNonNull(MyWellySplashScreen.this);
            this.progress = 0.0f;
            this.showText = false;
            this.setBackground(MyWellySplashScreen.WHITE);
            (this.animationTimer = new Timer(20, e -> {
                this.progress += 0.015f;
                if (this.progress >= 1.0f) {
                    this.progress = 1.0f;
                    this.showText = true;
                }
                this.repaint();
            })).start();
        }
        
        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            final Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            final int width = this.getWidth();
            final int height = this.getHeight();
            final int centerX = width / 2;
            final int centerY = height / 2;
            final int heartSize = 300;
            this.drawAnimatedHeart(g2d, centerX, centerY - 50, heartSize);
            if (this.showText) {
                this.drawMyWellyText(g2d, centerX, centerY - 30);
            }
        }
        
        private void drawAnimatedHeart(final Graphics2D g2d, final int centerX, final int centerY, final int size) {
            final Path2D heart = new Path2D.Float();
            final float startX = (float)centerX;
            final float startY = centerY + size * 0.5f;
            heart.moveTo(startX, startY);
            heart.curveTo(startX - size * 0.5f, startY - size * 0.3f, startX - size * 0.5f, startY - size * 0.6f, startX - size * 0.25f, startY - size * 0.8f);
            heart.curveTo(startX - size * 0.1f, startY - size * 0.9f, startX, startY - size * 0.85f, startX, startY - size * 0.7f);
            heart.curveTo(startX, startY - size * 0.85f, startX + size * 0.1f, startY - size * 0.9f, startX + size * 0.25f, startY - size * 0.8f);
            heart.curveTo(startX + size * 0.5f, startY - size * 0.6f, startX + size * 0.5f, startY - size * 0.3f, startX, startY);
            final BasicStroke stroke = new BasicStroke(12.0f, 1, 1);
            g2d.setStroke(stroke);
            g2d.setColor(MyWellySplashScreen.PRIMARY_GREEN);
            if (this.progress < 1.0f) {
                final Shape clippedHeart = this.getClippedShape(heart, this.progress);
                g2d.draw(clippedHeart);
            }
            else {
                g2d.draw(heart);
            }
        }
        
        private Shape getClippedShape(final Path2D path, final float progress) {
            final PathIterator pi = path.getPathIterator(null, 1.0);
            final Path2D result = new Path2D.Float();
            final float[] coords = new float[6];
            float totalLength = 0.0f;
            float currentLength = 0.0f;
            final List<float[]> points = new ArrayList<float[]>();
            float lastX = 0.0f;
            float lastY = 0.0f;
            while (!pi.isDone()) {
                final int type = pi.currentSegment(coords);
                if (type == 0) {
                    lastX = coords[0];
                    lastY = coords[1];
                    points.add(new float[] { (float)type, coords[0], coords[1], 0.0f, 0.0f, 0.0f });
                }
                else if (type == 1) {
                    final float dx = coords[0] - lastX;
                    final float dy = coords[1] - lastY;
                    final float length = (float)Math.sqrt(dx * dx + dy * dy);
                    totalLength += length;
                    points.add(new float[] { (float)type, coords[0], coords[1], 0.0f, 0.0f, 0.0f });
                    lastX = coords[0];
                    lastY = coords[1];
                }
                pi.next();
            }
            final float targetLength = totalLength * progress;
            lastX = 0.0f;
            lastY = 0.0f;
            for (final float[] point : points) {
                final int type2 = (int)point[0];
                if (type2 == 0) {
                    result.moveTo(point[1], point[2]);
                    lastX = point[1];
                    lastY = point[2];
                }
                else {
                    if (type2 != 1) {
                        continue;
                    }
                    final float dx2 = point[1] - lastX;
                    final float dy2 = point[2] - lastY;
                    final float length2 = (float)Math.sqrt(dx2 * dx2 + dy2 * dy2);
                    if (currentLength + length2 > targetLength) {
                        final float remaining = targetLength - currentLength;
                        final float ratio = remaining / length2;
                        final float newX = lastX + dx2 * ratio;
                        final float newY = lastY + dy2 * ratio;
                        result.lineTo(newX, newY);
                        break;
                    }
                    result.lineTo(point[1], point[2]);
                    currentLength += length2;
                    lastX = point[1];
                    lastY = point[2];
                }
            }
            return result;
        }
        
        private void drawMyWellyText(final Graphics2D g2d, final int centerX, final int centerY) {
            final float alpha = Math.min(1.0f, (this.progress - 0.8f) * 5.0f);
            g2d.setComposite(AlphaComposite.getInstance(3, alpha));
            Font font = new Font("Brush Script MT", 1, 80);
            if (!this.isFontAvailable("Brush Script MT")) {
                font = new Font("Segoe Script", 1, 80);
            }
            if (!this.isFontAvailable("Segoe Script")) {
                font = new Font("Comic Sans MS", 1, 70);
            }
            g2d.setFont(font);
            g2d.setColor(MyWellySplashScreen.PRIMARY_GREEN);
            final String text = "MyWelly";
            final FontMetrics fm = g2d.getFontMetrics();
            final int textWidth = fm.stringWidth(text);
            final int textX = centerX - textWidth / 2;
            final int textY = centerY + 20;
            g2d.drawString(text, textX, textY);
            g2d.setComposite(AlphaComposite.getInstance(3, 1.0f));
        }
        
        private boolean isFontAvailable(final String fontName) {
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            final String[] availableFontFamilyNames;
            final String[] fonts = availableFontFamilyNames = ge.getAvailableFontFamilyNames();
            for (final String font : availableFontFamilyNames) {
                if (font.equalsIgnoreCase(fontName)) {
                    return true;
                }
            }
            return false;
        }
    }
}
