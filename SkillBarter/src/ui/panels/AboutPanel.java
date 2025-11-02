package ui.panels;

import ui.components.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AboutPanel extends JPanel {

    public AboutPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.bgDark);
        setBorder(new EmptyBorder(60, 100, 60, 100));

        JLabel title = new JLabel("About SkillBarter 🌐");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(Theme.neonBlue);
        add(title, BorderLayout.NORTH);

        JLabel about = new JLabel("""
        <html><body style='font-size:17px;color:#E0E0E0;line-height:1.6;'>
        <b>SkillBarter</b> is a futuristic platform where <b>knowledge becomes a shared currency</b>.<br><br>
        Our mission is to connect passionate learners and teachers worldwide —
        enabling them to exchange skills, time, and creativity in a <b>cash-free ecosystem</b>.<br><br>
        <b>Our Vision:</b> Empower every individual to grow beyond borders through collaboration.<br><br>
        <b>Core Values:</b><br>
        🌟 Inclusivity — Learning for everyone<br>
        ⚙️ Innovation — Powered by AI-driven matchmaking<br>
        💙 Community — Built on trust, feedback, and mutual respect
        </body></html>
        """);
        about.setBorder(new EmptyBorder(20, 0, 0, 0));
        add(about, BorderLayout.CENTER);
    }
}
