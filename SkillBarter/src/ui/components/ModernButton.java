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
        
        // Check if text contains emojis - use emoji font if so
        if (containsEmojis(text)) {
            setFont(Theme.getEmojiFont(14)); // Use Unicode-compatible font for emojis
        } else {
            setFont(Theme.button);
        }
        
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
    
    /**
     * Check if text contains emoji characters
     */
    private boolean containsEmojis(String text) {
        if (text == null || text.isEmpty()) return false;
        
        // Check for common emoji ranges
        for (char c : text.toCharArray()) {
            int codePoint = (int) c;
            // Emoji ranges in Unicode
            if (codePoint >= 0x1F300 && codePoint <= 0x1F9FF) return true; // Emoticons
            if (codePoint >= 0x1F600 && codePoint <= 0x1F64F) return true; // Emoticons
            if (codePoint >= 0x1F900 && codePoint <= 0x1F9FF) return true; // Supplemental Symbols
            if (codePoint >= 0x2600 && codePoint <= 0x26FF) return true; // Miscellaneous Symbols
            if (codePoint >= 0x2700 && codePoint <= 0x27BF) return true; // Dingbats
            if (codePoint >= 0x1F680 && codePoint <= 0x1F6FF) return true; // Transport & Map
            if (codePoint >= 0x1F1E0 && codePoint <= 0x1F1FF) return true; // Flags
            if (codePoint >= 0x1FA00 && codePoint <= 0x1FAFF) return true; // Extended
        }
        return false;
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
                // For emoji buttons, don't set text color - let emojis display naturally
                if (!containsEmojis(getText())) {
                    Color textColor = Theme.textSecondary; // Default to visible color
                    if (getForeground() != null && !getForeground().equals(Color.BLACK) && !getForeground().equals(new Color(0, 0, 0, 0))) {
                        textColor = getForeground();
                    }
                    if (hovered) {
                        textColor = Theme.primary;
                    }
                    g2.setColor(textColor);
                } else {
                    // For emoji buttons, don't override color - let OS render emojis in color
                    g2.setColor(Theme.textPrimary); // Use a neutral color that won't tint emojis
                }
                break;
        }
        
        // Draw text - use current color from switch statement
        FontMetrics fm = g2.getFontMetrics();
        int textX = (w - fm.stringWidth(getText())) / 2;
        int textY = (h + fm.getAscent() - fm.getDescent()) / 2;
        
        // For emoji-only buttons, use a rendering hint that preserves emoji colors
        if (containsEmojis(getText()) && getText().trim().matches("^[\\p{So}\\p{Cn}]+$")) {
            // If text is only emojis/symbols, use a different rendering approach
            // Unfortunately, Swing's drawString will still apply color, but we can minimize the tint
            g2.setColor(Color.BLACK); // Use black as base - OS will render emojis in color if font supports it
        }
        
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

