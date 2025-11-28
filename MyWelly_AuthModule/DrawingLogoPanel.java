import java.awt.event.ActionEvent;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Shape;
import java.awt.Paint;
import java.awt.GradientPaint;
import java.awt.geom.Path2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import javax.swing.JPanel;

// 
// Decompiled by Procyon v0.6.0
// 

class DrawingLogoPanel extends JPanel
{
    private float progress;
    private Timer animationTimer;
    private BufferedImage logoImage;
    private List<Point2D.Float> heartPoints;
    private List<Point2D.Float> textPoints;
    
    public DrawingLogoPanel() {
        this.progress = 0.0f;
        this.setBackground(Color.WHITE);
        this.createDrawingPaths();
        try {
            final File logoFile = new File("logo.jpg");
            if (logoFile.exists()) {
                this.logoImage = ImageIO.read(logoFile);
            }
        }
        catch (final Exception e) {
            this.logoImage = null;
        }
    }
    
    private void createDrawingPaths() {
        this.heartPoints = new ArrayList<Point2D.Float>();
        this.textPoints = new ArrayList<Point2D.Float>();
        final int centerX = 0;
        final int centerY = 0;
        final int size = 180;
        for (int i = 0; i <= 50; ++i) {
            final float t = i / 50.0f;
            final float x = -60.0f + t * 60.0f;
            final float y = -50.0f + t * 30.0f;
            this.heartPoints.add(new Point2D.Float(x * size / 100.0f, y * size / 100.0f));
        }
        for (int i = 0; i <= 50; ++i) {
            final float t = i / 50.0f;
            final float x = -60.0f + t * 60.0f;
            final float y = -20.0f + t * 70.0f;
            this.heartPoints.add(new Point2D.Float(x * size / 100.0f, y * size / 100.0f));
        }
        for (int i = 0; i <= 50; ++i) {
            final float t = i / 50.0f;
            final float x = 0.0f + t * 60.0f;
            final float y = 50.0f - t * 70.0f;
            this.heartPoints.add(new Point2D.Float(x * size / 100.0f, y * size / 100.0f));
        }
        for (int i = 0; i <= 50; ++i) {
            final float t = i / 50.0f;
            final float x = 60.0f - t * 60.0f;
            final float y = -20.0f - t * 30.0f;
            this.heartPoints.add(new Point2D.Float(x * size / 100.0f, y * size / 100.0f));
        }
    }
    
    public void startDrawing() {
        (this.animationTimer = new Timer(15, e -> {
            this.progress += 0.008f;
            if (this.progress >= 1.0f) {
                this.progress = 1.0f;
                ((Timer)e.getSource()).stop();
            }
            this.repaint();
        })).start();
    }
    
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        final int centerX = this.getWidth() / 2;
        final int centerY = this.getHeight() / 2 - 50;
        if (this.progress < 0.4f) {
            this.drawProgressiveHeart(g2d, centerX, centerY, this.progress / 0.4f);
        }
        else if (this.progress < 0.6f) {
            this.drawCompleteHeart(g2d, centerX, centerY, (this.progress - 0.4f) / 0.2f);
        }
        else {
            this.drawCompleteHeart(g2d, centerX, centerY, 1.0f);
            this.drawProgressiveText(g2d, centerX, centerY, (this.progress - 0.6f) / 0.4f);
        }
        if (this.progress > 0.9f) {
            this.drawSparkles(g2d, centerX, centerY, (this.progress - 0.9f) / 0.1f);
        }
    }
    
    private void drawProgressiveHeart(final Graphics2D g2d, final int cx, final int cy, final float prog) {
        g2d.translate(cx, cy);
        final int pointsToDraw = (int)(this.heartPoints.size() * prog);
        g2d.setStroke(new BasicStroke(6.0f, 1, 1));
        g2d.setColor(UIConstants.PRIMARY_GREEN);
        for (int i = 1; i < pointsToDraw && i < this.heartPoints.size(); ++i) {
            final Point2D.Float p1 = this.heartPoints.get(i - 1);
            final Point2D.Float p2 = this.heartPoints.get(i);
            g2d.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
        }
        if (pointsToDraw > 0 && pointsToDraw < this.heartPoints.size()) {
            final Point2D.Float tip = this.heartPoints.get(pointsToDraw);
            g2d.setColor(UIConstants.HOVER_GREEN);
            g2d.fillOval((int)tip.x - 4, (int)tip.y - 4, 8, 8);
        }
        g2d.translate(-cx, -cy);
    }
    
    private void drawCompleteHeart(final Graphics2D g2d, final int cx, final int cy, final float fillProg) {
        g2d.translate(cx, cy);
        final Path2D.Float heart = new Path2D.Float();
        final int size = 180;
        final float scale = size / 100.0f;
        heart.moveTo(0.0f, -30.0f * scale);
        heart.curveTo(-25.0f * scale, -50.0f * scale, -60.0f * scale, -50.0f * scale, -60.0f * scale, -20.0f * scale);
        heart.curveTo(-60.0f * scale, 0.0f, -40.0f * scale, 20.0f * scale, 0.0f, 50.0f * scale);
        heart.curveTo(40.0f * scale, 20.0f * scale, 60.0f * scale, 0.0f, 60.0f * scale, -20.0f * scale);
        heart.curveTo(60.0f * scale, -50.0f * scale, 25.0f * scale, -50.0f * scale, 0.0f, -30.0f * scale);
        heart.closePath();
        final int alpha = (int)(fillProg * 200.0f);
        final GradientPaint gradient = new GradientPaint(0.0f, -100.0f, new Color(46, 125, 50, alpha), 0.0f, 100.0f, new Color(0, 100, 63, alpha));
        g2d.setPaint(gradient);
        g2d.fill(heart);
        g2d.setStroke(new BasicStroke(6.0f, 1, 1));
        g2d.setColor(UIConstants.PRIMARY_GREEN);
        g2d.draw(heart);
        g2d.translate(-cx, -cy);
    }
    
    private void drawProgressiveText(final Graphics2D g2d, final int cx, final int cy, final float prog) {
        final String text = "MyWelly";
        final Font font = UIConstants.LOGO_FONT;
        g2d.setFont(font);
        final FontMetrics fm = g2d.getFontMetrics();
        final int textWidth = fm.stringWidth(text);
        final int textHeight = fm.getHeight();
        final int charsToDraw = (int)(text.length() * prog);
        final String visibleText = text.substring(0, Math.min(charsToDraw + 1, text.length()));
        final float alpha = Math.min(prog * 2.0f, 1.0f);
        g2d.setColor(new Color(UIConstants.PRIMARY_GREEN.getRed(), UIConstants.PRIMARY_GREEN.getGreen(), UIConstants.PRIMARY_GREEN.getBlue(), (int)(alpha * 255.0f)));
        g2d.drawString(visibleText, cx - textWidth / 2, cy + 20);
        if (charsToDraw < text.length()) {
            final int cursorX = cx - textWidth / 2 + fm.stringWidth(visibleText);
            g2d.setStroke(new BasicStroke(3.0f));
            g2d.setColor(UIConstants.HOVER_GREEN);
            g2d.drawLine(cursorX, cy + 25, cursorX, cy - textHeight + 30);
        }
    }
    
    private void drawSparkles(final Graphics2D g2d, final int cx, final int cy, final float alpha) {
        g2d.setComposite(AlphaComposite.getInstance(3, alpha * 0.7f));
        final int[][] array;
        final int[][] sparklePos = array = new int[][] { { -120, -80 }, { 120, -80 }, { -140, 0 }, { 140, 0 }, { -100, 80 }, { 100, 80 }, { 0, -120 }, { 0, 100 } };
        for (final int[] pos : array) {
            this.drawStar(g2d, cx + pos[0], cy + pos[1], 10, 5);
        }
        g2d.setComposite(AlphaComposite.getInstance(3, 1.0f));
    }
    
    private void drawStar(final Graphics2D g2d, final int x, final int y, final int outerRadius, final int innerRadius) {
        final Path2D.Float star = new Path2D.Float();
        for (int i = 0; i < 5; ++i) {
            final double angle = i * 4 * 3.141592653589793 / 5.0 - 1.5707963267948966;
            final double nextAngle = (i * 4 + 2) * 3.141592653589793 / 5.0 - 1.5707963267948966;
            if (i == 0) {
                star.moveTo(x + Math.cos(angle) * outerRadius, y + Math.sin(angle) * outerRadius);
            }
            else {
                star.lineTo(x + Math.cos(angle) * outerRadius, y + Math.sin(angle) * outerRadius);
            }
            star.lineTo(x + Math.cos(nextAngle) * innerRadius, y + Math.sin(nextAngle) * innerRadius);
        }
        star.closePath();
        g2d.setColor(UIConstants.SUCCESS_GREEN);
        g2d.fill(star);
    }
}
