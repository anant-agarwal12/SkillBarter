package ui.components;

import ui.core.Theme;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Professional card component with subtle shadows
 */
public class ProfessionalCard extends JPanel {
    private boolean hovered = false;
    private boolean selected = false;
    
    public ProfessionalCard() {
        setOpaque(true);
        setBackground(Theme.BG_CARD);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM, 
                Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM)
        ));
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                hovered = true;
                repaint();
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                hovered = false;
                repaint();
            }
        });
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth();
        int h = getHeight();
        
        // Background
        g2.setColor(hovered ? Theme.BG_HOVER : Theme.BG_CARD);
        g2.fillRoundRect(0, 0, w, h, Theme.RADIUS_MEDIUM, Theme.RADIUS_MEDIUM);
        
        // Selected state - top border accent
        if (selected) {
            g2.setColor(Theme.CORAL_ACCENT);
            g2.setStroke(new BasicStroke(3f));
            g2.drawLine(0, 0, w, 0);
        }
        
        // Subtle shadow on hover
        if (hovered) {
            for (int i = 0; i < 3; i++) {
                float alpha = 0.08f - (i * 0.02f);
                g2.setColor(new Color(0, 0, 0, (int)(255 * alpha)));
                g2.fillRoundRect(i + 1, i + 1, w - (i * 2) - 2, h - (i * 2) - 2, 
                    Theme.RADIUS_MEDIUM, Theme.RADIUS_MEDIUM);
            }
        }
        
        g2.dispose();
    }
}
