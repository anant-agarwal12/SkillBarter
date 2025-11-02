package ui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FeatureCard extends JPanel {

    public FeatureCard(String icon, String title, String desc) {
        this(icon, title, desc, null);
    }

    public FeatureCard(String icon, String title, String desc, Runnable onClick) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Theme.cardDark);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(40, 40, 45), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titleLabel.setForeground(Theme.neonBlue);

        JLabel descLabel = new JLabel("<html><body style='width:250px;color:#CCCCCC;font-size:13px;'>" + desc + "</body></html>");

        add(iconLabel);
        add(Box.createVerticalStrut(10));
        add(titleLabel);
        add(Box.createVerticalStrut(5));
        add(descLabel);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Theme.neonBlue, 2, true));
                setBackground(new Color(30, 30, 35));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(new Color(40, 40, 45), 1, true));
                setBackground(Theme.cardDark);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClick != null) onClick.run();
            }
        });
    }
}
