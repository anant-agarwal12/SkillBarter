package ui.components;

import ui.core.Theme;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class StatCard extends JPanel {
    private final String icon;
    private final String value;
    private final String label;
    private final Color accentColor;
    private final JLabel valueLabel;
    private boolean hovered = false;
    
    public StatCard(String icon, String value, String label, Color accentColor) {
        this.icon = icon;
        this.value = value;
        this.label = label;
        this.accentColor = accentColor;
        setOpaque(false);
        setBorder(new EmptyBorder(Theme.spacingL, Theme.spacingL, Theme.spacingL, Theme.spacingL));
        setLayout(new BorderLayout());
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(Theme.getEmojiFont(18)); // Use Unicode-compatible font for emojis
        // Don't set foreground color - let emojis display in their natural colors
        
        valueLabel = new JLabel(value);
        valueLabel.setFont(Theme.headingM);
        valueLabel.setForeground(accentColor);
        
        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(Theme.bodySmall);
        labelLabel.setForeground(Theme.textSecondary);
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.add(iconLabel);
        content.add(Box.createVerticalStrut(Theme.spacingM));
        content.add(valueLabel);
        content.add(labelLabel);
        
        add(content, BorderLayout.CENTER);
        
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
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        int w = getWidth();
        int h = getHeight();
        
        // Soft shadow
        for (int i = 0; i < 4; i++) {
            float alpha = 0.06f - (i * 0.015f);
            if (alpha > 0) {
                g2.setColor(new Color(0, 0, 0, (int)(255 * alpha)));
                g2.fillRoundRect(2 + i, 2 + i, w - 4 - i * 2, h - 4 - i * 2, Theme.radiusL, Theme.radiusL);
            }
        }
        
        // Background with smooth gradient
        if (hovered) {
            GradientPaint cardGrad = Theme.getCardGradient(w, h, 
                new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 15),
                Theme.bgCard);
            g2.setPaint(cardGrad);
        } else {
            GradientPaint subtleGrad = Theme.getSubtleGradient(w, h);
            g2.setPaint(subtleGrad);
        }
        g2.fillRoundRect(0, 0, w, h, Theme.radiusL, Theme.radiusL);
        
        // Top accent border with smooth gradient
        GradientPaint accentGrad = new GradientPaint(
            0, 0, accentColor,
            w, 0, new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 180)
        );
        g2.setPaint(accentGrad);
        g2.setStroke(new BasicStroke(3f));
        g2.drawLine(0, 0, w, 0);
        
        // Very subtle border
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(Theme.borderLight);
        g2.drawRoundRect(0, 0, w - 1, h - 1, Theme.radiusL, Theme.radiusL);
        
        g2.dispose();
    }
    
    public JLabel getValueLabel() {
        return valueLabel;
    }
}

