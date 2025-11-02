package ui.panels;

import ui.components.*;
import ui.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ServicesPanel extends JPanel {

    public ServicesPanel(MainWindow window) {
        setLayout(new BorderLayout());
        setBackground(Theme.bgDark);
        setBorder(new EmptyBorder(60, 80, 60, 80));

        JLabel headline = new JLabel("Our Services ⚙️");
        headline.setFont(new Font("Segoe UI", Font.BOLD, 32));
        headline.setForeground(Theme.neonBlue);

        JLabel sub = new JLabel("<html><body style='color:#EAEAEA;font-size:15px;'>Explore the tools and features that make SkillBarter a next-gen learning platform.</body></html>");
        sub.setBorder(new EmptyBorder(10, 0, 30, 0));

        JPanel top = new JPanel();
        top.setBackground(Theme.bgDark);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(headline);
        top.add(sub);

        JPanel grid = new JPanel(new GridLayout(2, 3, 25, 25));
        grid.setOpaque(false);

        grid.add(new FeatureCard("🤖", "AI Matchmaking", "Intelligent partner recommendations based on your goals."));
        grid.add(new FeatureCard("🔐", "Secure Payments", "Points and transactions are fully encrypted."));
        grid.add(new FeatureCard("📅", "Session Scheduler", "Easily book, reschedule, and manage lessons.", window::showScheduler));
        grid.add(new FeatureCard("💬", "Real-time Chat", "Instant messaging and translation support."));
        grid.add(new FeatureCard("⭐", "Feedback System", "Rate sessions to maintain quality learning."));
        grid.add(new FeatureCard("📊", "Analytics Dashboard", "Track growth, progress, and learning history."));

        add(top, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);
    }
}
