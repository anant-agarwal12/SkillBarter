package ui.components;

import ui.core.Theme;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Neon card with glowing border
 */
public class NeonCard extends JPanel {
    private Color neonColor = Theme.NEON_CYAN;
    private boolean glowing = false;
    
    public NeonCard() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, Theme.PADDING_LARGE, 
            Theme.PADDING_LARGE, Theme.PADDING_LARGE));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                glowing = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                glowing = false;
                repaint();
            }
        });
    }
    
    public void setNeonColor(Color color) {
        this.neonColor = color;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth();
        int h = getHeight();
        
        // Glow effect
        if (glowing) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            for (int i = 4; i >= 1; i--) {
                g2.setColor(new Color(neonColor.getRed(), neonColor.getGreen(), neonColor.getBlue(), 40 * i));
                g2.setStroke(new BasicStroke(i * 1.5f));
                g2.drawRoundRect(0, 0, w, h, Theme.RADIUS_LARGE, Theme.RADIUS_LARGE);
            }
        }
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        
        // Background
        g2.setColor(Theme.BG_CARD);
        g2.fillRoundRect(0, 0, w, h, Theme.RADIUS_LARGE, Theme.RADIUS_LARGE);
        
        // Border
        g2.setColor(glowing ? neonColor : Theme.BORDER_LIGHT);
        g2.setStroke(new BasicStroke(glowing ? 2.0f : 1.0f));
        g2.drawRoundRect(0, 0, w - 1, h - 1, Theme.RADIUS_LARGE, Theme.RADIUS_LARGE);
        
        g2.dispose();
    }
}

