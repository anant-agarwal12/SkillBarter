package ui.panels;

import ui.components.*;
import ui.core.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Professional Dashboard with stats, learning journey, and activity insights
 */
public class DashboardPanel extends JPanel {
    private UserManager userManager;
    private JLabel[] statLabels = new JLabel[4];
    
    public DashboardPanel(UserManager userManager) {
        this.userManager = userManager;
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Theme.BG_PRIMARY);
        setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, Theme.PADDING_XL, 
            Theme.PADDING_LARGE, Theme.PADDING_XL));
        
        initUI();
        loadData();
    }
    
    private void initUI() {
        // Hero Stats Cards (Top Row - 4 cards)
        JPanel statsRow = createStatsRow();
        
        // Main Content Area (2 columns)
        JPanel mainContent = new JPanel(new GridLayout(1, 2, Theme.PADDING_LARGE, Theme.PADDING_LARGE));
        mainContent.setOpaque(false);
        
        // Left Column - Learning Journey
        JPanel leftColumn = createLearningJourney();
        
        // Right Column - Activity & Insights
        JPanel rightColumn = createActivityInsights();
        
        mainContent.add(leftColumn);
        mainContent.add(rightColumn);
        
        // Quick Actions Bar (Bottom)
        JPanel quickActions = createQuickActions();
        
        add(statsRow, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
        add(quickActions, BorderLayout.SOUTH);
    }
    
    private JPanel createStatsRow() {
        JPanel panel = new JPanel(new GridLayout(1, 4, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_LARGE, 0));
        
        // Learning Hours card
        ProfessionalCard hoursCard = createStatCard(
            "Learning Hours", "24.5 hours", "+12% this month", 
            Theme.INFO_BLUE, "📚"
        );
        statLabels[0] = extractValueLabel(hoursCard);
        
        // Teaching Hours card
        ProfessionalCard teachingCard = createStatCard(
            "Teaching Hours", "18 hours", "3 sessions this week", 
            Theme.CORAL_ACCENT, "👨‍🏫"
        );
        statLabels[1] = extractValueLabel(teachingCard);
        
        // Points Earned card
        ProfessionalCard pointsCard = createStatCard(
            "Points Earned", "2,450 points", "+180 today", 
            Theme.SUCCESS_GREEN, "💰"
        );
        statLabels[2] = extractValueLabel(pointsCard);
        
        // Skill Rating card
        ProfessionalCard ratingCard = createStatCard(
            "Skill Rating", "4.8/5.0", "Based on 23 reviews", 
            Theme.WARNING_YELLOW, "⭐"
        );
        statLabels[3] = extractValueLabel(ratingCard);
        
        panel.add(hoursCard);
        panel.add(teachingCard);
        panel.add(pointsCard);
        panel.add(ratingCard);
        
        return panel;
    }
    
    private ProfessionalCard createStatCard(String title, String value, String subtitle, Color accent, String icon) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(Theme.getEmojiFont(24)); // Use Unicode-compatible font for emojis
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Theme.FONT_SMALL);
        titleLabel.setForeground(Theme.TEXT_SECONDARY);
        
        header.add(iconLabel, BorderLayout.WEST);
        header.add(titleLabel, BorderLayout.CENTER);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(Theme.FONT_TITLE);
        valueLabel.setForeground(accent);
        valueLabel.setName("valueLabel");
        
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(Theme.FONT_SMALL);
        subtitleLabel.setForeground(Theme.SUCCESS_GREEN);
        
        // Mini trend indicator
        JPanel trendPanel = createTrendIndicator(true);
        
        card.add(header);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(trendPanel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(subtitleLabel);
        
        return card;
    }
    
    private JPanel createTrendIndicator(boolean up) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(60, 20));
        
        // Simple trend visualization
        JLabel arrow = new JLabel(up ? "↑" : "↓");
        arrow.setFont(Theme.FONT_BODY);
        arrow.setForeground(Theme.SUCCESS_GREEN);
        
        panel.add(arrow);
        return panel;
    }
    
    private JPanel createLearningJourney() {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("🎓 Your Learning Journey");
        title.setFont(Theme.FONT_SUBTITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        
        // Progress card
        JPanel progressCard = createProgressCard("JavaScript Mastery", 65);
        content.add(progressCard);
        content.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        
        // Next Session card
        JPanel nextSessionCard = createNextSessionCard();
        content.add(nextSessionCard);
        content.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        
        // Recommended Matches
        JLabel matchesTitle = new JLabel("🤝 Recommended Matches");
        matchesTitle.setFont(Theme.FONT_BODY);
        matchesTitle.setForeground(Theme.TEXT_PRIMARY);
        content.add(matchesTitle);
        content.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        
        JPanel matchesContainer = new JPanel(new GridLayout(3, 1, 0, Theme.PADDING_SMALL));
        matchesContainer.setOpaque(false);
        
        String[][] matches = {
            {"Alex", "🎹 Piano Basics", "4.9"},
            {"Sarah", "🗣️ Spanish Conversation", "4.8"},
            {"Mike", "💻 Web Design", "4.7"}
        };
        
        for (String[] match : matches) {
            matchesContainer.add(createMatchCard(match[0], match[1], match[2]));
        }
        
        content.add(matchesContainer);
        
        card.add(title, BorderLayout.NORTH);
        card.add(content, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createProgressCard(String skill, int progress) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        JLabel skillLabel = new JLabel(skill);
        skillLabel.setFont(Theme.FONT_BODY);
        skillLabel.setForeground(Theme.TEXT_PRIMARY);
        
        JLabel progressLabel = new JLabel(progress + "%");
        progressLabel.setFont(Theme.FONT_BODY);
        progressLabel.setForeground(Theme.SUCCESS_GREEN);
        
        JPanel progressBar = createProgressBar(progress, Theme.SUCCESS_GREEN);
        
        card.add(skillLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(progressLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(progressBar);
        
        return card;
    }
    
    private JPanel createProgressBar(int progress, Color color) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int w = getWidth();
                int h = getHeight();
                
                // Background
                g2.setColor(Theme.BG_HOVER);
                g2.fillRoundRect(0, 0, w, h, Theme.RADIUS_SMALL, Theme.RADIUS_SMALL);
                
                // Progress
                int progressWidth = (w * progress) / 100;
                g2.setColor(color);
                g2.fillRoundRect(0, 0, progressWidth, h, Theme.RADIUS_SMALL, Theme.RADIUS_SMALL);
                
                g2.dispose();
            }
        };
        panel.setPreferredSize(new Dimension(0, 8));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 8));
        panel.setOpaque(false);
        return panel;
    }
    
    private JPanel createNextSessionCard() {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);
        
        JLabel sessionLabel = new JLabel("🗣️ Spanish Conversation with Maria");
        sessionLabel.setFont(Theme.FONT_BODY);
        sessionLabel.setForeground(Theme.TEXT_PRIMARY);
        
        JLabel timeLabel = new JLabel("⏰ Today at 4:00 PM");
        timeLabel.setFont(Theme.FONT_SMALL);
        timeLabel.setForeground(Theme.TEXT_SECONDARY);
        
        JLabel videoLabel = new JLabel("📹 Video Call");
        videoLabel.setFont(Theme.FONT_SMALL);
        videoLabel.setForeground(Theme.INFO_BLUE);
        
        left.add(sessionLabel);
        left.add(Box.createVerticalStrut(Theme.PADDING_XS));
        left.add(timeLabel);
        left.add(Box.createVerticalStrut(Theme.PADDING_XS));
        left.add(videoLabel);
        
        ModernButton joinBtn = new ModernButton("▶️ Join Session", ModernButton.ButtonStyle.PRIMARY);
        joinBtn.setBackground(Theme.SUCCESS_GREEN);
        joinBtn.setForeground(Theme.TEXT_LIGHT);
        
        card.add(left, BorderLayout.WEST);
        card.add(joinBtn, BorderLayout.EAST);
        
        return card;
    }
    
    private JPanel createMatchCard(String name, String skill, String rating) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(Theme.FONT_BODY);
        nameLabel.setForeground(Theme.TEXT_PRIMARY);
        
        JLabel skillLabel = new JLabel(skill);
        skillLabel.setFont(Theme.FONT_SMALL);
        skillLabel.setForeground(Theme.TEXT_SECONDARY);
        
        JLabel ratingLabel = new JLabel("⭐ " + rating);
        ratingLabel.setFont(Theme.FONT_SMALL);
        ratingLabel.setForeground(Theme.WARNING_YELLOW);
        
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);
        left.add(nameLabel);
        left.add(Box.createVerticalStrut(2));
        left.add(skillLabel);
        left.add(ratingLabel);
        
        ModernButton requestBtn = new ModernButton("✉️ Request", ModernButton.ButtonStyle.OUTLINE);
        requestBtn.setForeground(Theme.CORAL_ACCENT);
        
        card.add(left, BorderLayout.WEST);
        card.add(requestBtn, BorderLayout.EAST);
        
        return card;
    }
    
    private JPanel createActivityInsights() {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("📊 Activity & Insights");
        title.setFont(Theme.FONT_SUBTITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        
        // Weekly activity heatmap placeholder
        JPanel heatmapCard = createHeatmapCard();
        content.add(heatmapCard);
        content.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        
        // Recent Achievements
        JLabel achievementsTitle = new JLabel("🏆 Recent Achievements");
        achievementsTitle.setFont(Theme.FONT_BODY);
        achievementsTitle.setForeground(Theme.TEXT_PRIMARY);
        content.add(achievementsTitle);
        content.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        
        JPanel achievementsContainer = new JPanel(new GridLayout(3, 1, 0, Theme.PADDING_SMALL));
        achievementsContainer.setOpaque(false);
        
        String[] achievements = {
            "✅ First Session Complete",
            "🔥 5-Day Streak",
            "⭐ Top Rated Teacher"
        };
        
        for (String achievement : achievements) {
            achievementsContainer.add(createAchievementCard(achievement));
        }
        
        content.add(achievementsContainer);
        content.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        
        // Upcoming Sessions mini calendar
        JLabel calendarTitle = new JLabel("📅 Upcoming Sessions");
        calendarTitle.setFont(Theme.FONT_BODY);
        calendarTitle.setForeground(Theme.TEXT_PRIMARY);
        content.add(calendarTitle);
        content.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        
        JPanel calendarCard = createMiniCalendar();
        content.add(calendarCard);
        
        card.add(title, BorderLayout.NORTH);
        card.add(content, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createHeatmapCard() {
        ProfessionalCard card = new ProfessionalCard();
        card.setPreferredSize(new Dimension(0, 100));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        JLabel label = new JLabel("Weekly Activity Heatmap");
        label.setFont(Theme.FONT_SMALL);
        label.setForeground(Theme.TEXT_SECONDARY);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(label, BorderLayout.CENTER);
        return card;
    }
    
    private JPanel createAchievementCard(String text) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        
        JLabel label = new JLabel(text);
        label.setFont(Theme.FONT_BODY);
        label.setForeground(Theme.TEXT_PRIMARY);
        
        card.add(label, BorderLayout.WEST);
        return card;
    }
    
    private JPanel createMiniCalendar() {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new GridLayout(7, 7, 2, 2));
        
        String[] days = {"S", "M", "T", "W", "T", "F", "S"};
        for (String day : days) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(Theme.FONT_SMALL);
            label.setForeground(Theme.TEXT_MUTED);
            card.add(label);
        }
        
        // Add some date cells with dots
        for (int i = 0; i < 28; i++) {
            JLabel cell = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            cell.setFont(Theme.FONT_SMALL);
            cell.setForeground(Theme.TEXT_SECONDARY);
            if (i == 14 || i == 18) {
                cell.setText(cell.getText() + " •");
                cell.setForeground(Theme.CORAL_ACCENT);
            }
            card.add(cell);
        }
        
        return card;
    }
    
    private JPanel createQuickActions() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, 0, 0, 0));
        
        String[] actions = {"Find a Teacher", "Schedule Teaching", "Browse Marketplace", "View Messages"};
        String[] icons = {"👨‍🏫", "📅", "🛍️", "💬"};
        
        for (int i = 0; i < actions.length; i++) {
            ModernButton btn = new ModernButton(icons[i] + " " + actions[i], ModernButton.ButtonStyle.PRIMARY);
            btn.setBackground(Theme.NAVY_PRIMARY);
            btn.setForeground(Theme.TEXT_LIGHT);
            panel.add(btn);
        }
        
        return panel;
    }
    
    private JLabel extractValueLabel(JPanel card) {
        for (Component comp : card.getComponents()) {
            if (comp instanceof JLabel && "valueLabel".equals(((JLabel) comp).getName())) {
                return (JLabel) comp;
            }
        }
        return null;
    }
    
    private void loadData() {
        SwingUtilities.invokeLater(() -> {
            try {
                Long userId = userManager.getCurrentUserId();
                if (userId != null && statLabels[2] != null) {
                    Integer points = APIClient.getUserPoints(userId);
                    if (points != null) {
                        statLabels[2].setText(points + " points");
                    }
                }
            } catch (Exception e) {
                System.err.println("Error loading dashboard data: " + e.getMessage());
            }
        });
    }
}