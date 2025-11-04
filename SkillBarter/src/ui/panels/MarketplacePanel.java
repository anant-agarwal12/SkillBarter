package ui.panels;

import ui.components.*;
import ui.core.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Professional Marketplace Panel
 * With search, featured section, grid layout, and categories
 */
public class MarketplacePanel extends JPanel {
    private JTextField searchField;
    private JPanel skillsContainer;
    private JPanel featuredContainer;
    private List<String> activeFilters = new ArrayList<>();
    
    public MarketplacePanel() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Theme.BG_PRIMARY);
        setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, Theme.PADDING_XL, 
            Theme.PADDING_LARGE, Theme.PADDING_XL));
        
        initUI();
        loadSkills();
    }
    
    private void initUI() {
        // Hero Search Bar
        JPanel searchSection = createHeroSearchBar();
        
        // Featured Section
        JPanel featuredSection = createFeaturedSection();
        
        // Main content with sidebar
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        
        // Categories Sidebar
        JPanel categoriesSidebar = createCategoriesSidebar();
        categoriesSidebar.setPreferredSize(new Dimension(220, 0));
        
        // Main grid area
        JPanel gridArea = new JPanel(new BorderLayout());
        gridArea.setOpaque(false);
        
        // Filters Active Bar and Sorting
        JPanel filterBar = createFilterBar();
        
        // Skills Grid
        skillsContainer = new JPanel(new GridLayout(0, 3, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        skillsContainer.setOpaque(false);
        
        JScrollPane scrollPane = new JScrollPane(skillsContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        
        // Bottom section
        JPanel bottomSection = createBottomSection();
        
        gridArea.add(filterBar, BorderLayout.NORTH);
        gridArea.add(scrollPane, BorderLayout.CENTER);
        gridArea.add(bottomSection, BorderLayout.SOUTH);
        
        mainContent.add(categoriesSidebar, BorderLayout.WEST);
        mainContent.add(gridArea, BorderLayout.CENTER);
        
        add(searchSection, BorderLayout.NORTH);
        add(featuredSection, BorderLayout.BEFORE_FIRST_LINE);
        add(mainContent, BorderLayout.CENTER);
    }
    
    private JPanel createHeroSearchBar() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_LARGE, 0));
        
        // Search input
        searchField = new JTextField("What do you want to learn today?");
        searchField.setFont(Theme.FONT_BODY);
        searchField.setForeground(Theme.TEXT_MUTED);
        searchField.setBackground(Theme.BG_CARD);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_LARGE, 
                Theme.PADDING_MEDIUM, Theme.PADDING_LARGE)
        ));
        searchField.setPreferredSize(new Dimension(0, 50));
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals("What do you want to learn today?")) {
                    searchField.setText("");
                    searchField.setForeground(Theme.TEXT_PRIMARY);
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("What do you want to learn today?");
                    searchField.setForeground(Theme.TEXT_MUTED);
                }
            }
        });
        
        // Search button
        ModernButton searchBtn = new ModernButton("🔍 Search", ModernButton.ButtonStyle.PRIMARY);
        searchBtn.setBackground(Theme.NAVY_PRIMARY);
        searchBtn.setForeground(Theme.TEXT_LIGHT);
        searchBtn.setPreferredSize(new Dimension(120, 50));
        searchBtn.addActionListener(e -> filterSkills());
        
        // Advanced filters button
        ModernButton filtersBtn = new ModernButton("⚙️ Filters", ModernButton.ButtonStyle.OUTLINE);
        filtersBtn.setForeground(Theme.TEXT_PRIMARY);
        filtersBtn.setPreferredSize(new Dimension(100, 50));
        
        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setOpaque(false);
        searchContainer.add(searchField, BorderLayout.CENTER);
        searchContainer.add(searchBtn, BorderLayout.EAST);
        searchContainer.add(Box.createHorizontalStrut(Theme.PADDING_SMALL), BorderLayout.AFTER_LAST_LINE);
        searchContainer.add(filtersBtn, BorderLayout.AFTER_LINE_ENDS);
        
        // AI Suggestions dropdown placeholder
        JPanel suggestionsPanel = createSuggestionsPanel();
        
        panel.add(searchContainer, BorderLayout.CENTER);
        panel.add(suggestionsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createSuggestionsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.PADDING_SMALL, Theme.PADDING_SMALL));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, 0, 0, 0));
        
        JLabel label = new JLabel("🔥 Popular: ");
        label.setFont(Theme.FONT_SMALL);
        label.setForeground(Theme.TEXT_SECONDARY);
        panel.add(label);
        
        String[] suggestions = {"🎸 Guitar", "🗣️ Spanish", "💻 Web Development", "📸 Photography"};
        for (String suggestion : suggestions) {
            JButton btn = new JButton(suggestion);
            btn.setFont(Theme.FONT_SMALL);
            btn.setForeground(Theme.INFO_BLUE);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addActionListener(e -> {
                searchField.setText(suggestion);
                filterSkills();
            });
            panel.add(btn);
        }
        
        return panel;
    }
    
    private JPanel createFeaturedSection() {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_LARGE, 0));
        
        JLabel title = new JLabel("⭐ Top Rated Teachers This Week");
        title.setFont(Theme.FONT_SUBTITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        featuredContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        featuredContainer.setOpaque(false);
        
        // Featured teacher cards
        String[][] teachers = {
            {"Sarah Chen", "📸 Portrait Photography", "4.9", "127 reviews", "150"},
            {"Marco Rossi", "🍝 Italian Cooking", "4.8", "89 reviews", "180"},
            {"Alex Kim", "🎹 Piano", "4.9", "156 reviews", "140"}
        };
        
        for (String[] teacher : teachers) {
            featuredContainer.add(createFeaturedTeacherCard(teacher));
        }
        
        JScrollPane scrollPane = new JScrollPane(featuredContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        
        card.add(title, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createFeaturedTeacherCard(String[] data) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(200, 250));
        
        // Avatar
        JPanel avatar = createAvatarPanel(data[0].charAt(0), 80);
        
        // Featured badge
        JLabel featuredBadge = createBadge("Featured", Theme.CORAL_ACCENT);
        featuredBadge.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Name
        JLabel nameLabel = new JLabel(data[0]);
        nameLabel.setFont(Theme.FONT_BODY);
        nameLabel.setForeground(Theme.TEXT_PRIMARY);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Skill
        JLabel skillLabel = new JLabel(data[1]);
        skillLabel.setFont(Theme.FONT_SMALL);
        skillLabel.setForeground(Theme.TEXT_SECONDARY);
        skillLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Rating
        JLabel ratingLabel = new JLabel("⭐ " + data[2] + " (" + data[3] + ")");
        ratingLabel.setFont(Theme.FONT_SMALL);
        ratingLabel.setForeground(Theme.WARNING_YELLOW);
        ratingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Rate
        JLabel rateLabel = new JLabel(data[4] + " points/hour");
        rateLabel.setFont(Theme.FONT_BODY);
        rateLabel.setForeground(Theme.NAVY_PRIMARY);
        rateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, Theme.PADDING_SMALL, 0));
        buttonPanel.setOpaque(false);
        
        ModernButton viewBtn = new ModernButton("👁️ View", ModernButton.ButtonStyle.OUTLINE);
        viewBtn.setForeground(Theme.INFO_BLUE);
        viewBtn.setPreferredSize(new Dimension(80, 28));
        
        ModernButton bookBtn = new ModernButton("📅 Book", ModernButton.ButtonStyle.PRIMARY);
        bookBtn.setBackground(Theme.CORAL_ACCENT);
        bookBtn.setForeground(Theme.TEXT_LIGHT);
        bookBtn.setPreferredSize(new Dimension(80, 28));
        
        buttonPanel.add(viewBtn);
        buttonPanel.add(bookBtn);
        
        card.add(avatar);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(featuredBadge);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(skillLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(ratingLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(rateLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        card.add(buttonPanel);
        
        return card;
    }
    
    private JPanel createCategoriesSidebar() {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        JLabel title = new JLabel("📚 Categories");
        title.setFont(Theme.FONT_SUBTITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        String[][] categories = {
            {"🌐 All Categories", "0"},
            {"🎨 Arts & Crafts", "12"},
            {"💻 Technology", "45"},
            {"🎵 Music", "28"},
            {"🗣️ Languages", "32"},
            {"🍳 Cooking", "18"},
            {"💼 Business", "22"},
            {"🏋️ Fitness", "15"},
            {"📸 Photography", "25"}
        };
        
        for (String[] category : categories) {
            JPanel categoryItem = new JPanel(new BorderLayout());
            categoryItem.setOpaque(false);
            categoryItem.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, 0, Theme.PADDING_SMALL, 0));
            
            JLabel catLabel = new JLabel(category[0]);
            catLabel.setFont(Theme.FONT_BODY);
            catLabel.setForeground(Theme.TEXT_PRIMARY);
            catLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            JLabel countLabel = new JLabel(category[1]);
            countLabel.setFont(Theme.FONT_SMALL);
            countLabel.setForeground(Theme.TEXT_MUTED);
            
            categoryItem.add(catLabel, BorderLayout.WEST);
            categoryItem.add(countLabel, BorderLayout.EAST);
            
            card.add(categoryItem);
        }
        
        return card;
    }
    
    private JPanel createFilterBar() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        // Active filters
        JPanel filtersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.PADDING_SMALL, 0));
        filtersPanel.setOpaque(false);
        
        if (!activeFilters.isEmpty()) {
            for (String filter : activeFilters) {
                JPanel filterPill = createFilterPill(filter);
                filtersPanel.add(filterPill);
            }
            
            JButton clearBtn = new JButton("Clear all");
            clearBtn.setFont(Theme.FONT_SMALL);
            clearBtn.setForeground(Theme.CORAL_ACCENT);
            clearBtn.setContentAreaFilled(false);
            clearBtn.setBorderPainted(false);
            clearBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            clearBtn.addActionListener(e -> {
                activeFilters.clear();
                filtersPanel.removeAll();
                filtersPanel.revalidate();
                filtersPanel.repaint();
            });
            filtersPanel.add(clearBtn);
        }
        
        // Sorting dropdown
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        sortPanel.setOpaque(false);
        
        JLabel sortLabel = new JLabel("Sort by: ");
        sortLabel.setFont(Theme.FONT_SMALL);
        sortLabel.setForeground(Theme.TEXT_SECONDARY);
        
        String[] sortOptions = {"Most Popular", "Highest Rated", "Lowest Points", "Newest", "Closest to Me"};
        JComboBox<String> sortCombo = new JComboBox<>(sortOptions);
        sortCombo.setFont(Theme.FONT_SMALL);
        sortCombo.setPreferredSize(new Dimension(150, 28));
        
        sortPanel.add(sortLabel);
        sortPanel.add(sortCombo);
        
        panel.add(filtersPanel, BorderLayout.WEST);
        panel.add(sortPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createFilterPill(String text) {
        JPanel pill = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pill.setOpaque(true);
        pill.setBackground(Theme.BG_HOVER);
        pill.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_XS, Theme.PADDING_SMALL, 
                Theme.PADDING_XS, Theme.PADDING_XS)
        ));
        
        JLabel label = new JLabel(text);
        label.setFont(Theme.FONT_SMALL);
        label.setForeground(Theme.TEXT_PRIMARY);
        
        JButton removeBtn = new JButton("❌");
        removeBtn.setFont(Theme.FONT_SMALL);
        removeBtn.setContentAreaFilled(false);
        removeBtn.setBorderPainted(false);
        removeBtn.setPreferredSize(new Dimension(16, 16));
        removeBtn.addActionListener(e -> {
            activeFilters.remove(text);
            ((JPanel) pill.getParent()).remove(pill);
            pill.getParent().revalidate();
            pill.getParent().repaint();
        });
        
        pill.add(label);
        pill.add(removeBtn);
        
        return pill;
    }
    
    private JPanel createBottomSection() {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, 0, 0, 0));
        
        JLabel title = new JLabel("❓ Can't find what you're looking for?");
        title.setFont(Theme.FONT_SUBTITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, Theme.PADDING_MEDIUM, 0));
        actionsPanel.setOpaque(false);
        
        ModernButton requestBtn = new ModernButton("✉️ Request a Skill", ModernButton.ButtonStyle.PRIMARY);
        requestBtn.setBackground(Theme.CORAL_ACCENT);
        requestBtn.setForeground(Theme.TEXT_LIGHT);
        
        JLabel orLabel = new JLabel("or");
        orLabel.setFont(Theme.FONT_BODY);
        orLabel.setForeground(Theme.TEXT_SECONDARY);
        
        JLabel teachingLink = new JLabel("👨‍🏫 start teaching and earn points");
        teachingLink.setFont(Theme.FONT_BODY);
        teachingLink.setForeground(Theme.INFO_BLUE);
        teachingLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        actionsPanel.add(requestBtn);
        actionsPanel.add(orLabel);
        actionsPanel.add(teachingLink);
        
        card.add(title, BorderLayout.NORTH);
        card.add(actionsPanel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private void loadSkills() {
        skillsContainer.removeAll();
        
        String[][] skills = {
            {"📸 Master Portrait Photography", "Sarah Chen", "4.8", "34", "💰 150 points/hour", "⏱️ 8 weeks", "📚 16 sessions", "📊 Intermediate", "📹 Video Sessions", "📁 Projects Included"},
            {"💻 Complete JavaScript Course", "John Doe", "4.9", "127", "💰 800 points", "⏱️ 12 weeks", "📚 24 sessions", "📊 Advanced", "📹 Video Sessions", "🎓 Certificate"},
            {"🗣️ Spanish Conversation", "Maria Garcia", "4.7", "89", "💰 120 points/hour", "⏱️ 6 weeks", "📚 12 sessions", "📊 Beginner", "📹 Video Sessions", "💬 Chat Support"},
            {"🍝 Italian Cooking Masterclass", "Chef Marco", "4.9", "156", "💰 200 points/hour", "⏱️ 4 weeks", "📚 8 sessions", "📊 Intermediate", "👥 In-Person", "📜 Recipes Included"},
            {"🎹 Piano for Beginners", "Alex Kim", "4.8", "78", "💰 140 points/hour", "⏱️ 10 weeks", "📚 20 sessions", "📊 Beginner", "📹 Video Sessions", "🎼 Sheet Music"},
            {"💻 Web Design Bootcamp", "Sarah Lee", "4.9", "234", "💰 900 points", "⏱️ 16 weeks", "📚 32 sessions", "📊 Advanced", "📹 Video Sessions", "📁 Projects", "🎓 Certificate"}
        };
        
        for (String[] skill : skills) {
            skillsContainer.add(createSkillCard(skill));
        }
        
        skillsContainer.revalidate();
        skillsContainer.repaint();
    }
    
    private JPanel createSkillCard(String[] data) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        // Thumbnail placeholder
        JPanel thumbnail = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int w = getWidth();
                int h = getHeight();
                
                g2.setColor(Theme.BG_HOVER);
                g2.fillRoundRect(0, 0, w, h, Theme.RADIUS_MEDIUM, Theme.RADIUS_MEDIUM);
                
                g2.setFont(Theme.FONT_TITLE);
                g2.setColor(Theme.TEXT_MUTED);
                String icon = "📚";
                FontMetrics fm = g2.getFontMetrics();
                int x = (w - fm.stringWidth(icon)) / 2;
                int y = (h + fm.getAscent()) / 2;
                g2.drawString(icon, x, y);
                
                g2.dispose();
            }
        };
        thumbnail.setPreferredSize(new Dimension(0, 150));
        thumbnail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        thumbnail.setOpaque(false);
        
        // Skill title
        JLabel titleLabel = new JLabel(data[0]);
        titleLabel.setFont(Theme.FONT_BODY);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);
        
        // Instructor
        JLabel instructorLabel = new JLabel("by " + data[1]);
        instructorLabel.setFont(Theme.FONT_SMALL);
        instructorLabel.setForeground(Theme.TEXT_SECONDARY);
        
        // Rating
        JLabel ratingLabel = new JLabel("⭐ " + data[2] + " (" + data[3] + " students)");
        ratingLabel.setFont(Theme.FONT_SMALL);
        ratingLabel.setForeground(Theme.WARNING_YELLOW);
        
        // Format badges
        JPanel badgesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.PADDING_XS, 0));
        badgesPanel.setOpaque(false);
        
        for (int i = 8; i < data.length; i++) {
            JLabel badge = createBadge(data[i], Theme.INFO_BLUE);
            badgesPanel.add(badge);
        }
        
        // Stats
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.PADDING_SMALL, 0));
        statsPanel.setOpaque(false);
        
        JLabel weeksLabel = new JLabel(data[5]);
        weeksLabel.setFont(Theme.FONT_SMALL);
        weeksLabel.setForeground(Theme.TEXT_MUTED);
        
        JLabel sessionsLabel = new JLabel("• " + data[6]);
        sessionsLabel.setFont(Theme.FONT_SMALL);
        sessionsLabel.setForeground(Theme.TEXT_MUTED);
        
        JLabel levelLabel = new JLabel("• " + data[7]);
        levelLabel.setFont(Theme.FONT_SMALL);
        levelLabel.setForeground(Theme.TEXT_MUTED);
        
        statsPanel.add(weeksLabel);
        statsPanel.add(sessionsLabel);
        statsPanel.add(levelLabel);
        
        // Pricing
        JLabel priceLabel = new JLabel(data[4]);
        priceLabel.setFont(Theme.FONT_SUBTITLE);
        priceLabel.setForeground(Theme.NAVY_PRIMARY);
        
        // CTA Button
        ModernButton enrollBtn = new ModernButton("✨ Enroll Now", ModernButton.ButtonStyle.PRIMARY);
        enrollBtn.setBackground(Theme.CORAL_ACCENT);
        enrollBtn.setForeground(Theme.TEXT_LIGHT);
        enrollBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(thumbnail);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(instructorLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(ratingLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(badgesPanel);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(statsPanel);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(priceLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(enrollBtn);
        
        return card;
    }
    
    private JPanel createAvatarPanel(char initial, int size) {
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
                String text = String.valueOf(initial);
                int x = (size - fm.stringWidth(text)) / 2;
                int y = (size + fm.getAscent()) / 2;
                g2.drawString(text, x, y);
                
                g2.dispose();
            }
        };
        panel.setPreferredSize(new Dimension(size, size));
        panel.setMaximumSize(new Dimension(size, size));
        panel.setOpaque(false);
        return panel;
    }
    
    private JLabel createBadge(String text, Color bgColor) {
        JLabel badge = new JLabel(text);
        badge.setFont(Theme.FONT_CAPTION);
        badge.setForeground(Theme.TEXT_LIGHT);
        badge.setBackground(bgColor);
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_XS, Theme.PADDING_SMALL, 
            Theme.PADDING_XS, Theme.PADDING_SMALL));
        return badge;
    }
    
    private void filterSkills() {
        String query = searchField.getText().toLowerCase();
        if (query.isEmpty() || query.equals("what do you want to learn today?")) {
            loadSkills();
            return;
        }
        
        // Filter logic here
        loadSkills();
    }
}