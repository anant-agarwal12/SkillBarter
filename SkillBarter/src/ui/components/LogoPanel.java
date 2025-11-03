package ui.components;

import ui.core.Theme;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class LogoPanel extends JPanel {
    
    public LogoPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(50, 50));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        int w = getWidth();
        int h = getHeight();
        int centerX = w / 2;
        int centerY = h / 2;
        int radius = Math.min(w, h) / 2 - 2;
        
        // Outer glow circle
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        for (int i = 0; i < 3; i++) {
            float r = radius + i * 3;
            RadialGradientPaint rgp = new RadialGradientPaint(
                centerX, centerY, r,
                new float[]{0f, 1f},
                new Color[]{new Color(Theme.primary.getRed(), Theme.primary.getGreen(), Theme.primary.getBlue(), 150), 
                           new Color(Theme.primary.getRed(), Theme.primary.getGreen(), Theme.primary.getBlue(), 0)}
            );
            g2.setPaint(rgp);
            g2.fillOval((int)(centerX - r), (int)(centerY - r), (int)(r * 2), (int)(r * 2));
        }
        
        // Main logo circle with gradient
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        GradientPaint logoGrad = Theme.getPrimaryGradient(w, h);
        g2.setPaint(logoGrad);
        g2.fillOval(2, 2, radius * 2, radius * 2);
        
        // Inner highlight
        g2.setColor(new Color(255, 255, 255, 100));
        g2.fillOval(5, 5, radius * 2 - 6, radius * 2 - 6);
        
        // Icon - exchange arrows
        g2.setColor(Theme.textInverse);
        g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int iconSize = radius;
        int startX = centerX - iconSize / 3;
        int startY = centerY;
        int endX = centerX + iconSize / 3;
        int endY = centerY;
        
        // Left arrow
        g2.drawLine(startX, startY, endX, endY);
        g2.drawLine(startX, startY, startX + 5, startY - 5);
        g2.drawLine(startX, startY, startX + 5, startY + 5);
        
        // Right arrow
        g2.drawLine(endX, endY, startX, startY);
        g2.drawLine(endX, endY, endX - 5, endY - 5);
        g2.drawLine(endX, endY, endX - 5, endY + 5);
        
        g2.dispose();
    }
}

