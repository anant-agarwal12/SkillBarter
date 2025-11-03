package ui.components;

import ui.core.Theme;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class HeroPanel extends JPanel {
    private String title;
    private String subtitle;
    private String ctaText;
    private Runnable ctaAction;
    
    public HeroPanel(String title, String subtitle, String ctaText, Runnable ctaAction) {
        this.title = title;
        this.subtitle = subtitle;
        this.ctaText = ctaText;
        this.ctaAction = ctaAction;
        setOpaque(false);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 200));
        setBorder(BorderFactory.createEmptyBorder(Theme.spacingXL, Theme.spacingXXL, Theme.spacingXL, Theme.spacingXXL));
        
        initializeComponents();
    }
    
    private void initializeComponents() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        
        // Title with glow effect
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(255, 255, 255, 240));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(Theme.spacingM, 0, Theme.spacingL, 0));
        
        // Vibrant CTA Button
        VibrantButton ctaButton = new VibrantButton(ctaText, VibrantButton.ButtonStyle.GLOW);
        ctaButton.setCustomColor(Theme.accent);
        ctaButton.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        ctaButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        ctaButton.setPreferredSize(new Dimension(200, 52));
        if (ctaAction != null) {
            ctaButton.addActionListener(e -> ctaAction.run());
        }
        
        content.add(titleLabel);
        content.add(subtitleLabel);
        content.add(ctaButton);
        
        add(content, BorderLayout.CENTER);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int w = getWidth();
        int h = getHeight();
        
        // Early exit for invalid dimensions
        if (w <= 0 || h <= 0 || !isVisible()) {
            return;
        }
        
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED); // Changed to SPEED for performance
        
        // Premium dark gradient background with vibrant accents
        // Base dark gradient
        LinearGradientPaint darkGradient = new LinearGradientPaint(
            0, 0, w, h,
            new float[]{0.0f, 0.5f, 1.0f},
            new Color[]{
                Theme.bgPrimary,
                Theme.bgSecondary,
                Theme.bgCard
            }
        );
        g2.setPaint(darkGradient);
        g2.fillRoundRect(0, 0, w, h, Theme.radiusXL, Theme.radiusXL);
        
        // Vibrant accent gradient overlay (Teal, Violet, Coral)
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        LinearGradientPaint bgGradient = Theme.getVibrantGradient(w, h);
        g2.setPaint(bgGradient);
        g2.fillRoundRect(0, 0, w, h, Theme.radiusXL, Theme.radiusXL);
        
        // Premium glow layer for depth
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        RadialGradientPaint glow = new RadialGradientPaint(
            w / 2, h / 2, Math.max(w, h) * 0.7f,
            new float[]{0.0f, 0.7f, 1.0f},
            new Color[]{
                new Color(Theme.primary.getRed(), Theme.primary.getGreen(), Theme.primary.getBlue(), 150),
                new Color(Theme.secondary.getRed(), Theme.secondary.getGreen(), Theme.secondary.getBlue(), 80),
                new Color(0, 0, 0, 0)
            }
        );
        g2.setPaint(glow);
        g2.fillRoundRect(0, 0, w, h, Theme.radiusXL, Theme.radiusXL);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        
        // Decorative circles for visual interest
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
        
        // Large circle in background
        int circle1X = (int)(w * 0.8);
        int circle1Y = (int)(h * 0.3);
        RadialGradientPaint circleGrad = new RadialGradientPaint(
            circle1X, circle1Y, 150,
            new float[]{0f, 1f},
            new Color[]{Theme.textInverse, new Color(255, 255, 255, 0)}
        );
        g2.setPaint(circleGrad);
        g2.fillOval(circle1X - 150, circle1Y - 150, 300, 300);
        
        // Medium circle
        int circle2X = (int)(w * 0.1);
        int circle2Y = (int)(h * 0.7);
        RadialGradientPaint circleGrad2 = new RadialGradientPaint(
            circle2X, circle2Y, 100,
            new float[]{0f, 1f},
            new Color[]{Theme.textInverse, new Color(255, 255, 255, 0)}
        );
        g2.setPaint(circleGrad2);
        g2.fillOval(circle2X - 100, circle2Y - 100, 200, 200);
        
        // Overlay pattern - Optimized: reduced density and only render if panel is reasonably sized
        if (w < 2000 && h < 2000) { // Only render pattern for reasonable sizes
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
            g2.setColor(Theme.textInverse);
            // Increased spacing from 30 to 50 for better performance
            for (int i = 0; i < w; i += 50) {
                for (int j = 0; j < h; j += 50) {
                    g2.fillOval(i, j, 2, 2);
                }
            }
        }
        
        // Top border accent
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.setStroke(new BasicStroke(4f));
        LinearGradientPaint borderGrad = new LinearGradientPaint(
            0, 0, w, 0,
            new float[]{0f, 0.5f, 1f},
            new Color[]{Theme.gradientStart, Theme.gradientAccent, Theme.gradientEnd}
        );
        g2.setPaint(borderGrad);
        g2.drawLine(0, 0, w, 0);
        } finally {
            g2.dispose();
        }
    }
}

