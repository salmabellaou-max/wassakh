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
import java.awt.Color;
import javax.swing.JPanel;

class DrawingLogoPanel extends JPanel
{
    public DrawingLogoPanel() {
        this.setBackground(Color.WHITE);
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

        // Draw complete heart immediately
        this.drawCompleteHeart(g2d, centerX, centerY);

        // Draw complete text immediately
        this.drawCompleteText(g2d, centerX, centerY);
    }

    private void drawCompleteHeart(final Graphics2D g2d, final int cx, final int cy) {
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

        // Draw filled heart
        final GradientPaint gradient = new GradientPaint(
            0.0f, -100.0f, new Color(46, 125, 50, 50),
            0.0f, 100.0f, new Color(0, 100, 63, 50)
        );
        g2d.setPaint(gradient);
        g2d.fill(heart);

        // Draw heart outline
        g2d.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(UIConstants.PRIMARY_GREEN);
        g2d.draw(heart);

        g2d.translate(-cx, -cy);
    }

    private void drawCompleteText(final Graphics2D g2d, final int cx, final int cy) {
        final String text = "MyWelly";
        final Font font = UIConstants.LOGO_FONT;
        g2d.setFont(font);

        final FontMetrics fm = g2d.getFontMetrics();
        final int textWidth = fm.stringWidth(text);

        g2d.setColor(UIConstants.PRIMARY_GREEN);
        g2d.drawString(text, cx - textWidth / 2, cy + 20);
    }
}
