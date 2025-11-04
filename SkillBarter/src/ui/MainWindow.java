package ui;

import ui.components.*;
import ui.core.*;
import ui.panels.*;
import javax.swing.*;
import java.awt.*;

/**
 * Main application window with professional navigation
 */
public class MainWindow extends JFrame {
    public final UserManager userManager = new UserManager();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private JLabel pointsLabel;
    private JLabel notificationBadge;
    private JLabel messageBadge;

    public MainWindow() {
        setTitle("SkillBarter - Skill Exchange Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        // Navigation
        add(createNavigation(), BorderLayout.NORTH);
        
        // Content panels
        cards.setOpaque(false);
        cards.setBackground(Theme.BG_PRIMARY);
        
        cards.add(new DashboardPanel(userManager), "DASHBOARD");
        cards.add(new MarketplacePanel(), "MARKETPLACE");
        cards.add(new SessionsPanel(), "SESSIONS");
        cards.add(new WalletPanel(), "WALLET");
        cards.add(new ProfilePanel(), "PROFILE");

        add(cards, BorderLayout.CENTER);
        
        // Show dashboard
        cardLayout.show(cards, "DASHBOARD");
    }
    
    private JPanel createNavigation() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setOpaque(true);
        nav.setBackground(Theme.BG_SECONDARY);
        nav.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER_LIGHT),
            BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_XL, 
                Theme.PADDING_MEDIUM, Theme.PADDING_XL)
        ));
        
        // Left side - Logo
        JLabel logo = new JLabel("SkillBarter");
        logo.setFont(Theme.FONT_TITLE);
        logo.setForeground(Theme.NAVY_PRIMARY);
        
        // Center - Main navigation
        JPanel navButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.PADDING_MEDIUM, 0));
        navButtons.setOpaque(false);
        
        String[] items = {"📊 Dashboard", "📅 Sessions", "🛍️ Marketplace", "💰 Wallet", "👤 Profile"};
        String[] cardNames = {"DASHBOARD", "SESSIONS", "MARKETPLACE", "WALLET", "PROFILE"};
        for (int i = 0; i < items.length; i++) {
            final int index = i;
            ModernButton btn = new ModernButton(items[i], ModernButton.ButtonStyle.GHOST);
            btn.setForeground(Theme.TEXT_PRIMARY);
            btn.setFont(Theme.FONT_BODY);
            btn.addActionListener(e -> {
                cardLayout.show(cards, cardNames[index]);
                cards.revalidate();
                cards.repaint();
            });
            navButtons.add(btn);
        }
        
        // Right side - Search, Notifications, Messages, Profile dropdown
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, Theme.PADDING_SMALL, 0));
        rightPanel.setOpaque(false);
        
        // Search icon button
        ModernButton searchBtn = new ModernButton("🔍", ModernButton.ButtonStyle.GHOST);
        searchBtn.setForeground(Theme.TEXT_SECONDARY);
        searchBtn.setFont(Theme.getEmojiFont(18)); // Use Unicode-compatible font for emojis
        searchBtn.setToolTipText("Search");
        searchBtn.setPreferredSize(new Dimension(40, 40));
        searchBtn.addActionListener(e -> showSearchDialog());
        
        // Notifications button with badge
        JPanel notificationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        notificationPanel.setOpaque(false);
        
        ModernButton notificationBtn = new ModernButton("🔔", ModernButton.ButtonStyle.GHOST);
        notificationBtn.setForeground(Theme.TEXT_SECONDARY);
        notificationBtn.setFont(Theme.getEmojiFont(18)); // Use Unicode-compatible font for emojis
        notificationBtn.setToolTipText("Notifications");
        notificationBtn.setPreferredSize(new Dimension(40, 40));
        notificationBtn.addActionListener(e -> showNotifications());
        
        notificationBadge = new JLabel("3");
        notificationBadge.setFont(Theme.FONT_SMALL);
        notificationBadge.setForeground(Theme.TEXT_LIGHT);
        notificationBadge.setBackground(Theme.CORAL_ACCENT);
        notificationBadge.setOpaque(true);
        notificationBadge.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        notificationBadge.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BG_SECONDARY, 2),
            BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));
        notificationBadge.setPreferredSize(new Dimension(20, 20));
        notificationBadge.setLocation(25, 0);
        
        notificationPanel.add(notificationBtn);
        notificationPanel.add(notificationBadge);
        notificationPanel.setLayout(null);
        notificationBtn.setBounds(0, 0, 40, 40);
        notificationBadge.setBounds(25, 0, 20, 20);
        notificationPanel.setPreferredSize(new Dimension(40, 40));
        
        // Messages button with badge
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        messagePanel.setOpaque(false);
        
        ModernButton messageBtn = new ModernButton("💬", ModernButton.ButtonStyle.GHOST);
        messageBtn.setForeground(Theme.TEXT_SECONDARY);
        messageBtn.setFont(Theme.getEmojiFont(18)); // Use Unicode-compatible font for emojis
        messageBtn.setToolTipText("Messages");
        messageBtn.setPreferredSize(new Dimension(40, 40));
        messageBtn.addActionListener(e -> showMessages());
        
        messageBadge = new JLabel("5");
        messageBadge.setFont(Theme.FONT_SMALL);
        messageBadge.setForeground(Theme.TEXT_LIGHT);
        messageBadge.setBackground(Theme.INFO_BLUE);
        messageBadge.setOpaque(true);
        messageBadge.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BG_SECONDARY, 2),
            BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));
        messageBadge.setPreferredSize(new Dimension(20, 20));
        messageBadge.setLocation(25, 0);
        
        messagePanel.add(messageBtn);
        messagePanel.add(messageBadge);
        messagePanel.setLayout(null);
        messageBtn.setBounds(0, 0, 40, 40);
        messageBadge.setBounds(25, 0, 20, 20);
        messagePanel.setPreferredSize(new Dimension(40, 40));
        
        // Points display
        pointsLabel = new JLabel("Points: 0");
        pointsLabel.setFont(Theme.FONT_BODY);
        pointsLabel.setForeground(Theme.NAVY_PRIMARY);
        pointsLabel.setBorder(BorderFactory.createEmptyBorder(0, Theme.PADDING_MEDIUM, 0, Theme.PADDING_MEDIUM));
        
        // Profile dropdown
        JButton profileBtn = new JButton("👤");
        profileBtn.setFont(Theme.FONT_TITLE);
        profileBtn.setForeground(Theme.TEXT_SECONDARY);
        profileBtn.setBackground(Theme.BG_SECONDARY);
        profileBtn.setOpaque(true);
        profileBtn.setBorderPainted(false);
        profileBtn.setPreferredSize(new Dimension(40, 40));
        profileBtn.addActionListener(e -> showProfileMenu(profileBtn));
        
        rightPanel.add(searchBtn);
        rightPanel.add(notificationPanel);
        rightPanel.add(messagePanel);
        rightPanel.add(pointsLabel);
        rightPanel.add(profileBtn);
        
        nav.add(logo, BorderLayout.WEST);
        nav.add(navButtons, BorderLayout.CENTER);
        nav.add(rightPanel, BorderLayout.EAST);
        
        return nav;
    }
    
    private void showSearchDialog() {
        JDialog searchDialog = new JDialog(this, "Search", true);
        searchDialog.setSize(600, 400);
        searchDialog.setLocationRelativeTo(this);
        searchDialog.setLayout(new BorderLayout());
        
        JTextField searchField = new JTextField();
        searchField.setFont(Theme.FONT_BODY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM, 
                Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM)
        ));
        searchField.setPreferredSize(new Dimension(0, 50));
        
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setOpaque(false);
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 
            Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        searchDialog.add(searchField, BorderLayout.NORTH);
        searchDialog.add(scrollPane, BorderLayout.CENTER);
        searchDialog.setVisible(true);
    }
    
    private void showNotifications() {
        JDialog notificationDialog = new JDialog(this, "Notifications", true);
        notificationDialog.setSize(400, 500);
        notificationDialog.setLocationRelativeTo(this);
        notificationDialog.setLayout(new BorderLayout());
        
        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));
        notificationPanel.setOpaque(false);
        notificationPanel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 
            Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        
        String[] notifications = {
            "New session request from John",
            "Your session starts in 30 minutes",
            "You earned 150 points!",
            "New match found: 95% compatibility"
        };
        
        for (String notification : notifications) {
            ProfessionalCard card = new ProfessionalCard();
            card.setLayout(new BorderLayout());
            
            JLabel label = new JLabel(notification);
            label.setFont(Theme.FONT_BODY);
            label.setForeground(Theme.TEXT_PRIMARY);
            
            card.add(label, BorderLayout.WEST);
            notificationPanel.add(card);
            notificationPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        }
        
        JScrollPane scrollPane = new JScrollPane(notificationPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        notificationDialog.add(scrollPane, BorderLayout.CENTER);
        notificationDialog.setVisible(true);
    }
    
    private void showMessages() {
        JDialog messageDialog = new JDialog(this, "Messages", true);
        messageDialog.setSize(600, 500);
        messageDialog.setLocationRelativeTo(this);
        messageDialog.setLayout(new BorderLayout());
        
        // Sidebar with conversation list
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setOpaque(false);
        sidebar.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 
            Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        sidebar.setPreferredSize(new Dimension(200, 0));
        
        String[] conversations = {"John Doe", "Sarah Lee", "Mike Johnson", "Alex Chen"};
        for (String name : conversations) {
            ProfessionalCard card = new ProfessionalCard();
            card.setLayout(new BorderLayout());
            
            JPanel avatar = createAvatarPanel(name.charAt(0), 40);
            JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(Theme.FONT_BODY);
            nameLabel.setForeground(Theme.TEXT_PRIMARY);
            
            card.add(avatar, BorderLayout.WEST);
            card.add(nameLabel, BorderLayout.CENTER);
            
            sidebar.add(card);
            sidebar.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        }
        
        JScrollPane sidebarScroll = new JScrollPane(sidebar);
        sidebarScroll.setOpaque(false);
        sidebarScroll.getViewport().setOpaque(false);
        
        // Main chat area
        JPanel chatArea = new JPanel(new BorderLayout());
        chatArea.setOpaque(false);
        chatArea.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, 
            Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        
        JTextArea messagesArea = new JTextArea();
        messagesArea.setFont(Theme.FONT_BODY);
        messagesArea.setBackground(Theme.BG_CARD);
        messagesArea.setEditable(false);
        messagesArea.setText("Messages will appear here...");
        
        JTextField inputField = new JTextField();
        inputField.setFont(Theme.FONT_BODY);
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, Theme.PADDING_MEDIUM, 
                Theme.PADDING_SMALL, Theme.PADDING_MEDIUM)
        ));
        
        ModernButton sendBtn = new ModernButton("Send", ModernButton.ButtonStyle.PRIMARY);
        sendBtn.setBackground(Theme.NAVY_PRIMARY);
        sendBtn.setForeground(Theme.TEXT_LIGHT);
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setOpaque(false);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);
        
        chatArea.add(new JScrollPane(messagesArea), BorderLayout.CENTER);
        chatArea.add(inputPanel, BorderLayout.SOUTH);
        
        messageDialog.add(sidebarScroll, BorderLayout.WEST);
        messageDialog.add(chatArea, BorderLayout.CENTER);
        messageDialog.setVisible(true);
    }
    
    private void showProfileMenu(JButton button) {
        JPopupMenu menu = new JPopupMenu();
        
        JMenuItem profileItem = new JMenuItem("View Profile");
        profileItem.addActionListener(e -> {
            cardLayout.show(cards, "PROFILE");
            cards.revalidate();
            cards.repaint();
        });
        
        JMenuItem settingsItem = new JMenuItem("Settings");
        settingsItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Settings coming soon!"));
        
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to logout?", "Logout", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                userManager.logout();
                AuthDialog authDialog = new AuthDialog(this, userManager);
                authDialog.setVisible(true);
                if (!userManager.isLoggedIn()) {
                    System.exit(0);
                } else {
                    // Refresh UI
                    refreshUI();
                }
            }
        });
        
        menu.add(profileItem);
        menu.add(settingsItem);
        menu.addSeparator();
        menu.add(logoutItem);
        
        menu.show(button, 0, button.getHeight());
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
                
                g2.setFont(Theme.FONT_BODY);
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
    
    public void updatePoints(int points) {
        if (pointsLabel != null) {
            pointsLabel.setText("Points: " + points);
        }
    }
    
    public void refreshUI() {
        // Refresh user data
        userManager.refreshUser();
        
        // Update points display
        if (userManager.getCurrentUser() != null && userManager.getCurrentUser().getPoints() != null) {
            updatePoints(userManager.getCurrentUser().getPoints());
        }
        
        // Refresh panels
        cards.removeAll();
        cards.add(new DashboardPanel(userManager), "DASHBOARD");
        cards.add(new MarketplacePanel(), "MARKETPLACE");
        cards.add(new SessionsPanel(), "SESSIONS");
        cards.add(new WalletPanel(), "WALLET");
        cards.add(new ProfilePanel(), "PROFILE");
        
        cardLayout.show(cards, "DASHBOARD");
        revalidate();
        repaint();
    }
}