package ui.panels;

import ui.components.*;
import ui.core.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Professional Sessions Management Panel
 * With tabs, session cards, filters, and detail panels
 */
public class SessionsPanel extends JPanel {
    private JPanel upcomingContainer;
    private JPanel pastContainer;
    private JPanel requestsContainer;
    private JTabbedPane tabs;
    
    public SessionsPanel() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Theme.BG_PRIMARY);
        setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, Theme.PADDING_XL, 
            Theme.PADDING_LARGE, Theme.PADDING_XL));
        
        initUI();
        loadSessions();
    }
    
    private void initUI() {
        // Header Tabs with counts
        tabs = new JTabbedPane();
        tabs.setOpaque(true);
        tabs.setBackground(Theme.BG_PRIMARY);
        tabs.setForeground(Theme.TEXT_PRIMARY);
        tabs.setFont(Theme.FONT_BODY);
        
        // Upcoming Sessions
        upcomingContainer = new JPanel();
        upcomingContainer.setLayout(new BoxLayout(upcomingContainer, BoxLayout.Y_AXIS));
        upcomingContainer.setOpaque(false);
        upcomingContainer.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 0, 0, 0));
        
        JScrollPane upcomingScroll = new JScrollPane(upcomingContainer);
        upcomingScroll.setOpaque(false);
        upcomingScroll.getViewport().setOpaque(false);
        upcomingScroll.setBorder(null);
        
        // Past Sessions
        pastContainer = new JPanel();
        pastContainer.setLayout(new BoxLayout(pastContainer, BoxLayout.Y_AXIS));
        pastContainer.setOpaque(false);
        pastContainer.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 0, 0, 0));
        
        JScrollPane pastScroll = new JScrollPane(pastContainer);
        pastScroll.setOpaque(false);
        pastScroll.getViewport().setOpaque(false);
        pastScroll.setBorder(null);
        
        // Requests
        requestsContainer = new JPanel();
        requestsContainer.setLayout(new BoxLayout(requestsContainer, BoxLayout.Y_AXIS));
        requestsContainer.setOpaque(false);
        requestsContainer.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 0, 0, 0));
        
        JScrollPane requestsScroll = new JScrollPane(requestsContainer);
        requestsScroll.setOpaque(false);
        requestsScroll.getViewport().setOpaque(false);
        requestsScroll.setBorder(null);
        
        tabs.addTab("📅 Upcoming (4)", upcomingScroll);
        tabs.addTab("✅ Past", pastScroll);
        tabs.addTab("📬 Requests (2)", requestsScroll);
        
        // Main layout with sidebar
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        
        // Filters Sidebar
        JPanel sidebar = createFiltersSidebar();
        sidebar.setPreferredSize(new Dimension(250, 0));
        
        mainContent.add(sidebar, BorderLayout.WEST);
        mainContent.add(tabs, BorderLayout.CENTER);
        
        add(mainContent, BorderLayout.CENTER);
    }
    
    private JPanel createFiltersSidebar() {
        ProfessionalCard sidebarCard = new ProfessionalCard();
        sidebarCard.setLayout(new BoxLayout(sidebarCard, BoxLayout.Y_AXIS));
        
        JLabel title = new JLabel("🔍 Filters");
        title.setFont(Theme.FONT_SUBTITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        // Session Type Toggle
        JLabel typeLabel = new JLabel("📋 Session Type");
        typeLabel.setFont(Theme.FONT_BODY);
        typeLabel.setForeground(Theme.TEXT_SECONDARY);
        typeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_SMALL, 0));
        
        JPanel typeToggle = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        typeToggle.setOpaque(false);
        
        JButton allBtn = createFilterButton("All", true);
        JButton teachingBtn = createFilterButton("Teaching", false);
        JButton learningBtn = createFilterButton("Learning", false);
        
        typeToggle.add(allBtn);
        typeToggle.add(teachingBtn);
        typeToggle.add(learningBtn);
        
        // Date Range
        JLabel dateLabel = new JLabel("📆 Date Range");
        dateLabel.setFont(Theme.FONT_BODY);
        dateLabel.setForeground(Theme.TEXT_SECONDARY);
        dateLabel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 0, Theme.PADDING_SMALL, 0));
        
        JPanel datePanel = new JPanel(new GridLayout(2, 1, 0, Theme.PADDING_SMALL));
        datePanel.setOpaque(false);
        
        JTextField fromDate = new JTextField("Start Date");
        fromDate.setFont(Theme.FONT_SMALL);
        fromDate.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, Theme.PADDING_SMALL, 
                Theme.PADDING_SMALL, Theme.PADDING_SMALL)
        ));
        
        JTextField toDate = new JTextField("End Date");
        toDate.setFont(Theme.FONT_SMALL);
        toDate.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, Theme.PADDING_SMALL, 
                Theme.PADDING_SMALL, Theme.PADDING_SMALL)
        ));
        
        datePanel.add(fromDate);
        datePanel.add(toDate);
        
        // Skill Category
        JLabel categoryLabel = new JLabel("📚 Skill Category");
        categoryLabel.setFont(Theme.FONT_BODY);
        categoryLabel.setForeground(Theme.TEXT_SECONDARY);
        categoryLabel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 0, Theme.PADDING_SMALL, 0));
        
        String[] categories = {"Programming", "Languages", "Design", "Music", "Business"};
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setOpaque(false);
        
        for (String cat : categories) {
            JCheckBox checkbox = new JCheckBox(cat);
            checkbox.setFont(Theme.FONT_SMALL);
            checkbox.setForeground(Theme.TEXT_PRIMARY);
            checkbox.setOpaque(false);
            categoryPanel.add(checkbox);
        }
        
        // Session Type
        JLabel sessionTypeLabel = new JLabel("🎥 Format");
        sessionTypeLabel.setFont(Theme.FONT_BODY);
        sessionTypeLabel.setForeground(Theme.TEXT_SECONDARY);
        sessionTypeLabel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 0, Theme.PADDING_SMALL, 0));
        
        JPanel sessionTypePanel = new JPanel();
        sessionTypePanel.setLayout(new BoxLayout(sessionTypePanel, BoxLayout.Y_AXIS));
        sessionTypePanel.setOpaque(false);
        
        String[] types = {"Video", "In-Person", "Chat-Only"};
        for (String type : types) {
            JCheckBox checkbox = new JCheckBox(type);
            checkbox.setFont(Theme.FONT_SMALL);
            checkbox.setForeground(Theme.TEXT_PRIMARY);
            checkbox.setOpaque(false);
            sessionTypePanel.add(checkbox);
        }
        
        sidebarCard.add(title);
        sidebarCard.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        sidebarCard.add(typeLabel);
        sidebarCard.add(typeToggle);
        sidebarCard.add(dateLabel);
        sidebarCard.add(datePanel);
        sidebarCard.add(categoryLabel);
        sidebarCard.add(categoryPanel);
        sidebarCard.add(sessionTypeLabel);
        sidebarCard.add(sessionTypePanel);
        
        return sidebarCard;
    }
    
    private JButton createFilterButton(String text, boolean selected) {
        JButton btn = new JButton(text);
        btn.setFont(Theme.FONT_SMALL);
        btn.setPreferredSize(new Dimension(70, 28));
        btn.setMargin(new Insets(4, 8, 4, 8));
        
        if (selected) {
            btn.setBackground(Theme.NAVY_PRIMARY);
            btn.setForeground(Theme.TEXT_LIGHT);
        } else {
            btn.setBackground(Theme.BG_HOVER);
            btn.setForeground(Theme.TEXT_PRIMARY);
        }
        
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        
        return btn;
    }
    
    private void loadSessions() {
        upcomingContainer.removeAll();
        pastContainer.removeAll();
        requestsContainer.removeAll();
        
        // Upcoming sessions
        String[][] upcoming = {
            {"🍝 Italian Cooking Basics", "Chef Marco", "⏰ Tomorrow, 3:00 PM - 4:00 PM", "📚 1-on-1 Learning", "150", "💸 -100 points", "📹 Video Call", "📍 2.3 mi away"},
            {"🗣️ Spanish Conversation", "Maria Garcia", "⏰ Friday, 10:00 AM - 11:30 AM", "📚 1-on-1 Learning", "120", "💸 -80 points", "📹 Video Call", ""}
        };
        
        for (String[] session : upcoming) {
            upcomingContainer.add(createDetailedSessionCard(session, true));
            upcomingContainer.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        }
        
        // Past sessions
        String[][] past = {
            {"Web Development", "John Smith", "Jan 15, 2025", "Completed", "100", "", "Video Call", ""},
            {"UI/UX Design", "Sarah Chen", "Jan 10, 2025", "Completed", "150", "", "In-Person", ""}
        };
        
        for (String[] session : past) {
            pastContainer.add(createDetailedSessionCard(session, false));
            pastContainer.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        }
        
        // Session requests
        String[][] requests = {
            {"Piano Lessons", "Alex Johnson", "Waiting for your response", "Request", "180", "", "Video Call", ""}
        };
        
        for (String[] session : requests) {
            requestsContainer.add(createDetailedSessionCard(session, true));
            requestsContainer.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        }
        
        upcomingContainer.revalidate();
        pastContainer.revalidate();
        requestsContainer.revalidate();
    }
    
    private JPanel createDetailedSessionCard(String[] data, boolean upcoming) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        
        // Left side - Main info
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        
        // Time/Date
        JLabel timeLabel = new JLabel(data[2]);
        timeLabel.setFont(Theme.FONT_BODY);
        timeLabel.setForeground(Theme.TEXT_SECONDARY);
        
        // Avatar placeholder
        JPanel avatarPanel = createAvatarPanel(data[1].charAt(0));
        
        // Skill name
        JLabel skillLabel = new JLabel(data[0]);
        skillLabel.setFont(Theme.FONT_SUBTITLE);
        skillLabel.setForeground(Theme.TEXT_PRIMARY);
        
        // Tutor/Student name
        JLabel nameLabel = new JLabel("with " + data[1]);
        nameLabel.setFont(Theme.FONT_BODY);
        nameLabel.setForeground(Theme.TEXT_SECONDARY);
        
        // Tags - All in one panel with consistent alignment
        JPanel tagsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.PADDING_SMALL, 0));
        tagsPanel.setOpaque(false);
        tagsPanel.setAlignmentY(Component.TOP_ALIGNMENT); // Align all tags at top
        
        // Session type badge
        JLabel typeBadge = createBadge(data[3], data[3].contains("Teaching") ? Theme.CORAL_ACCENT : Theme.SUCCESS_GREEN);
        
        String[] tags = data[5].isEmpty() ? new String[]{data[0], "Beginner Friendly"} : 
            new String[]{data[0], "Beginner Friendly", data[6]};
        
        for (String tag : tags) {
            if (!tag.isEmpty()) {
                JLabel tagLabel = createTagLabel(tag);
                tagsPanel.add(tagLabel);
            }
        }
        
        // Points badge
        if (!data[5].isEmpty()) {
            JLabel pointsBadge = createBadge(data[5], data[5].startsWith("+") ? Theme.SUCCESS_GREEN : Theme.CORAL_ACCENT);
            tagsPanel.add(pointsBadge);
        }
        
        // Location tag - use consistent styling
        if (!data[7].isEmpty()) {
            JLabel locationLabel = createTagLabel("📍 " + data[7]); // Use same tag styling
            tagsPanel.add(locationLabel);
        }
        
        leftPanel.add(timeLabel);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        leftPanel.add(avatarPanel);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        leftPanel.add(skillLabel);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_XS));
        leftPanel.add(nameLabel);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        leftPanel.add(typeBadge);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        leftPanel.add(tagsPanel);
        
        // Right side - Actions
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        
        if (upcoming && !data[3].equals("Completed")) {
            ModernButton primaryBtn = new ModernButton("▶️ Join Video Call", ModernButton.ButtonStyle.PRIMARY);
            primaryBtn.setBackground(Theme.SUCCESS_GREEN);
            primaryBtn.setForeground(Theme.TEXT_LIGHT);
            primaryBtn.setPreferredSize(new Dimension(140, 36));
            
            ModernButton secondaryBtn = new ModernButton("🔄 Reschedule", ModernButton.ButtonStyle.OUTLINE);
            secondaryBtn.setForeground(Theme.CORAL_ACCENT);
            secondaryBtn.setPreferredSize(new Dimension(140, 32));
            
            ModernButton messageBtn = new ModernButton("💬 Message", ModernButton.ButtonStyle.OUTLINE);
            messageBtn.setForeground(Theme.INFO_BLUE);
            messageBtn.setPreferredSize(new Dimension(140, 32));
            
            rightPanel.add(primaryBtn);
            rightPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
            rightPanel.add(secondaryBtn);
            rightPanel.add(Box.createVerticalStrut(Theme.PADDING_XS));
            rightPanel.add(messageBtn);
        } else if (data[3].equals("Request")) {
            ModernButton acceptBtn = new ModernButton("✅ Accept", ModernButton.ButtonStyle.PRIMARY);
            acceptBtn.setBackground(Theme.SUCCESS_GREEN);
            acceptBtn.setForeground(Theme.TEXT_LIGHT);
            acceptBtn.setPreferredSize(new Dimension(140, 36));
            
            ModernButton declineBtn = new ModernButton("❌ Decline", ModernButton.ButtonStyle.OUTLINE);
            declineBtn.setForeground(Theme.CORAL_ACCENT);
            declineBtn.setPreferredSize(new Dimension(140, 32));
            
            rightPanel.add(acceptBtn);
            rightPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
            rightPanel.add(declineBtn);
        } else {
            JLabel completedLabel = new JLabel("✓ Completed");
            completedLabel.setFont(Theme.FONT_BODY);
            completedLabel.setForeground(Theme.SUCCESS_GREEN);
            rightPanel.add(completedLabel);
        }
        
        card.add(leftPanel, BorderLayout.WEST);
        card.add(rightPanel, BorderLayout.EAST);
        
        return card;
    }
    
    private JPanel createAvatarPanel(char initial) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int size = Math.min(getWidth(), getHeight());
                g2.setColor(Theme.NAVY_PRIMARY);
                g2.fillOval(0, 0, size, size);
                
                g2.setFont(Theme.FONT_SUBTITLE);
                g2.setColor(Theme.TEXT_LIGHT);
                FontMetrics fm = g2.getFontMetrics();
                String text = String.valueOf(initial);
                int x = (size - fm.stringWidth(text)) / 2;
                int y = (size + fm.getAscent()) / 2;
                g2.drawString(text, x, y);
                
                // Online indicator
                g2.setColor(Theme.SUCCESS_GREEN);
                g2.fillOval(size - 12, size - 12, 10, 10);
                
                g2.dispose();
            }
        };
        panel.setPreferredSize(new Dimension(60, 60));
        panel.setMaximumSize(new Dimension(60, 60));
        panel.setOpaque(false);
        return panel;
    }
    
    private JLabel createTagLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.FONT_SMALL);
        label.setForeground(Theme.TEXT_SECONDARY);
        label.setBackground(Theme.BG_HOVER);
        label.setOpaque(true);
        label.setVerticalAlignment(SwingConstants.CENTER); // Center text vertically
        label.setVerticalTextPosition(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_XS, Theme.PADDING_SMALL, 
                Theme.PADDING_XS, Theme.PADDING_SMALL)
        ));
        // Set consistent preferred height for all tags
        label.setPreferredSize(new Dimension(label.getPreferredSize().width, 24));
        label.setMinimumSize(new Dimension(label.getMinimumSize().width, 24));
        label.setMaximumSize(new Dimension(label.getMaximumSize().width, 24));
        return label;
    }
    
    private JLabel createBadge(String text, Color bgColor) {
        JLabel badge = new JLabel(text);
        badge.setFont(Theme.FONT_SMALL);
        badge.setForeground(Theme.TEXT_LIGHT);
        badge.setBackground(bgColor);
        badge.setOpaque(true);
        badge.setVerticalAlignment(SwingConstants.CENTER); // Center text vertically
        badge.setVerticalTextPosition(SwingConstants.CENTER);
        badge.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_XS, Theme.PADDING_SMALL, 
            Theme.PADDING_XS, Theme.PADDING_SMALL));
        // Set consistent preferred height matching tags
        badge.setPreferredSize(new Dimension(badge.getPreferredSize().width, 24));
        badge.setMinimumSize(new Dimension(badge.getMinimumSize().width, 24));
        badge.setMaximumSize(new Dimension(badge.getMaximumSize().width, 24));
        return badge;
    }
}