package ui.panels;

import ui.components.*;
import ui.MainWindow;
import ui.core.UserManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ModulesPanel extends JPanel {

    private final MainWindow window;

    public ModulesPanel(MainWindow window) {
        this.window = window;
        setLayout(new BorderLayout());
        setBackground(Theme.bgDark);
        setBorder(new EmptyBorder(60, 80, 60, 80));

        JLabel headline = new JLabel("Core Modules 🧩");
        headline.setFont(new Font("Segoe UI", Font.BOLD, 32));
        headline.setForeground(Theme.neonBlue);

        JLabel sub = new JLabel("<html><body style='color:#EAEAEA;font-size:15px;'>Each module is built for scalability, AI integration, and seamless collaboration.</body></html>");
        sub.setBorder(new EmptyBorder(10, 0, 30, 0));

        JPanel top = new JPanel();
        top.setBackground(Theme.bgDark);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(headline);
        top.add(sub);

        JPanel grid = new JPanel(new GridLayout(2, 3, 25, 25));
        grid.setOpaque(false);

        // ✅ User Management
        grid.add(makeFeatureCard("🏗️", "User Management",
                "Handles profiles, authentication, and access control.", () -> {
                    window.cardLayout.show(window.cards, "USERMANAGEMENT");
                }));



        // ✅ Other modules
        grid.add(makeFeatureCard("🤖", "AI Recommender", "Suggests ideal skill exchange matches."));
        grid.add(makeFeatureCard("💰", "Points Engine", "Tracks earning, spending, and balance updates."));
        grid.add(makeFeatureCard("📅", "Scheduler", "Manages time zones, availability, and bookings.", window::showScheduler));
        grid.add(makeFeatureCard("💬", "Messaging System", "Encrypted chat with notifications."));
        grid.add(makeFeatureCard("🛡️", "Admin Dashboard", "Moderation, analytics, and health monitoring."));

        add(top, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);
    }

    // 🔹 Reusable Feature Card Creator (with click action)
    private JPanel makeFeatureCard(String icon, String title, String desc, Runnable onClick) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.cardDark);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(40, 40, 45), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel i = new JLabel(icon);
        i.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 17));
        t.setForeground(Theme.neonBlue);
        JLabel d = new JLabel("<html><body style='width:250px;color:#CCCCCC;font-size:13px;'>" + desc + "</body></html>");

        card.add(i);
        card.add(Box.createVerticalStrut(10));
        card.add(t);
        card.add(Box.createVerticalStrut(5));
        card.add(d);

        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(Theme.neonBlue, 2, true));
                card.setBackground(new Color(30, 30, 35));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 45), 1, true));
                card.setBackground(Theme.cardDark);
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (onClick != null) onClick.run();
            }
        });

        return card;
    }

    // 🔹 Overload for static (non-clickable) cards
    private JPanel makeFeatureCard(String icon, String title, String desc) {
        return makeFeatureCard(icon, title, desc, null);
    }
}
