package ui;

import ui.components.*;
import ui.core.*;
import ui.panels.*;
import javax.swing.*;
import java.awt.*;

/**
 * Main application window
 */
public class MainWindow extends JFrame {
    public final UserManager userManager = new UserManager();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private JLabel pointsLabel;
    
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
        nav.setOpaque(false);
        nav.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER_NEON),
            BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_XL, 
                Theme.PADDING_MEDIUM, Theme.PADDING_XL)
        ));
        
        // Logo
        JLabel logo = new JLabel("SkillBarter");
        logo.setFont(Theme.FONT_TITLE);
        logo.setForeground(Theme.NEON_CYAN);
        
        // Nav buttons
        JPanel navButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.PADDING_MEDIUM, 0));
        navButtons.setOpaque(false);
        
        String[] items = {"Dashboard", "Marketplace", "Sessions", "Wallet", "Profile"};
        for (String item : items) {
            NeonButton btn = new NeonButton(item);
            btn.setNeonColor(Theme.NEON_CYAN);
            btn.addActionListener(e -> {
                cardLayout.show(cards, item.toUpperCase());
                cards.revalidate();
                cards.repaint();
            });
            navButtons.add(btn);
        }
        
        // Right side
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, Theme.PADDING_MEDIUM, 0));
        rightPanel.setOpaque(false);
        
        pointsLabel = new JLabel("Points: 0");
        pointsLabel.setFont(Theme.FONT_BODY);
        pointsLabel.setForeground(Theme.NEON_CYAN);
        
        rightPanel.add(pointsLabel);
        
        nav.add(logo, BorderLayout.WEST);
        nav.add(navButtons, BorderLayout.CENTER);
        nav.add(rightPanel, BorderLayout.EAST);
        
        return nav;
    }
    
    private JPanel createPlaceholderPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(true);
        panel.setBackground(Theme.BG_PRIMARY);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_XL, Theme.PADDING_XL, 
            Theme.PADDING_XL, Theme.PADDING_XL));
        
        JLabel label = new JLabel(title);
        label.setFont(Theme.FONT_LARGE);
        label.setForeground(Theme.NEON_CYAN);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(label, BorderLayout.CENTER);
        
        return panel;
    }
    
    public void updatePoints(int points) {
        if (pointsLabel != null) {
            pointsLabel.setText("Points: " + points);
        }
    }
}

