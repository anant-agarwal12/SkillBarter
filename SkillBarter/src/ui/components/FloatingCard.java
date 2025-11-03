package ui.components;

import ui.core.Theme;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class FloatingCard extends AnimatedPanel {
    private Color accentColor = Theme.primary;
    private boolean glowing = false;
    
    public FloatingCard() {
        super();
        setOpaque(false);
    }
    
    public void setAccentColor(Color color) {
        this.accentColor = color;
        repaint();
    }
    
    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
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
        
        // Smooth animated shadow for floating effect
        int shadowOffset = (int) (10 + hover * 5); // 10 to 15
        float shadowAlpha = (float) (0.08 + hover * 0.04); // 0.08 to 0.12
        
        // Reduced shadow layers for better performance
        if (isHovered) {
            for (int i = 0; i < 8; i++) { // Reduced from 15 to 8
                float alpha = shadowAlpha - (i * 0.012f);
                if (alpha > 0) {
                    g2.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), (int)(255 * alpha)));
                    int offset = shadowOffset + i * 2;
                    g2.fillRoundRect(
                        offset, offset,
                        w - offset * 2, h - offset * 2,
                        radius, radius
                    );
                }
            }
        } else {
            for (int i = 0; i < 5; i++) { // Reduced from 8 to 5
                float alpha = shadowAlpha - (i * 0.015f);
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
        }
        
        // Animated glowing effect
        if (glowing || isHovered) {
            float glowAlpha = 0.3f + hover * 0.1f; // 0.3 to 0.4
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, glowAlpha));
            RadialGradientPaint glow = Theme.getGlowGradient(w, h, accentColor);
            g2.setPaint(glow);
            g2.fillRoundRect(-10, -10, w + 20, h + 20, radius, radius);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
        
        // Premium dark card background with subtle gradient
        int baseR = Theme.bgCard.getRed();
        int baseG = Theme.bgCard.getGreen();
        int baseB = Theme.bgCard.getBlue();
        int accentTint = (int)(hover * 8); // Subtle accent tint on hover
        
        LinearGradientPaint bgGradient = new LinearGradientPaint(
            0, 0, w, h,
            new float[]{0.0f, 0.5f, 1.0f},
            new Color[]{
                Theme.bgCard,
                new Color(
                    Math.min(255, baseR + accentTint + accentColor.getRed() / 30),
                    Math.min(255, baseG + accentTint + accentColor.getGreen() / 30),
                    Math.min(255, baseB + accentTint + accentColor.getBlue() / 30)
                ),
                Theme.bgCard
            }
        );
        g2.setPaint(bgGradient);
        g2.fillRoundRect(0, 0, w, h, radius, radius);
        
        // Animated vibrant border gradient
        float borderWidth = 2f + hover * 1f; // 2 to 3
        g2.setStroke(new BasicStroke(borderWidth));
        LinearGradientPaint borderGrad = new LinearGradientPaint(
            0, 0, w, h,
            new float[]{0.0f, 0.5f, 1.0f},
            new Color[]{
                accentColor,
                Theme.secondary,
                Theme.accent
            }
        );
        g2.setPaint(borderGrad);
        g2.drawRoundRect(0, 0, w - 1, h - 1, radius, radius);
        
        // Animated inner glow
        if (isHovered) {
            float innerGlowAlpha = 0.15f + hover * 0.05f; // 0.15 to 0.2
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, innerGlowAlpha));
            g2.setColor(accentColor);
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(2, 2, w - 5, h - 5, radius - 2, radius - 2);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
        
        g2.dispose();
    }
}

