package ui.panels;

import ui.components.*;
import ui.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomePanel extends JPanel {

    public HomePanel(MainWindow window) {
        setLayout(new BorderLayout());
        setBackground(Theme.bgDark);
        setBorder(new EmptyBorder(60, 80, 60, 80));

        JLabel headline = new JLabel("Where Your Talent is the Currency");
        headline.setFont(new Font("Segoe UI", Font.BOLD, 36));
        headline.setForeground(Theme.neonBlue);

        JLabel sub = new JLabel("<html><body style='color:#EAEAEA;font-size:16px;'>Join a global network of learners and teachers who trade skills instead of money.</body></html>");
        sub.setBorder(new EmptyBorder(10, 0, 25, 0));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(Theme.bgDark);
        top.add(headline);
        top.add(sub);

        JPanel features = new JPanel(new GridLayout(1, 3, 25, 0));
        features.setOpaque(false);
        features.add(new FeatureCard("💎", "Earn & Spend Points", "Teach to earn points, spend them to learn new skills."));
        features.add(new FeatureCard("🤝", "AI Skill Matchmaking", "Find ideal skill exchange partners with AI."));
        features.add(new FeatureCard("📅", "Smart Scheduling", "Plan and manage sessions effortlessly.", window::showScheduler));

        add(top, BorderLayout.NORTH);
        add(features, BorderLayout.CENTER);
    }
}
