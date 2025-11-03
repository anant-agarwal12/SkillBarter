package ui.components;

import ui.core.Theme;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class GlassCard extends AnimatedPanel {
    private float glassOpacity = 0.15f;
    private Color glowColor = null;
    private boolean floating = false;
    
    public GlassCard() {
        this(false);
    }
    
    public GlassCard(boolean floating) {
        super();
        this.floating = floating;
        setOpaque(false);
    }
    
    public void setGlowColor(Color color) {
        this.glowColor = color;
        repaint();
    }
    
    public void setGlassOpacity(float opacity) {
        this.glassOpacity = opacity;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        // Simplified rendering hints for better performance
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED); // Changed from QUALITY to SPEED
        
        int w = getWidth();
        int h = getHeight();
        int radius = Theme.radiusXL;
        float hover = getHoverProgress();
        boolean isHovered = isHovered();
        
        // Smooth animated shadow with hover
        int shadowOffset = (int) (8 + hover * 4); // 8 to 12
        float shadowAlpha = (float) (0.08 + hover * 0.07); // 0.08 to 0.15
        
        // Reduced shadow layers for better performance
        if (floating || isHovered) {
            for (int i = 0; i < 6; i++) { // Reduced from 10 to 6
                float alpha = shadowAlpha - (i * 0.02f);
                if (alpha > 0) {
                    g2.setColor(new Color(0, 0, 0, (int)(255 * alpha)));
                    int offset = shadowOffset + i;
                    g2.fillRoundRect(
                        offset, offset, 
                        w - offset * 2, h - offset * 2,
                        radius, radius
                    );
                }
            }
            
            // Animated glowing accent
            if (glowColor != null && isHovered) {
                float glowAlpha = 0.2f + hover * 0.1f; // 0.2 to 0.3
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, glowAlpha));
                RadialGradientPaint glow = Theme.getGlowGradient(w, h, glowColor);
                g2.setPaint(glow);
                g2.fillRoundRect(-5, -5, w + 10, h + 10, radius, radius);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }
        }
        
        // Premium glassmorphism effect - translucent with dark background
        float glassAlpha = glassOpacity + hover * 0.05f; // Animated opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, glassAlpha));
        
        // Main glass background - dark translucent
        Color glassBg = new Color(Theme.glassLight.getRed(), Theme.glassLight.getGreen(), Theme.glassLight.getBlue(), 
            (int)(255 * (0.15f + hover * 0.1f))); // 15% to 25% opacity
        g2.setColor(glassBg);
        g2.fillRoundRect(0, 0, w, h, radius, radius);
        
        // Gradient overlay for depth
        LinearGradientPaint glassGradient = new LinearGradientPaint(
            0, 0, w, h,
            new float[]{0.0f, 0.5f, 1.0f},
            new Color[]{
                new Color(Theme.glassLight.getRed(), Theme.glassLight.getGreen(), Theme.glassLight.getBlue(), 
                    (int)(255 * (0.2f + hover * 0.1f))),
                new Color(Theme.glassLight.getRed(), Theme.glassLight.getGreen(), Theme.glassLight.getBlue(), 
                    (int)(255 * (0.15f + hover * 0.05f))),
                new Color(Theme.glassLight.getRed(), Theme.glassLight.getGreen(), Theme.glassLight.getBlue(), 
                    (int)(255 * (0.1f + hover * 0.05f)))
            }
        );
        g2.setPaint(glassGradient);
        g2.fillRoundRect(0, 0, w, h, radius, radius);
        
        // Animated border with gradient
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        float borderWidth = 1.5f + hover * 1.0f; // 1.5 to 2.5
        g2.setStroke(new BasicStroke(borderWidth));
        
        LinearGradientPaint borderGrad = new LinearGradientPaint(
            0, 0, w, h,
            new float[]{0.0f, 0.5f, 1.0f},
            new Color[]{
                new Color(Theme.primary.getRed(), Theme.primary.getGreen(), Theme.primary.getBlue(), 
                    (int)(100 + hover * 50)),
                new Color(Theme.secondary.getRed(), Theme.secondary.getGreen(), Theme.secondary.getBlue(), 
                    (int)(100 + hover * 50)),
                new Color(Theme.accent.getRed(), Theme.accent.getGreen(), Theme.accent.getBlue(), 
                    (int)(100 + hover * 50))
            }
        );
        g2.setPaint(borderGrad);
        int borderOffset = (int)(hover * 1);
        g2.drawRoundRect(
            borderOffset, borderOffset,
            w - borderOffset * 2, h - borderOffset * 2,
            radius, radius
        );
        
        // Inner highlight for glass effect (animated)
        float highlightAlpha = 0.3f + hover * 0.1f;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, highlightAlpha));
        g2.setColor(new Color(255, 255, 255, 100));
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(1, 1, w - 3, h - 3, radius - 1, radius - 1);
        
        // Premium reflection at top (animated)
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f + hover * 0.05f));
        LinearGradientPaint reflection = new LinearGradientPaint(
            0, 0, 0, h / 4,
            new float[]{0.0f, 1.0f},
            new Color[]{new Color(255, 255, 255, 100), new Color(255, 255, 255, 0)}
        );
        g2.setPaint(reflection);
        g2.fillRoundRect(0, 0, w, h / 4, radius, radius);
        
        g2.dispose();
    }
}

