package ui.components;

import ui.core.Theme;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class EnhancedSkillCard extends JPanel {
    private boolean hovered = false;
    private final String skillName;
    private final String teacherName;
    private final double rating;
    private final int pointsRequired;
    private final String location;
    private final String category;
    private final Runnable onClick;
    
    public EnhancedSkillCard(String skillName, String teacherName, double rating, int pointsRequired, 
                            String location, String category, Runnable onClick) {
        this.skillName = skillName;
        this.teacherName = teacherName;
        this.rating = rating;
        this.pointsRequired = pointsRequired;
        this.location = location;
        this.category = category;
        this.onClick = onClick;
        
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(Theme.spacingL, Theme.spacingL, Theme.spacingL, Theme.spacingL));
        setLayout(new BorderLayout());
        
        initializeComponents();
        
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
            
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (onClick != null) onClick.run();
            }
        });
    }
    
    private void initializeComponents() {
        // Category badge
        JLabel categoryBadge = new JLabel(category);
        categoryBadge.setFont(Theme.bodySmall);
        categoryBadge.setForeground(Theme.primary);
        categoryBadge.setOpaque(true);
        categoryBadge.setBackground(new Color(Theme.primary.getRed(), Theme.primary.getGreen(), Theme.primary.getBlue(), 20));
        categoryBadge.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(Theme.primary.getRed(), Theme.primary.getGreen(), Theme.primary.getBlue(), 50), 1),
            new EmptyBorder(4, 10, 4, 10)
        ));
        
        // Top section
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);
        topSection.add(categoryBadge, BorderLayout.WEST);
        
        // Rating stars
        JPanel ratingPanel = createRatingPanel(rating);
        topSection.add(ratingPanel, BorderLayout.EAST);
        
        // Skill name
        JLabel skillLabel = new JLabel(skillName);
        skillLabel.setFont(Theme.headingS);
        skillLabel.setForeground(Theme.textPrimary);
        skillLabel.setBorder(new EmptyBorder(Theme.spacingM, 0, Theme.spacingS, 0));
        
        // Teacher info
        JLabel teacherLabel = new JLabel("👤 " + teacherName);
        teacherLabel.setFont(Theme.body);
        teacherLabel.setForeground(Theme.textSecondary);
        
        JLabel locationLabel = new JLabel("📍 " + location);
        locationLabel.setFont(Theme.bodySmall);
        locationLabel.setForeground(Theme.textMuted);
        
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.spacingS, 0));
        infoPanel.setOpaque(false);
        infoPanel.add(teacherLabel);
        infoPanel.add(new JLabel(" • "));
        infoPanel.add(locationLabel);
        
        // Bottom section - Points and button
        JPanel bottomSection = new JPanel(new BorderLayout());
        bottomSection.setOpaque(false);
        bottomSection.setBorder(new EmptyBorder(Theme.spacingM, 0, 0, 0));
        
        JLabel pointsLabel = new JLabel("💎 " + pointsRequired + " points");
        pointsLabel.setFont(Theme.body);
        pointsLabel.setForeground(Theme.primary);
        pointsLabel.setFont(pointsLabel.getFont().deriveFont(Font.BOLD));
        
        ModernButton learnButton = new ModernButton("Learn Now", ModernButton.ButtonStyle.PRIMARY);
        learnButton.setPreferredSize(new Dimension(120, 38));
        learnButton.addActionListener(e -> {
            if (onClick != null) onClick.run();
        });
        
        bottomSection.add(pointsLabel, BorderLayout.WEST);
        bottomSection.add(learnButton, BorderLayout.EAST);
        
        // Assemble
        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.add(topSection, BorderLayout.NORTH);
        content.add(skillLabel, BorderLayout.CENTER);
        content.add(infoPanel, BorderLayout.SOUTH);
        content.add(bottomSection, BorderLayout.PAGE_END);
        
        add(content, BorderLayout.CENTER);
    }
    
    private JPanel createRatingPanel(double rating) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 0));
        panel.setOpaque(false);
        
        int fullStars = (int) rating;
        boolean hasHalfStar = rating % 1 >= 0.5;
        
        for (int i = 0; i < 5; i++) {
            if (i < fullStars) {
                panel.add(createStarLabel("★", true));
            } else if (i == fullStars && hasHalfStar) {
                panel.add(createStarLabel("★", false));
            } else {
                panel.add(createStarLabel("☆", false));
            }
        }
        
        JLabel ratingText = new JLabel(String.format("%.1f", rating));
        ratingText.setFont(Theme.bodySmall);
        ratingText.setForeground(Theme.textSecondary);
        panel.add(new JLabel(" "));
        panel.add(ratingText);
        
        return panel;
    }
    
    private JLabel createStarLabel(String star, boolean filled) {
        JLabel label = new JLabel(star);
        label.setFont(Theme.body);
        label.setForeground(filled ? Theme.rating : Theme.borderMedium);
        return label;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        int w = getWidth();
        int h = getHeight();
        
        // Shadow effect
        if (!hovered) {
            g2.setColor(new Color(0, 0, 0, 10));
            g2.fillRoundRect(3, 3, w - 3, h - 3, Theme.radiusL, Theme.radiusL);
        } else {
            // Enhanced shadow on hover
            for (int i = 0; i < 5; i++) {
                float alpha = 0.15f - (i * 0.03f);
                if (alpha > 0) {
                    g2.setColor(new Color(Theme.primary.getRed(), Theme.primary.getGreen(), Theme.primary.getBlue(), (int)(255 * alpha)));
                    g2.fillRoundRect(3 + i, 3 + i, w - 3 - i * 2, h - 3 - i * 2, Theme.radiusL, Theme.radiusL);
                }
            }
        }
        
        // Card background with gradient
        if (hovered) {
            GradientPaint bgGrad = new GradientPaint(
                0, 0, Theme.bgCard,
                w, h, Theme.bgHover
            );
            g2.setPaint(bgGrad);
        } else {
            g2.setColor(Theme.bgCard);
        }
        g2.fillRoundRect(0, 0, w, h, Theme.radiusL, Theme.radiusL);
        
        // Top accent border
        g2.setStroke(new BasicStroke(2.5f));
        GradientPaint accentGrad = new GradientPaint(
            0, 0, Theme.primary,
            w, 0, Theme.secondary
        );
        g2.setPaint(accentGrad);
        g2.drawLine(0, 0, w, 0);
        
        // Border
        g2.setStroke(new BasicStroke(hovered ? 1.5f : 1f));
        g2.setColor(hovered ? Theme.primary : Theme.borderLight);
        g2.drawRoundRect(0, 0, w - 1, h - 1, Theme.radiusL, Theme.radiusL);
        
        // Highlight effect on hover
        if (hovered) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
            g2.setColor(Theme.primary);
            g2.fillRoundRect(0, 0, w, h, Theme.radiusL, Theme.radiusL);
        }
        
        g2.dispose();
        super.paintComponent(g);
    }
}

