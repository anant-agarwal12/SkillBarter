package ui.components;

import ui.core.Theme;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class VibrantButton extends JButton {
    private boolean hovered = false;
    private boolean pressed = false;
    private final ButtonStyle style;
    private Color customColor = null;
    private float hoverProgress = 0.0f;
    private Timer animationTimer;
    private static final int ANIMATION_DURATION = 150;
    private static final int FRAMES_PER_SECOND = 30; // Reduced from 60 for better performance
    
    public enum ButtonStyle {
        PRIMARY, SECONDARY, GRADIENT, GLOW
    }
    
    public VibrantButton(String text) {
        this(text, ButtonStyle.PRIMARY);
    }
    
    public VibrantButton(String text, ButtonStyle style) {
        super(text);
        this.style = style;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(14, 28, 14, 28));
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                hovered = true;
                startAnimation();
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                hovered = false;
                pressed = false;
                startAnimation();
            }
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                pressed = true;
                repaint();
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                pressed = false;
                repaint();
            }
        });
    }
    
    private void startAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        final long startTime = System.currentTimeMillis();
        animationTimer = new Timer(1000 / FRAMES_PER_SECOND, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                long elapsed = System.currentTimeMillis() - startTime;
                float progress = Math.min(elapsed / (float) ANIMATION_DURATION, 1.0f);
                
                // Smooth easing function (ease-in-out)
                float easedProgress;
                if (progress < 0.5f) {
                    easedProgress = 2 * progress * progress;
                } else {
                    easedProgress = 1 - (float) Math.pow(-2 * progress + 2, 2) / 2;
                }
                
                hoverProgress = hovered ? easedProgress : 1.0f - easedProgress;
                
                if (progress >= 1.0f) {
                    hoverProgress = hovered ? 1.0f : 0.0f;
                    animationTimer.stop();
                }
                
                repaint();
            }
        });
        
        animationTimer.start();
    }
    
    public void setCustomColor(Color color) {
        this.customColor = color;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        int w = getWidth();
        int h = getHeight();
        int radius = Theme.radiusM;
        
        Color color1 = customColor != null ? customColor : Theme.primary;
        Color color2 = customColor != null ? 
            new Color(Math.min(255, customColor.getRed() + 30), Math.min(255, customColor.getGreen() + 30), Math.min(255, customColor.getBlue() + 30)) :
            Theme.secondary;
        
        // Smooth animated hover
        float hover = hoverProgress;
        float brightness = 1.0f + hover * 0.2f; // 1.0 to 1.2
        
        switch (style) {
            case PRIMARY:
            case GRADIENT:
                // Smooth animated vibrant gradient background
                LinearGradientPaint bgGradient = new LinearGradientPaint(
                    0, 0, w, h,
                    new float[]{0.0f, 1.0f},
                    new Color[]{
                        interpolateColor(color1, color1.brighter(), hover),
                        interpolateColor(color2, color2.brighter(), hover)
                    }
                );
                g2.setPaint(bgGradient);
                break;
                
            case SECONDARY:
                int alpha = (int) (200 + hover * 30); // 200 to 230
                g2.setColor(new Color(color1.getRed(), color1.getGreen(), color1.getBlue(), alpha));
                break;
                
            case GLOW:
                // Animated glowing effect
                if (hovered) {
                    float glowAlpha = 0.3f + hover * 0.1f; // 0.3 to 0.4
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, glowAlpha));
                    RadialGradientPaint glow = Theme.getGlowGradient(w, h, color1);
                    g2.setPaint(glow);
                    g2.fillRoundRect(-5, -5, w + 10, h + 10, radius, radius);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }
                LinearGradientPaint glowGradient = new LinearGradientPaint(
                    0, 0, w, h,
                    new float[]{0.0f, 1.0f},
                    new Color[]{
                        interpolateColor(color1, color1.brighter(), hover),
                        interpolateColor(color2, color2.brighter(), hover)
                    }
                );
                g2.setPaint(glowGradient);
                break;
        }
        
        // Smooth animated shadow for depth
        if (hovered && !pressed) {
            float shadowAlpha = 0.2f + hover * 0.05f; // 0.2 to 0.25
            for (int i = 0; i < 8; i++) {
                float alpha = shadowAlpha - (i * 0.03f);
                if (alpha > 0) {
                    g2.setColor(new Color(color1.getRed(), color1.getGreen(), color1.getBlue(), (int)(255 * alpha)));
                    g2.fillRoundRect(3 + i, 3 + i, w - 6 - i * 2, h - 6 - i * 2, radius, radius);
                }
            }
        }
        
        // Button background
        if (pressed) {
            g2.translate(1, 1);
            w -= 2;
            h -= 2;
        }
        
        if (style != ButtonStyle.SECONDARY) {
            g2.fillRoundRect(0, 0, w, h, radius, radius);
        } else {
            g2.fillRoundRect(0, 0, w, h, radius, radius);
            // Outline
            g2.setStroke(new BasicStroke(2f));
            g2.setColor(color1);
            g2.drawRoundRect(1, 1, w - 3, h - 3, radius - 1, radius - 1);
        }
        
        // Text with shadow for depth
        FontMetrics fm = g2.getFontMetrics();
        int textX = (w - fm.stringWidth(getText())) / 2;
        int textY = (h + fm.getAscent() - fm.getDescent()) / 2;
        
        // Text shadow
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        g2.setColor(new Color(0, 0, 0, 100));
        g2.drawString(getText(), textX + 2, textY + 2);
        
        // Text
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.setColor(getForeground());
        g2.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        g2.drawString(getText(), textX, textY);
        
        g2.dispose();
    }
    
    private Color interpolateColor(Color c1, Color c2, float t) {
        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * t);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t);
        return new Color(r, g, b);
    }
}

