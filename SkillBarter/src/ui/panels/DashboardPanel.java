package ui.panels;

import ui.components.*;
import ui.core.*;
import ui.models.User;
import javax.swing.*;
import java.awt.*;

/**
 * Dashboard panel - Main view
 */
public class DashboardPanel extends JPanel {
    private JLabel pointsLabel, sessionsLabel, skillsLabel;
    private UserManager userManager;
    
    public DashboardPanel(UserManager userManager) {
        this.userManager = userManager;
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Theme.BG_PRIMARY);
        setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_LARGE, 
            Theme.PADDING_MEDIUM, Theme.PADDING_LARGE));
        
        initUI();
        loadData();
    }
    
    private void initUI() {
        // Title
        JLabel title = new JLabel("Dashboard");
        title.setFont(Theme.FONT_LARGE);
        title.setForeground(Theme.NEON_CYAN);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        // Stats panel
        JPanel statsPanel = createStatsPanel();
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        // Content panel
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        contentPanel.setOpaque(false);
        
        // Quick actions
        NeonCard quickActions = new NeonCard();
        quickActions.setNeonColor(Theme.NEON_PURPLE);
        quickActions.setLayout(new BoxLayout(quickActions, BoxLayout.Y_AXIS));
        
        JLabel actionsTitle = new JLabel("Quick Actions");
        actionsTitle.setFont(Theme.FONT_SUBTITLE);
        actionsTitle.setForeground(Theme.TEXT_PRIMARY);
        
        NeonButton browseBtn = new NeonButton("Browse Skills");
        browseBtn.setNeonColor(Theme.NEON_CYAN);
        
        NeonButton sessionsBtn = new NeonButton("My Sessions");
        sessionsBtn.setNeonColor(Theme.NEON_PINK);
        
        quickActions.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM, 
            Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        
        quickActions.add(actionsTitle);
        quickActions.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        quickActions.add(browseBtn);
        quickActions.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        quickActions.add(sessionsBtn);
        
        // Recent activity
        NeonCard recentActivity = new NeonCard();
        recentActivity.setNeonColor(Theme.NEON_GREEN);
        recentActivity.setLayout(new BoxLayout(recentActivity, BoxLayout.Y_AXIS));
        
        JLabel activityTitle = new JLabel("Recent Activity");
        activityTitle.setFont(Theme.FONT_SUBTITLE);
        activityTitle.setForeground(Theme.TEXT_PRIMARY);
        
        JLabel activity1 = new JLabel("Completed session");
        activity1.setFont(Theme.FONT_BODY);
        activity1.setForeground(Theme.TEXT_SECONDARY);
        
        JLabel activity2 = new JLabel("Earned points");
        activity2.setFont(Theme.FONT_BODY);
        activity2.setForeground(Theme.TEXT_SECONDARY);
        
        recentActivity.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM, 
            Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        
        recentActivity.add(activityTitle);
        recentActivity.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        recentActivity.add(activity1);
        recentActivity.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        recentActivity.add(activity2);
        
        contentPanel.add(quickActions);
        contentPanel.add(recentActivity);
        
        add(title, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        panel.setOpaque(false);
        
        // Points card
        NeonCard pointsCard = createStatCard("Points", "0", Theme.NEON_CYAN);
        JLabel[] pointsRef = new JLabel[1];
        extractValueLabel(pointsCard, pointsRef);
        pointsLabel = pointsRef[0];
        
        // Sessions card
        NeonCard sessionsCard = createStatCard("Sessions", "0", Theme.NEON_PURPLE);
        JLabel[] sessionsRef = new JLabel[1];
        extractValueLabel(sessionsCard, sessionsRef);
        sessionsLabel = sessionsRef[0];
        
        // Skills card
        NeonCard skillsCard = createStatCard("Skills", "0", Theme.NEON_PINK);
        JLabel[] skillsRef = new JLabel[1];
        extractValueLabel(skillsCard, skillsRef);
        skillsLabel = skillsRef[0];
        
        panel.add(pointsCard);
        panel.add(sessionsCard);
        panel.add(skillsCard);
        
        return panel;
    }
    
    private NeonCard createStatCard(String title, String value, Color neonColor) {
        NeonCard card = new NeonCard();
        card.setNeonColor(neonColor);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM, 
            Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Theme.FONT_BODY);
        titleLabel.setForeground(Theme.TEXT_SECONDARY);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(Theme.FONT_LARGE);
        valueLabel.setForeground(neonColor);
        valueLabel.setName("valueLabel");
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(valueLabel);
        
        return card;
    }
    
    private void extractValueLabel(JPanel card, JLabel[] ref) {
        for (Component comp : card.getComponents()) {
            if (comp instanceof JLabel && "valueLabel".equals(((JLabel) comp).getName())) {
                ref[0] = (JLabel) comp;
                return;
            }
        }
    }
    
    private void loadData() {
        SwingUtilities.invokeLater(() -> {
            try {
                Long userId = userManager.getCurrentUserId();
                if (userId != null) {
                    Integer points = APIClient.getUserPoints(userId);
                    if (pointsLabel != null) {
                        pointsLabel.setText(String.valueOf(points != null ? points : 0));
                    }
                    
                    java.util.List<java.util.Map<String, Object>> sessions = APIClient.getUpcomingSessions(userId);
                    if (sessionsLabel != null) {
                        sessionsLabel.setText(String.valueOf(sessions != null ? sessions.size() : 0));
                    }
                }
            } catch (Exception e) {
                System.err.println("Error loading data: " + e.getMessage());
            }
        });
    }
}

