package ui.components;

import ui.core.Theme;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SkillCard extends JPanel {
    private boolean hovered = false;
    private final String skillName;
    private final String teacherName;
    private final double rating;
    private final int pointsRequired;
    private final String location;
    private final Runnable onClick;
    
    public SkillCard(String skillName, String teacherName, double rating, int pointsRequired, String location, Runnable onClick) {
        this.skillName = skillName;
        this.teacherName = teacherName;
        this.rating = rating;
        this.pointsRequired = pointsRequired;
        this.location = location;
        this.onClick = onClick;
        
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(Theme.spacingM, Theme.spacingM, Theme.spacingM, Theme.spacingM));
        setLayout(new BorderLayout());
        
        initializeComponents();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClick != null) onClick.run();
            }
        });
    }
    
    private void initializeComponents() {
        // Main content panel
        JPanel content = new JPanel(new BorderLayout(Theme.spacingS, Theme.spacingS));
        content.setOpaque(false);
        
        // Top section - Skill name and rating
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);
        
        JLabel skillLabel = new JLabel(skillName);
        skillLabel.setFont(Theme.headingS);
        skillLabel.setForeground(Theme.textPrimary);
        
        // Rating stars
        JPanel ratingPanel = createRatingPanel(rating);
        topSection.add(skillLabel, BorderLayout.WEST);
        topSection.add(ratingPanel, BorderLayout.EAST);
        
        // Middle section - Teacher info
        JLabel teacherLabel = new JLabel("👤 " + teacherName);
        teacherLabel.setFont(Theme.body);
        teacherLabel.setForeground(Theme.textSecondary);
        
        // Location
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
        
        JLabel pointsLabel = new JLabel(pointsRequired + " points");
        pointsLabel.setFont(Theme.body);
        pointsLabel.setForeground(Theme.primary);
        pointsLabel.setFont(pointsLabel.getFont().deriveFont(Font.BOLD));
        
        ModernButton learnButton = new ModernButton("Learn", ModernButton.ButtonStyle.PRIMARY);
        learnButton.setPreferredSize(new Dimension(100, 36));
        learnButton.addActionListener(e -> {
            if (onClick != null) onClick.run();
        });
        
        bottomSection.add(pointsLabel, BorderLayout.WEST);
        bottomSection.add(learnButton, BorderLayout.EAST);
        
        // Assemble content
        content.add(topSection, BorderLayout.NORTH);
        content.add(infoPanel, BorderLayout.CENTER);
        content.add(bottomSection, BorderLayout.SOUTH);
        
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
                panel.add(createStarLabel("★", false)); // Half star
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
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        int w = getWidth();
        int h = getHeight();
        
        // Shadow
        if (!hovered) {
            g2.setColor(new Color(0, 0, 0, 5));
            g2.fillRoundRect(2, 2, w - 4, h - 4, Theme.radiusL, Theme.radiusL);
        }
        
        // Card background
        g2.setColor(hovered ? Theme.bgHover : Theme.bgCard);
        g2.fillRoundRect(0, 0, w, h, Theme.radiusL, Theme.radiusL);
        
        // Border
        g2.setStroke(new BasicStroke(hovered ? 1.5f : 1f));
        g2.setColor(hovered ? Theme.primary : Theme.borderLight);
        g2.drawRoundRect(0, 0, w - 1, h - 1, Theme.radiusL, Theme.radiusL);
        
        // Hover effect
        if (hovered) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
            g2.setColor(Theme.primary);
            g2.fillRoundRect(0, 0, w, h, Theme.radiusL, Theme.radiusL);
        }
        
        g2.dispose();
        super.paintComponent(g);
    }
}

