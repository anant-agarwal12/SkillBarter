package ui.components;

import ui.core.Theme;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Neon button with glowing effects
 */
public class NeonButton extends JButton {
    private boolean hovered = false;
    private Color neonColor = Theme.NEON_CYAN;
    
    public NeonButton(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(Theme.FONT_BODY);
        setForeground(Theme.TEXT_PRIMARY);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
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
        if (hovered) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            for (int i = 3; i >= 1; i--) {
                g2.setColor(new Color(neonColor.getRed(), neonColor.getGreen(), neonColor.getBlue(), 50 * i));
                g2.setStroke(new BasicStroke(i * 2));
                g2.drawRoundRect(0, 0, w, h, Theme.RADIUS_MEDIUM, Theme.RADIUS_MEDIUM);
            }
        }
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        
        // Background
        g2.setColor(hovered ? Theme.BG_CARD : Theme.BG_SECONDARY);
        g2.fillRoundRect(0, 0, w, h, Theme.RADIUS_MEDIUM, Theme.RADIUS_MEDIUM);
        
        // Border
        g2.setColor(hovered ? neonColor : Theme.BORDER_LIGHT);
        g2.setStroke(new BasicStroke(hovered ? 2.0f : 1.0f));
        g2.drawRoundRect(0, 0, w - 1, h - 1, Theme.RADIUS_MEDIUM, Theme.RADIUS_MEDIUM);
        
        // Text
        FontMetrics fm = g2.getFontMetrics();
        int x = (w - fm.stringWidth(getText())) / 2;
        int y = (h + fm.getAscent() - fm.getDescent()) / 2;
        g2.setColor(hovered ? neonColor : Theme.TEXT_PRIMARY);
        g2.drawString(getText(), x, y);
        
        g2.dispose();
    }
}

