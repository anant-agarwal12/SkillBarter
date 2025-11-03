package ui.components;

import ui.core.Theme;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class PremiumSkillCard extends FloatingCard {
    private boolean hovered = false;
    private final String skillName;
    private final String teacherName;
    private final double rating;
    private final int pointsRequired;
    private final String location;
    private final String category;
    private final Runnable onClick;
    private Color categoryColor;
    
    public PremiumSkillCard(String skillName, String teacherName, double rating, int pointsRequired, 
                           String location, String category, Runnable onClick) {
        super();
        this.skillName = skillName;
        this.teacherName = teacherName;
        this.rating = rating;
        this.pointsRequired = pointsRequired;
        this.location = location;
        this.category = category;
        this.onClick = onClick;
        
        // Assign category colors for visual variety
        this.categoryColor = getCategoryColor(category);
        setAccentColor(categoryColor);
        setGlowing(true);
        
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(380, 320));
        
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
    
    private Color getCategoryColor(String category) {
        switch (category.toLowerCase()) {
            case "programming": return new Color(20, 108, 227);
            case "design": return new Color(161, 13, 182);
            case "music": return new Color(255, 87, 34);
            case "language": return new Color(16, 185, 129);
            case "business": return new Color(251, 191, 36);
            default: return Theme.primary;
        }
    }
    
    private void initializeComponents() {
        // Top section with category badge and image placeholder
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);
        topSection.setPreferredSize(new Dimension(0, 140));
        
        // Category badge
        JLabel categoryBadge = new JLabel(category);
        categoryBadge.setFont(new Font("Segoe UI Semibold", Font.BOLD, 11));
        categoryBadge.setForeground(Theme.textInverse);
        categoryBadge.setOpaque(true);
        categoryBadge.setBackground(categoryColor);
        categoryBadge.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(),
            new EmptyBorder(6, 12, 6, 12)
        ));
        categoryBadge.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        
        JPanel badgeWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        badgeWrapper.setOpaque(false);
        badgeWrapper.setBorder(new EmptyBorder(12, 12, 0, 0));
        badgeWrapper.add(categoryBadge);
        
        topSection.add(badgeWrapper, BorderLayout.NORTH);
        
        // Content section
        JPanel contentSection = new JPanel();
        contentSection.setLayout(new BoxLayout(contentSection, BoxLayout.Y_AXIS));
        contentSection.setOpaque(false);
        contentSection.setBorder(new EmptyBorder(Theme.spacingM, Theme.spacingL, Theme.spacingM, Theme.spacingL));
        
        // Skill name
        JLabel skillLabel = new JLabel(skillName);
        skillLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        skillLabel.setForeground(Theme.textPrimary);
        skillLabel.setBorder(new EmptyBorder(0, 0, Theme.spacingS, 0));
        
        // Teacher info with rating
        JPanel teacherPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        teacherPanel.setOpaque(false);
        
        JLabel teacherLabel = new JLabel(teacherName);
        teacherLabel.setFont(Theme.body);
        teacherLabel.setForeground(Theme.textSecondary);
        
        JLabel separator = new JLabel(" • ");
        separator.setFont(Theme.bodySmall);
        separator.setForeground(Theme.textMuted);
        
        JPanel ratingPanel = createRatingPanel(rating);
        
        teacherPanel.add(teacherLabel);
        teacherPanel.add(separator);
        teacherPanel.add(ratingPanel);
        
        // Location
        JLabel locationLabel = new JLabel("Location: " + location);
        locationLabel.setFont(Theme.bodySmall);
        locationLabel.setForeground(Theme.textMuted);
        locationLabel.setBorder(new EmptyBorder(Theme.spacingXS, 0, 0, 0));
        
        // Bottom section - Points and button
        JPanel bottomSection = new JPanel(new BorderLayout());
        bottomSection.setOpaque(false);
        bottomSection.setBorder(new EmptyBorder(Theme.spacingM, 0, 0, 0));
        
        JLabel pointsLabel = new JLabel(pointsRequired + " points");
        pointsLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        pointsLabel.setForeground(categoryColor);
        
        VibrantButton learnButton = new VibrantButton("Enroll Now", VibrantButton.ButtonStyle.GRADIENT);
        learnButton.setCustomColor(categoryColor);
        learnButton.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        learnButton.setPreferredSize(new Dimension(140, 46));
        learnButton.addActionListener(e -> {
            if (onClick != null) onClick.run();
        });
        
        bottomSection.add(pointsLabel, BorderLayout.WEST);
        bottomSection.add(learnButton, BorderLayout.EAST);
        
        contentSection.add(skillLabel);
        contentSection.add(teacherPanel);
        contentSection.add(locationLabel);
        contentSection.add(Box.createVerticalGlue());
        contentSection.add(bottomSection);
        
        add(topSection, BorderLayout.NORTH);
        add(contentSection, BorderLayout.CENTER);
    }
    
    private JPanel createRatingPanel(double rating) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        panel.setOpaque(false);
        
        // Use text-based rating instead of stars
        JLabel ratingText = new JLabel("Rating: " + String.format("%.1f", rating));
        ratingText.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        ratingText.setForeground(Theme.rating);
        
        panel.add(ratingText);
        
        return panel;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        // Let FloatingCard handle base painting
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        int w = getWidth();
        int h = getHeight();
        
        // Vibrant top gradient section (where image would be) - more vibrant
        LinearGradientPaint topGradient = new LinearGradientPaint(
            0, 0, 0, 160,
            new float[]{0.0f, 0.5f, 1.0f},
            new Color[]{
                categoryColor,
                new Color(categoryColor.getRed(), categoryColor.getGreen(), categoryColor.getBlue(), 220),
                new Color(categoryColor.getRed(), categoryColor.getGreen(), categoryColor.getBlue(), 180)
            }
        );
        g2.setPaint(topGradient);
        g2.fillRoundRect(0, 0, w, 160, Theme.radiusXL, Theme.radiusXL);
        
        // Animated pattern overlay on top section - Optimized: reduced density
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
        g2.setColor(Color.WHITE);
        // Increased spacing from 25 to 40 for better performance
        for (int i = 0; i < w; i += 40) {
            for (int j = 0; j < 160; j += 40) {
                g2.fillOval(i, j, 3, 3);
            }
        }
        
        // Glass effect overlay on top
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        LinearGradientPaint glassOverlay = new LinearGradientPaint(
            0, 0, 0, 80,
            new float[]{0.0f, 1.0f},
            new Color[]{Color.WHITE, new Color(255, 255, 255, 0)}
        );
        g2.setPaint(glassOverlay);
        g2.fillRoundRect(0, 0, w, 80, Theme.radiusXL, Theme.radiusXL);
        
        g2.dispose();
        // Removed redundant super.paintComponent(g) call - FloatingCard already handled base painting
    }
}

