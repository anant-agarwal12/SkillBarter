package ui.panels;

import ui.components.*;
import ui.core.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Professional Wallet Panel
 * With balance card, stats, transaction history, earning opportunities
 */
public class WalletPanel extends JPanel {
    private JLabel balanceLabel;
    private JLabel usdLabel;
    private JPanel transactionsContainer;
    private JTabbedPane transactionTabs;
    
    public WalletPanel() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Theme.BG_PRIMARY);
        setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, Theme.PADDING_XL, 
            Theme.PADDING_LARGE, Theme.PADDING_XL));
        
        initUI();
        loadData();
    }
    
    private void initUI() {
        // Balance Card (Hero)
        JPanel balanceCard = createBalanceCard();
        
        // Quick Stats (3 mini cards)
        JPanel statsRow = createQuickStats();
        
        // Main content area
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        
        // Transaction History with tabs
        JPanel transactionSection = createTransactionSection();
        
        // Bottom section with earning opportunities and packages
        JPanel bottomSection = createBottomSection();
        
        mainContent.add(transactionSection, BorderLayout.CENTER);
        mainContent.add(bottomSection, BorderLayout.SOUTH);
        
        add(balanceCard, BorderLayout.NORTH);
        add(statsRow, BorderLayout.BEFORE_FIRST_LINE);
        add(mainContent, BorderLayout.CENTER);
    }
    
    private JPanel createBalanceCard() {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_XL, Theme.PADDING_XL, 
            Theme.PADDING_XL, Theme.PADDING_XL));
        
        // Gradient background effect
        card = new ProfessionalCard() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int w = getWidth();
                int h = getHeight();
                
                // Gradient background
                LinearGradientPaint gradient = new LinearGradientPaint(
                    0, 0, w, h,
                    new float[]{0.0f, 0.5f, 1.0f},
                    new Color[]{
                        new Color(59, 130, 246, 20),
                        new Color(139, 92, 246, 15),
                        new Color(34, 197, 94, 20)
                    }
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, w, h, Theme.RADIUS_LARGE, Theme.RADIUS_LARGE);
                
                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_XL, Theme.PADDING_XL, 
            Theme.PADDING_XL, Theme.PADDING_XL));
        
        // Left side - Balance info
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        
        JLabel balanceTitle = new JLabel("💰 Total Points");
        balanceTitle.setFont(Theme.FONT_BODY);
        balanceTitle.setForeground(Theme.TEXT_SECONDARY);
        
        balanceLabel = new JLabel("2,450");
        balanceLabel.setFont(Theme.FONT_LARGE);
        balanceLabel.setForeground(Theme.NAVY_PRIMARY);
        
        usdLabel = new JLabel("💵 ≈ $24.50 value");
        usdLabel.setFont(Theme.FONT_BODY);
        usdLabel.setForeground(Theme.TEXT_SECONDARY);
        
        // Sparkline graph placeholder
        JPanel sparklinePanel = createSparklineGraph();
        sparklinePanel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 0, 0, 0));
        
        leftPanel.add(balanceTitle);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        leftPanel.add(balanceLabel);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_XS));
        leftPanel.add(usdLabel);
        leftPanel.add(sparklinePanel);
        
        // Right side - Action buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        
        ModernButton earnBtn = new ModernButton("💰 Earn Points", ModernButton.ButtonStyle.PRIMARY);
        earnBtn.setBackground(Theme.SUCCESS_GREEN);
        earnBtn.setForeground(Theme.TEXT_LIGHT);
        earnBtn.setPreferredSize(new Dimension(160, 40));
        
        ModernButton buyBtn = new ModernButton("💳 Buy Points", ModernButton.ButtonStyle.OUTLINE);
        buyBtn.setForeground(Theme.CORAL_ACCENT);
        buyBtn.setPreferredSize(new Dimension(160, 40));
        
        rightPanel.add(earnBtn);
        rightPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        rightPanel.add(buyBtn);
        
        card.add(leftPanel, BorderLayout.WEST);
        card.add(rightPanel, BorderLayout.EAST);
        
        return card;
    }
    
    private JPanel createSparklineGraph() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int w = getWidth();
                int h = getHeight();
                
                // Simple sparkline visualization
                int[] values = {50, 65, 70, 80, 75, 90, 85, 95, 100};
                int pointWidth = w / values.length;
                
                g2.setColor(Theme.SUCCESS_GREEN);
                g2.setStroke(new BasicStroke(2f));
                
                for (int i = 0; i < values.length - 1; i++) {
                    int x1 = i * pointWidth;
                    int y1 = h - (values[i] * h / 100);
                    int x2 = (i + 1) * pointWidth;
                    int y2 = h - (values[i + 1] * h / 100);
                    g2.drawLine(x1, y1, x2, y2);
                }
                
                g2.dispose();
            }
        };
        panel.setPreferredSize(new Dimension(0, 40));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        panel.setOpaque(false);
        return panel;
    }
    
    private JPanel createQuickStats() {
        JPanel panel = new JPanel(new GridLayout(1, 3, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, 0, Theme.PADDING_LARGE, 0));
        
        // Total Earned
        ProfessionalCard earnedCard = createStatCard(
            "Total Earned", "3,200 points", "↑ From 18 teaching sessions", 
            Theme.SUCCESS_GREEN, "💰"
        );
        
        // Total Spent
        ProfessionalCard spentCard = createStatCard(
            "Total Spent", "750 points", "📚 On 5 learning sessions", 
            Theme.CORAL_ACCENT, "📖"
        );
        
        // This Month
        ProfessionalCard monthCard = createStatCard(
            "This Month", "+450 points", "📈 Monthly chart", 
            Theme.INFO_BLUE, "📅"
        );
        
        panel.add(earnedCard);
        panel.add(spentCard);
        panel.add(monthCard);
        
        return panel;
    }
    
    private ProfessionalCard createStatCard(String title, String value, String subtitle, Color accent, String icon) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(Theme.FONT_TITLE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Theme.FONT_SMALL);
        titleLabel.setForeground(Theme.TEXT_SECONDARY);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(Theme.FONT_SUBTITLE);
        valueLabel.setForeground(accent);
        
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(Theme.FONT_SMALL);
        subtitleLabel.setForeground(Theme.TEXT_MUTED);
        
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(subtitleLabel);
        
        return card;
    }
    
    private JPanel createTransactionSection() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, 0, 0, 0));
        
        JLabel title = new JLabel("📜 Transaction History");
        title.setFont(Theme.FONT_SUBTITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        // Tabbed view
        transactionTabs = new JTabbedPane();
        transactionTabs.setOpaque(true);
        transactionTabs.setBackground(Theme.BG_PRIMARY);
        transactionTabs.setForeground(Theme.TEXT_PRIMARY);
        transactionTabs.setFont(Theme.FONT_BODY);
        
        // Transactions container
        transactionsContainer = new JPanel();
        transactionsContainer.setLayout(new BoxLayout(transactionsContainer, BoxLayout.Y_AXIS));
        transactionsContainer.setOpaque(false);
        transactionsContainer.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 0, 0, 0));
        
        JScrollPane scrollPane = new JScrollPane(transactionsContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        
        // Create tabs
        transactionTabs.addTab("📋 All", scrollPane);
        transactionTabs.addTab("💰 Earned", new JScrollPane(createTransactionsPanel(true, false)));
        transactionTabs.addTab("💸 Spent", new JScrollPane(createTransactionsPanel(false, false)));
        transactionTabs.addTab("⏳ Pending", new JScrollPane(createTransactionsPanel(false, true)));
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(transactionTabs, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createTransactionsPanel(boolean earned, boolean pending) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 0, 0, 0));
        
        String[][] transactions = {
            {"+120", "💰 Earned from teaching 'Python Basics' to John D.", "⏰ 2 hours ago", "true", "false"},
            {"-100", "💸 Spent on 'Guitar Lesson' with Maria S.", "📅 Jan 15, 2025", "false", "false"},
            {"+200", "💰 Earned from teaching 'Web Design' to Alex P.", "📅 Jan 12, 2025", "true", "false"},
            {"-150", "⏳ Pending: Upcoming session with Sarah L.", "⏰ Pending", "false", "true"},
            {"+80", "🎁 Referral bonus", "📅 Jan 10, 2025", "true", "false"}
        };
        
        for (String[] trans : transactions) {
            boolean isEarned = Boolean.parseBoolean(trans[3]);
            boolean isPending = Boolean.parseBoolean(trans[4]);
            
            if (pending && !isPending) continue;
            if (!pending && earned && !isEarned) continue;
            if (!pending && !earned && isEarned) continue;
            
            panel.add(createTransactionRow(trans[0], trans[1], trans[2], isEarned, isPending));
            panel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        }
        
        return panel;
    }
    
    private JPanel createTransactionRow(String amount, String description, String timestamp, boolean earned, boolean pending) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        
        // Left side - Icon and description
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        
        JLabel iconLabel = new JLabel(earned ? "✓" : (pending ? "⏰" : "✗"));
        iconLabel.setFont(Theme.FONT_TITLE);
        iconLabel.setForeground(earned ? Theme.SUCCESS_GREEN : (pending ? Theme.WARNING_YELLOW : Theme.CORAL_ACCENT));
        iconLabel.setPreferredSize(new Dimension(40, 40));
        
        JPanel descPanel = new JPanel();
        descPanel.setLayout(new BoxLayout(descPanel, BoxLayout.Y_AXIS));
        descPanel.setOpaque(false);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(Theme.FONT_BODY);
        descLabel.setForeground(Theme.TEXT_PRIMARY);
        
        JLabel timeLabel = new JLabel(timestamp);
        timeLabel.setFont(Theme.FONT_SMALL);
        timeLabel.setForeground(Theme.TEXT_MUTED);
        
        descPanel.add(descLabel);
        descPanel.add(Box.createVerticalStrut(Theme.PADDING_XS));
        descPanel.add(timeLabel);
        
        leftPanel.add(iconLabel, BorderLayout.WEST);
        leftPanel.add(descPanel, BorderLayout.CENTER);
        
        // Right side - Amount
        JLabel amountLabel = new JLabel(amount);
        amountLabel.setFont(Theme.FONT_SUBTITLE);
        amountLabel.setForeground(earned ? Theme.SUCCESS_GREEN : (pending ? Theme.WARNING_YELLOW : Theme.CORAL_ACCENT));
        
        card.add(leftPanel, BorderLayout.WEST);
        card.add(amountLabel, BorderLayout.EAST);
        
        return card;
    }
    
    private JPanel createBottomSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_XL, 0, 0, 0));
        
        // Earning Opportunities
        JPanel earningSection = createEarningOpportunities();
        
        // Points Packages
        JPanel packagesSection = createPointsPackages();
        
        // Redemption section
        JPanel redemptionSection = createRedemptionSection();
        
        panel.add(earningSection);
        panel.add(Box.createVerticalStrut(Theme.PADDING_LARGE));
        panel.add(packagesSection);
        panel.add(Box.createVerticalStrut(Theme.PADDING_LARGE));
        panel.add(redemptionSection);
        
        return panel;
    }
    
    private JPanel createEarningOpportunities() {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("💡 Ways to Earn More Points");
        title.setFont(Theme.FONT_SUBTITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        JPanel opportunitiesPanel = new JPanel(new GridLayout(1, 4, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        opportunitiesPanel.setOpaque(false);
        
        String[][] opportunities = {
            {"👤 Complete your profile", "💰 50 points", "⏱️ 5 min"},
            {"✅ Get verified", "💰 100 points", "⏱️ 2 days"},
            {"🎯 First teaching session", "💰 200 points", "🎁 Bonus"},
            {"👥 Refer a friend", "💰 150 points", "🔄 Each"}
        };
        
        for (String[] opp : opportunities) {
            opportunitiesPanel.add(createOpportunityCard(opp[0], opp[1], opp[2]));
        }
        
        card.add(title, BorderLayout.NORTH);
        card.add(opportunitiesPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createOpportunityCard(String title, String points, String time) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Theme.FONT_BODY);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);
        
        JLabel pointsLabel = new JLabel(points);
        pointsLabel.setFont(Theme.FONT_SUBTITLE);
        pointsLabel.setForeground(Theme.SUCCESS_GREEN);
        
        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(Theme.FONT_SMALL);
        timeLabel.setForeground(Theme.TEXT_MUTED);
        
        // Progress bar placeholder
        JPanel progressBar = createProgressBar(60, Theme.SUCCESS_GREEN);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(pointsLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(timeLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(progressBar);
        
        return card;
    }
    
    private JPanel createPointsPackages() {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("💳 Need More Points?");
        title.setFont(Theme.FONT_SUBTITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        JPanel packagesPanel = new JPanel(new GridLayout(1, 3, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        packagesPanel.setOpaque(false);
        
        String[][] packages = {
            {"🚀 Starter", "💰 500 points", "💵 $5", ""},
            {"⭐ Popular", "💰 1,500 points", "💵 $13.50", "🎁 10% bonus"},
            {"💎 Pro", "💰 5,000 points", "💵 $40", "🎁 20% bonus"}
        };
        
        for (String[] pkg : packages) {
            packagesPanel.add(createPackageCard(pkg[0], pkg[1], pkg[2], pkg[3]));
        }
        
        card.add(title, BorderLayout.NORTH);
        card.add(packagesPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createPackageCard(String name, String points, String price, String bonus) {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        if (!bonus.isEmpty()) {
            card.setSelected(true);
        }
        
        if (!bonus.isEmpty()) {
            JLabel bonusBadge = createBadge(bonus, Theme.CORAL_ACCENT);
            bonusBadge.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(bonusBadge);
            card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        }
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(Theme.FONT_SUBTITLE);
        nameLabel.setForeground(Theme.TEXT_PRIMARY);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel pointsLabel = new JLabel(points);
        pointsLabel.setFont(Theme.FONT_TITLE);
        pointsLabel.setForeground(Theme.NAVY_PRIMARY);
        pointsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(Theme.FONT_BODY);
        priceLabel.setForeground(Theme.TEXT_SECONDARY);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        ModernButton buyBtn = new ModernButton("💳 Buy", ModernButton.ButtonStyle.PRIMARY);
        buyBtn.setBackground(Theme.NAVY_PRIMARY);
        buyBtn.setForeground(Theme.TEXT_LIGHT);
        buyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        card.add(pointsLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_XS));
        card.add(priceLabel);
        card.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        card.add(buyBtn);
        
        return card;
    }
    
    private JPanel createRedemptionSection() {
        ProfessionalCard card = new ProfessionalCard();
        card.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("🎁 Redeem Your Points");
        title.setFont(Theme.FONT_SUBTITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        JLabel minLabel = new JLabel("⚠️ Minimum: Redeem available at 5,000 points");
        minLabel.setFont(Theme.FONT_SMALL);
        minLabel.setForeground(Theme.TEXT_MUTED);
        
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.PADDING_MEDIUM, 0));
        optionsPanel.setOpaque(false);
        
        String[] options = {"💳 Transfer to PayPal", "🛍️ Amazon Gift Card", "❤️ Donate to Charity"};
        for (String option : options) {
            ModernButton btn = new ModernButton(option, ModernButton.ButtonStyle.OUTLINE);
            btn.setForeground(Theme.INFO_BLUE);
            optionsPanel.add(btn);
        }
        
        card.add(title, BorderLayout.NORTH);
        card.add(minLabel, BorderLayout.AFTER_LAST_LINE);
        card.add(optionsPanel, BorderLayout.CENTER);
        
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
                
                g2.setColor(Theme.BG_HOVER);
                g2.fillRoundRect(0, 0, w, h, Theme.RADIUS_SMALL, Theme.RADIUS_SMALL);
                
                int progressWidth = (w * progress) / 100;
                g2.setColor(color);
                g2.fillRoundRect(0, 0, progressWidth, h, Theme.RADIUS_SMALL, Theme.RADIUS_SMALL);
                
                g2.dispose();
            }
        };
        panel.setPreferredSize(new Dimension(0, 6));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6));
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
    
    private void loadData() {
        balanceLabel.setText("2,450");
        usdLabel.setText("≈ $24.50 value");
        
        transactionsContainer.removeAll();
        
        String[][] transactions = {
            {"+120", "Earned from teaching 'Python Basics' to John D.", "2 hours ago", "true", "false"},
            {"-100", "Spent on 'Guitar Lesson' with Maria S.", "Jan 15, 2025", "false", "false"},
            {"+200", "Earned from teaching 'Web Design' to Alex P.", "Jan 12, 2025", "true", "false"},
            {"-150", "Pending: Upcoming session with Sarah L.", "Pending", "false", "true"},
            {"+80", "Referral bonus", "Jan 10, 2025", "true", "false"}
        };
        
        for (String[] trans : transactions) {
            transactionsContainer.add(createTransactionRow(trans[0], trans[1], trans[2], 
                Boolean.parseBoolean(trans[3]), Boolean.parseBoolean(trans[4])));
            transactionsContainer.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        }
        
        transactionsContainer.revalidate();
        transactionsContainer.repaint();
    }
}