package ui.components;

import ui.core.Theme;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModernButton extends JButton {
    private boolean hovered = false;
    private boolean pressed = false;
    private ButtonStyle style;
    
    public enum ButtonStyle {
        PRIMARY, SECONDARY, OUTLINE, GHOST
    }
    
    public ModernButton(String text) {
        this(text, ButtonStyle.PRIMARY);
    }
    
    public ModernButton(String text, ButtonStyle style) {
        super(text);
        this.style = style;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(Theme.button);
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
                pressed = false;
                repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                pressed = false;
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        int w = getWidth();
        int h = getHeight();
        
        switch (style) {
            case PRIMARY:
                if (pressed) {
                    g2.setColor(Theme.primaryDark);
                } else if (hovered) {
                    g2.setPaint(Theme.getPrimaryGradient(w, h));
                } else {
                    g2.setColor(Theme.primary);
                }
                g2.fillRoundRect(0, 0, w, h, Theme.radiusM, Theme.radiusM);
                g2.setColor(Theme.textInverse);
                break;
                
            case SECONDARY:
                if (pressed) {
                    g2.setColor(new Color(Theme.secondary.getRed(), Theme.secondary.getGreen(), Theme.secondary.getBlue(), 200));
                } else if (hovered) {
                    g2.setColor(new Color(Theme.secondary.getRed(), Theme.secondary.getGreen(), Theme.secondary.getBlue(), 230));
                } else {
                    g2.setColor(Theme.secondary);
                }
                g2.fillRoundRect(0, 0, w, h, Theme.radiusM, Theme.radiusM);
                g2.setColor(Theme.textInverse);
                break;
                
            case OUTLINE:
                if (pressed) {
                    g2.setColor(Theme.bgHover);
                } else if (hovered) {
                    g2.setColor(Theme.bgHover);
                } else {
                    g2.setColor(Theme.bgCard);
                }
                g2.fillRoundRect(0, 0, w, h, Theme.radiusM, Theme.radiusM);
                g2.setStroke(new BasicStroke(1.5f));
                g2.setColor(Theme.primary);
                g2.drawRoundRect(1, 1, w - 3, h - 3, Theme.radiusM - 1, Theme.radiusM - 1);
                g2.setColor(Theme.primary);
                break;
                
            case GHOST:
                if (pressed) {
                    g2.setColor(new Color(Theme.primary.getRed(), Theme.primary.getGreen(), Theme.primary.getBlue(), 100));
                } else if (hovered) {
                    g2.setColor(new Color(Theme.primary.getRed(), Theme.primary.getGreen(), Theme.primary.getBlue(), 50));
                } else {
                    g2.setColor(new Color(0, 0, 0, 0)); // Transparent
                }
                g2.fillRoundRect(0, 0, w, h, Theme.radiusM, Theme.radiusM);
                // Always use visible text color - prioritize foreground if explicitly set, otherwise use textSecondary
                Color textColor = Theme.textSecondary; // Default to visible color
                if (getForeground() != null && !getForeground().equals(Color.BLACK) && !getForeground().equals(new Color(0, 0, 0, 0))) {
                    textColor = getForeground();
                }
                if (hovered) {
                    textColor = Theme.primary;
                }
                g2.setColor(textColor);
                break;
        }
        
        // Draw text - use current color from switch statement
        FontMetrics fm = g2.getFontMetrics();
        int textX = (w - fm.stringWidth(getText())) / 2;
        int textY = (h + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(getText(), textX, textY);
        
        g2.dispose();
    }
    
    public void setStyle(ButtonStyle newStyle) {
        this.style = newStyle;
        repaint();
    }
    
    public ButtonStyle getStyle() {
        return style;
    }
}

