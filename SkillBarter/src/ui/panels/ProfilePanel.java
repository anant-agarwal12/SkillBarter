package ui.panels;

import ui.components.*;
import ui.core.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Comprehensive Profile Panel
 * With tabs: About, Teaching, Learning, Reviews, Achievements
 */
public class ProfilePanel extends JPanel {
    private JTabbedPane tabs;
    private JLabel nameLabel, taglineLabel, locationLabel, memberSinceLabel;
    private JPanel hoursTaughtLabel, hoursLearnedLabel, pointsLabel, skillsLabel;
    private JLabel ratingLabel;
    
    public ProfilePanel() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Theme.BG_PRIMARY);
        setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, Theme.PADDING_XL, 
            Theme.PADDING_LARGE, Theme.PADDING_XL));
        
        initUI();
        loadProfile();
    }
    
    private void initUI() {
        // Header Section
        JPanel headerSection = createHeaderSection();
        
        // Profile Stats Bar
        JPanel statsBar = createStatsBar();
        
        // Tabs Navigation
        tabs = new JTabbedPane();
        tabs.setOpaque(true);
        tabs.setBackground(Theme.BG_PRIMARY);
        tabs.setForeground(Theme.TEXT_PRIMARY);
        tabs.setFont(Theme.FONT_BODY);
        
        // About Tab
        JPanel aboutTab = createAboutTab();
        tabs.addTab("About", aboutTab);
        
        // Teaching Tab
        JPanel teachingTab = createTeachingTab();
        tabs.addTab("Teaching", teachingTab);
        
        // Learning Tab
        JPanel learningTab = createLearningTab();
        tabs.addTab("Learning", learningTab);
        
        // Reviews Tab
        JPanel reviewsTab = createReviewsTab();
        tabs.addTab("Reviews", reviewsTab);
        
        // Achievements Tab
        JPanel achievementsTab = createAchievementsTab();
        tabs.addTab("Achievements", achievementsTab);
        
        add(headerSection, BorderLayout.NORTH);
        add(statsBar, BorderLayout.BEFORE_FIRST_LINE);
        add(tabs, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderSection() {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_XL, Theme.PADDING_XL, 
            Theme.PADDING_XL, Theme.PADDING_XL));
        
        // Left side - Profile info
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        
        // Avatar with edit overlay
        JPanel avatarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        avatarContainer.setOpaque(false);
        
        JPanel avatarPanel = createAvatarPanel("AM", 150);
        avatarContainer.add(avatarPanel);
        
        // Name and info
        nameLabel = new JLabel("Alexandra Martinez");
        nameLabel.setFont(Theme.FONT_TITLE);
        nameLabel.setForeground(Theme.TEXT_PRIMARY);
        
        taglineLabel = new JLabel("👨‍🏫 Teaching Spanish & 📸 Learning Photography");
        taglineLabel.setFont(Theme.FONT_BODY);
        taglineLabel.setForeground(Theme.TEXT_SECONDARY);
        
        JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.PADDING_SMALL, 0));
        locationPanel.setOpaque(false);
        
        locationLabel = new JLabel("📍 San Francisco, CA");
        locationLabel.setFont(Theme.FONT_SMALL);
        locationLabel.setForeground(Theme.TEXT_MUTED);
        
        memberSinceLabel = new JLabel("📅 Joined December 2024");
        memberSinceLabel.setFont(Theme.FONT_SMALL);
        memberSinceLabel.setForeground(Theme.TEXT_MUTED);
        
        locationPanel.add(locationLabel);
        locationPanel.add(new JLabel(" • "));
        locationPanel.add(memberSinceLabel);
        
        // Verification badge
        JLabel verifiedBadge = createBadge("✓ Verified Teacher", Theme.SUCCESS_GREEN);
        
        // Rating
        ratingLabel = new JLabel("⭐ 4.9 (127 reviews as teacher, 45 as student)");
        ratingLabel.setFont(Theme.FONT_BODY);
        ratingLabel.setForeground(Theme.WARNING_YELLOW);
        
        leftPanel.add(avatarContainer);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        leftPanel.add(nameLabel);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_XS));
        leftPanel.add(taglineLabel);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_XS));
        leftPanel.add(locationPanel);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        leftPanel.add(verifiedBadge);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        leftPanel.add(ratingLabel);
        
        // Right side - Action buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        
        ModernButton editBtn = new ModernButton("✏️ Edit Profile", ModernButton.ButtonStyle.OUTLINE);
        editBtn.setForeground(Theme.CORAL_ACCENT);
        editBtn.setPreferredSize(new Dimension(150, 36));
        
        ModernButton shareBtn = new ModernButton("🔗 Share Profile", ModernButton.ButtonStyle.OUTLINE);
        shareBtn.setForeground(Theme.INFO_BLUE);
        shareBtn.setPreferredSize(new Dimension(150, 36));
        
        ModernButton downloadBtn = new ModernButton("📥 Download Report", ModernButton.ButtonStyle.OUTLINE);
        downloadBtn.setForeground(Theme.NAVY_PRIMARY);
        downloadBtn.setPreferredSize(new Dimension(150, 36));
        
        rightPanel.add(editBtn);
        rightPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        rightPanel.add(shareBtn);
        rightPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        rightPanel.add(downloadBtn);
        
        card.add(leftPanel, BorderLayout.WEST);
        card.add(rightPanel, BorderLayout.EAST);
        
        return card;
    }
    
    private JPanel createStatsBar() {
        JPanel panel = new JPanel(new GridLayout(1, 4, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, 0, Theme.PADDING_LARGE, 0));
        
        hoursTaughtLabel = createStatLabel("👨‍🏫 248 Hours Taught", Theme.CORAL_ACCENT);
        hoursLearnedLabel = createStatLabel("📚 156 Hours Learned", Theme.SUCCESS_GREEN);
        pointsLabel = createStatLabel("💰 2,450 Points Balance", Theme.WARNING_YELLOW);
        skillsLabel = createStatLabel("🎯 23 Skills Mastered", Theme.INFO_BLUE);
        
        panel.add(hoursTaughtLabel);
        panel.add(hoursLearnedLabel);
        panel.add(pointsLabel);
        panel.add(skillsLabel);
        
        return panel;
    }
    
    private JPanel createStatLabel(String text, Color accent) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(Theme.FONT_BODY);
        label.setForeground(accent);
        
        card.add(label, BorderLayout.CENTER);
        return card;
    }
    
    private JPanel createAboutTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, 0, 0, 0));
        
        // Bio section
        ProfessionalCard bioCard = new ProfessionalCard();
        bioCard.setLayout(new BorderLayout());
        
        JLabel bioTitle = new JLabel("📝 Bio");
        bioTitle.setFont(Theme.FONT_SUBTITLE);
        bioTitle.setForeground(Theme.TEXT_PRIMARY);
        bioTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        JTextArea bioArea = new JTextArea("Passionate about teaching Spanish and learning photography. " +
            "I love connecting with students and sharing knowledge. " +
            "Always eager to learn new skills and help others grow!");
        bioArea.setFont(Theme.FONT_BODY);
        bioArea.setForeground(Theme.TEXT_PRIMARY);
        bioArea.setBackground(Theme.BG_CARD);
        bioArea.setBorder(BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1));
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        bioArea.setEditable(false);
        
        bioCard.add(bioTitle, BorderLayout.NORTH);
        bioCard.add(bioArea, BorderLayout.CENTER);
        
        panel.add(bioCard);
        panel.add(Box.createVerticalStrut(Theme.PADDING_LARGE));
        
        // Languages
        ProfessionalCard langCard = new ProfessionalCard();
        langCard.setLayout(new BoxLayout(langCard, BoxLayout.Y_AXIS));
        
        JLabel langTitle = new JLabel("🗣️ Languages");
        langTitle.setFont(Theme.FONT_SUBTITLE);
        langTitle.setForeground(Theme.TEXT_PRIMARY);
        langTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        String[] languages = {"🇪🇸 Spanish (Native)", "🇬🇧 English (Fluent)", "🇫🇷 French (Learning)"};
        for (String lang : languages) {
            JLabel langLabel = new JLabel("• " + lang);
            langLabel.setFont(Theme.FONT_BODY);
            langLabel.setForeground(Theme.TEXT_SECONDARY);
            langLabel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, 0, 0, 0));
            langCard.add(langLabel);
        }
        
        langCard.add(langTitle);
        
        panel.add(langCard);
        panel.add(Box.createVerticalStrut(Theme.PADDING_LARGE));
        
        // Availability
        ProfessionalCard availCard = new ProfessionalCard();
        availCard.setLayout(new BorderLayout());
        
        JLabel availTitle = new JLabel("📅 Availability");
        availTitle.setFont(Theme.FONT_SUBTITLE);
        availTitle.setForeground(Theme.TEXT_PRIMARY);
        availTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        // Simple calendar placeholder
        JPanel calendarPlaceholder = new JPanel();
        calendarPlaceholder.setPreferredSize(new Dimension(0, 200));
        calendarPlaceholder.setBackground(Theme.BG_HOVER);
        calendarPlaceholder.setBorder(BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1));
        
        JLabel calendarLabel = new JLabel("Weekly Calendar View", SwingConstants.CENTER);
        calendarLabel.setFont(Theme.FONT_BODY);
        calendarLabel.setForeground(Theme.TEXT_MUTED);
        calendarPlaceholder.add(calendarLabel);
        
        availCard.add(availTitle, BorderLayout.NORTH);
        availCard.add(calendarPlaceholder, BorderLayout.CENTER);
        
        panel.add(availCard);
        
        return panel;
    }
    
    private JPanel createTeachingTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, 0, 0, 0));
        
        // Skills I Teach
        JLabel title = new JLabel("👨‍🏫 Skills I Teach");
        title.setFont(Theme.FONT_SUBTITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        JPanel skillsPanel = new JPanel(new GridLayout(0, 2, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        skillsPanel.setOpaque(false);
        
        String[][] teachingSkills = {
            {"🗣️ Spanish Conversation", "📊 Beginner to Advanced", "💰 150", "👥 47 students", "⭐ 4.9"},
            {"📖 Spanish Grammar", "📊 Intermediate", "💰 120", "👥 23 students", "⭐ 4.8"},
            {"💬 Conversational Practice", "📊 All Levels", "💰 100", "👥 35 students", "⭐ 4.9"}
        };
        
        for (String[] skill : teachingSkills) {
            skillsPanel.add(createTeachingSkillCard(skill));
        }
        
        panel.add(title);
        panel.add(skillsPanel);
        panel.add(Box.createVerticalStrut(Theme.PADDING_LARGE));
        
        // Create New Course button
        ModernButton createBtn = new ModernButton("➕ Create New Course", ModernButton.ButtonStyle.PRIMARY);
        createBtn.setBackground(Theme.CORAL_ACCENT);
        createBtn.setForeground(Theme.TEXT_LIGHT);
        createBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(createBtn);
        
        return panel;
    }
    
    private JPanel createTeachingSkillCard(String[] data) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        JLabel skillLabel = new JLabel(data[0]);
        skillLabel.setFont(Theme.FONT_SUBTITLE);
        skillLabel.setForeground(Theme.TEXT_PRIMARY);
        
        JLabel levelLabel = new JLabel("Level: " + data[1]);
        levelLabel.setFont(Theme.FONT_SMALL);
        levelLabel.setForeground(Theme.TEXT_SECONDARY);
        
        JLabel rateLabel = new JLabel(data[2] + " points/hour");
        rateLabel.setFont(Theme.FONT_BODY);
        rateLabel.setForeground(Theme.CORAL_ACCENT);
        
        JLabel studentsLabel = new JLabel("Students taught: " + data[3]);
        studentsLabel.setFont(Theme.FONT_SMALL);
        studentsLabel.setForeground(Theme.TEXT_MUTED);
        
        JLabel ratingLabel = new JLabel("⭐ " + data[4]);
        ratingLabel.setFont(Theme.FONT_BODY);
        ratingLabel.setForeground(Theme.WARNING_YELLOW);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.PADDING_SMALL, 0));
        buttonPanel.setOpaque(false);
        
        ModernButton editBtn = new ModernButton("Edit", ModernButton.ButtonStyle.OUTLINE);
        editBtn.setForeground(Theme.INFO_BLUE);
        editBtn.setPreferredSize(new Dimension(70, 28));
        
        ModernButton previewBtn = new ModernButton("Preview", ModernButton.ButtonStyle.OUTLINE);
        previewBtn.setForeground(Theme.NAVY_PRIMARY);
        previewBtn.setPreferredSize(new Dimension(80, 28));
        
        buttonPanel.add(editBtn);
        buttonPanel.add(previewBtn);
        
        card.add(skillLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(levelLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(rateLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(studentsLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(ratingLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        card.add(buttonPanel);
        
        return card;
    }
    
    private JPanel createLearningTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, 0, 0, 0));
        
        // Currently Learning
        JLabel currentTitle = new JLabel("📚 Currently Learning");
        currentTitle.setFont(Theme.FONT_SUBTITLE);
        currentTitle.setForeground(Theme.TEXT_PRIMARY);
        currentTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        JPanel currentPanel = new JPanel(new GridLayout(0, 2, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        currentPanel.setOpaque(false);
        
        String[][] current = {
            {"📸 Portrait Photography", "65", "👤 Sarah Chen"},
            {"🍝 Italian Cooking", "30", "👤 Chef Marco"}
        };
        
        for (String[] skill : current) {
            currentPanel.add(createLearningCard(skill, false));
        }
        
        panel.add(currentTitle);
        panel.add(currentPanel);
        panel.add(Box.createVerticalStrut(Theme.PADDING_LARGE));
        
        // Completed Skills
        JLabel completedTitle = new JLabel("✅ Completed Skills");
        completedTitle.setFont(Theme.FONT_SUBTITLE);
        completedTitle.setForeground(Theme.TEXT_PRIMARY);
        completedTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        JPanel completedPanel = new JPanel(new GridLayout(0, 2, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        completedPanel.setOpaque(false);
        
        String[][] completed = {
            {"💻 Web Design Basics", "100", "👤 Sarah Lee"},
            {"🗣️ Spanish Conversation", "100", "👤 Maria Garcia"}
        };
        
        for (String[] skill : completed) {
            completedPanel.add(createLearningCard(skill, true));
        }
        
        panel.add(completedTitle);
        panel.add(completedPanel);
        
        return panel;
    }
    
    private JPanel createLearningCard(String[] data, boolean completed) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        if (completed) {
            card.setSelected(true);
        }
        
        JLabel skillLabel = new JLabel(data[0]);
        skillLabel.setFont(Theme.FONT_SUBTITLE);
        skillLabel.setForeground(Theme.TEXT_PRIMARY);
        
        if (!completed) {
            JLabel progressLabel = new JLabel(data[1] + "% complete");
            progressLabel.setFont(Theme.FONT_BODY);
            progressLabel.setForeground(Theme.SUCCESS_GREEN);
            
            JPanel progressBar = createProgressBar(Integer.parseInt(data[1]), Theme.SUCCESS_GREEN);
            
            card.add(progressLabel);
            card.add(Box.createVerticalStrut(Theme.PADDING_XS));
            card.add(progressBar);
        } else {
            JLabel completedLabel = new JLabel("✓ Completed");
            completedLabel.setFont(Theme.FONT_BODY);
            completedLabel.setForeground(Theme.SUCCESS_GREEN);
            card.add(completedLabel);
        }
        
        JLabel instructorLabel = new JLabel("Instructor: " + data[2]);
        instructorLabel.setFont(Theme.FONT_SMALL);
        instructorLabel.setForeground(Theme.TEXT_SECONDARY);
        
        card.add(skillLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(instructorLabel);
        
        if (completed) {
            JLabel badgeLabel = new JLabel("🏆 Certificate");
            badgeLabel.setFont(Theme.FONT_SMALL);
            badgeLabel.setForeground(Theme.WARNING_YELLOW);
            card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
            card.add(badgeLabel);
        }
        
        return card;
    }
    
    private JPanel createReviewsTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, 0, 0, 0));
        
        // Toggle and filter
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.PADDING_MEDIUM, 0));
        filterPanel.setOpaque(false);
        
        JButton teacherBtn = new JButton("👨‍🏫 Reviews as Teacher");
        teacherBtn.setFont(Theme.FONT_BODY);
        teacherBtn.setBackground(Theme.NAVY_PRIMARY);
        teacherBtn.setForeground(Theme.TEXT_LIGHT);
        teacherBtn.setOpaque(true);
        teacherBtn.setBorderPainted(false);
        
        JButton studentBtn = new JButton("📚 Reviews as Student");
        studentBtn.setFont(Theme.FONT_BODY);
        studentBtn.setBackground(Theme.BG_HOVER);
        studentBtn.setForeground(Theme.TEXT_PRIMARY);
        studentBtn.setOpaque(true);
        studentBtn.setBorderPainted(false);
        
        filterPanel.add(teacherBtn);
        filterPanel.add(studentBtn);
        
        // Reviews list
        JPanel reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        reviewsPanel.setOpaque(false);
        reviewsPanel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 0, 0, 0));
        
        String[][] reviews = {
            {"John Doe", "5", "Spanish Conversation Session", "Alexandra is patient and makes learning fun. Highly recommended!", "2 weeks ago"},
            {"Sarah Lee", "5", "Grammar Lesson", "Excellent teacher with great teaching methods.", "1 month ago"},
            {"Mike Johnson", "4", "Conversation Practice", "Good session, very helpful.", "2 months ago"}
        };
        
        for (String[] review : reviews) {
            reviewsPanel.add(createReviewCard(review));
            reviewsPanel.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        }
        
        panel.add(filterPanel);
        panel.add(reviewsPanel);
        
        return panel;
    }
    
    private JPanel createReviewCard(String[] data) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        
        JLabel nameLabel = new JLabel(data[0]);
        nameLabel.setFont(Theme.FONT_SUBTITLE);
        nameLabel.setForeground(Theme.TEXT_PRIMARY);
        
        // Star rating
        JLabel starsLabel = new JLabel("⭐".repeat(Integer.parseInt(data[1])));
        starsLabel.setFont(Theme.FONT_BODY);
        starsLabel.setForeground(Theme.WARNING_YELLOW);
        
        JLabel contextLabel = new JLabel(data[2]);
        contextLabel.setFont(Theme.FONT_SMALL);
        contextLabel.setForeground(Theme.TEXT_SECONDARY);
        
        JLabel reviewLabel = new JLabel(data[3]);
        reviewLabel.setFont(Theme.FONT_BODY);
        reviewLabel.setForeground(Theme.TEXT_PRIMARY);
        reviewLabel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, 0, 0, 0));
        
        JLabel dateLabel = new JLabel(data[4]);
        dateLabel.setFont(Theme.FONT_SMALL);
        dateLabel.setForeground(Theme.TEXT_MUTED);
        dateLabel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, 0, 0, 0));
        
        leftPanel.add(nameLabel);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_XS));
        leftPanel.add(starsLabel);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_XS));
        leftPanel.add(contextLabel);
        leftPanel.add(reviewLabel);
        leftPanel.add(dateLabel);
        
        // Avatar placeholder
        JPanel avatar = createAvatarPanel(String.valueOf(data[0].charAt(0)), 50);
        
        card.add(avatar, BorderLayout.WEST);
        card.add(leftPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createAchievementsTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, 0, 0, 0));
        
        // Badge gallery
        JPanel badgesPanel = new JPanel(new GridLayout(0, 4, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        badgesPanel.setOpaque(false);
        
        String[] achievements = {
            "🏆 Early Adopter", "⭐ Top 10% Teacher", "🔥 5-Star Streak", "📚 Learning Enthusiast",
            "💬 Master Communicator", "🤝 Community Helper", "⚡ Quick Responder", "🎯 Goal Achiever"
        };
        
        for (String achievement : achievements) {
            badgesPanel.add(createAchievementBadge(achievement, true));
        }
        
        // Locked badges
        String[] locked = {
            "🔒 100 Sessions", "🔒 1000 Points", "🔒 1 Year Member", "🔒 Perfect Rating"
        };
        
        for (String achievement : locked) {
            badgesPanel.add(createAchievementBadge(achievement, false));
        }
        
        panel.add(badgesPanel);
        
        return panel;
    }
    
    private JPanel createAchievementBadge(String text, boolean unlocked) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        if (!unlocked) {
            card.setEnabled(false);
        }
        
        JLabel iconLabel = new JLabel(text.split(" ")[0]);
        iconLabel.setFont(Theme.FONT_TITLE);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel nameLabel = new JLabel(text.substring(text.indexOf(" ") + 1));
        nameLabel.setFont(Theme.FONT_BODY);
        nameLabel.setForeground(unlocked ? Theme.TEXT_PRIMARY : Theme.TEXT_MUTED);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(nameLabel);
        
        if (!unlocked) {
            JLabel lockedLabel = new JLabel("🔒 Locked");
            lockedLabel.setFont(Theme.FONT_SMALL);
            lockedLabel.setForeground(Theme.TEXT_MUTED);
            lockedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(Box.createVerticalStrut(Theme.PADDING_XS));
            card.add(lockedLabel);
        }
        
        return card;
    }
    
    private JPanel createAvatarPanel(String initials, int size) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Theme.NAVY_PRIMARY);
                g2.fillOval(0, 0, size, size);
                
                g2.setFont(Theme.FONT_SUBTITLE);
                g2.setColor(Theme.TEXT_LIGHT);
                FontMetrics fm = g2.getFontMetrics();
                int x = (size - fm.stringWidth(initials.toString())) / 2;
                int y = (size + fm.getAscent()) / 2;
                g2.drawString(initials.toString(), x, y);
                
                g2.dispose();
            }
        };
        panel.setPreferredSize(new Dimension(size, size));
        panel.setMaximumSize(new Dimension(size, size));
        panel.setOpaque(false);
        return panel;
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
                
                g2.setColor(Theme.BG_HOVER);
                g2.fillRoundRect(0, 0, w, h, Theme.RADIUS_SMALL, Theme.RADIUS_SMALL);
                
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
    
    private JLabel createBadge(String text, Color bgColor) {
        JLabel badge = new JLabel(text);
        badge.setFont(Theme.FONT_SMALL);
        badge.setForeground(Theme.TEXT_LIGHT);
        badge.setBackground(bgColor);
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_XS, Theme.PADDING_SMALL, 
            Theme.PADDING_XS, Theme.PADDING_SMALL));
        return badge;
    }
    
    private void loadProfile() {
        nameLabel.setText("Alexandra Martinez");
        taglineLabel.setText("Teaching Spanish & Learning Photography");
        locationLabel.setText("📍 San Francisco, CA");
        memberSinceLabel.setText("Joined December 2024");
        ratingLabel.setText("⭐ 4.9 (127 reviews as teacher, 45 as student)");
        
        // Update stat labels (they're JPanels, so we need to update their content)
        updateStatLabel(hoursTaughtLabel, "248 Hours Taught");
        updateStatLabel(hoursLearnedLabel, "156 Hours Learned");
        updateStatLabel(pointsLabel, "2,450 Points Balance");
        updateStatLabel(skillsLabel, "23 Skills Mastered");
    }
    
    private void updateStatLabel(JPanel panel, String text) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setText(text);
                break;
            }
        }
    }
}