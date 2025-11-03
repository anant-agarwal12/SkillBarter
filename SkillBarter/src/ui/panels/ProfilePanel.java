package ui.panels;

import ui.components.*;
import ui.core.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Profile panel - User profile and settings
 */
public class ProfilePanel extends JPanel {
    private JLabel usernameLabel, emailLabel, pointsLabel;
    
    public ProfilePanel() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Theme.BG_PRIMARY);
        setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_LARGE, 
            Theme.PADDING_MEDIUM, Theme.PADDING_LARGE));
        
        initUI();
        loadProfile();
    }
    
    private void initUI() {
        // Header
        JLabel title = new JLabel("Profile");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.NEON_CYAN);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_LARGE, 0));
        
        // Main content with side-by-side layout
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, Theme.PADDING_LARGE, 0));
        contentPanel.setOpaque(false);
        
        // Left - Profile info
        NeonCard profileCard = new NeonCard();
        profileCard.setNeonColor(Theme.NEON_CYAN);
        profileCard.setLayout(new BoxLayout(profileCard, BoxLayout.Y_AXIS));
        profileCard.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, Theme.PADDING_LARGE, 
            Theme.PADDING_LARGE, Theme.PADDING_LARGE));
        
        // Avatar placeholder
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int size = Math.min(getWidth(), getHeight());
                g2.setPaint(Theme.getPrimaryGradient(size, size));
                g2.fillOval(0, 0, size, size);
                
                g2.setFont(Theme.FONT_LARGE);
                g2.setColor(Theme.BLACK);
                FontMetrics fm = g2.getFontMetrics();
                String initials = "JD";
                int x = (size - fm.stringWidth(initials)) / 2;
                int y = (size + fm.getAscent()) / 2;
                g2.drawString(initials, x, y);
                
                g2.dispose();
            }
        };
        avatarPanel.setPreferredSize(new Dimension(100, 100));
        avatarPanel.setMaximumSize(new Dimension(100, 100));
        avatarPanel.setOpaque(false);
        
        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(Theme.FONT_SUBTITLE);
        usernameLabel.setForeground(Theme.TEXT_PRIMARY);
        
        emailLabel = new JLabel("email@example.com");
        emailLabel.setFont(Theme.FONT_BODY);
        emailLabel.setForeground(Theme.TEXT_SECONDARY);
        
        pointsLabel = new JLabel("Points: 0");
        pointsLabel.setFont(Theme.FONT_BODY);
        pointsLabel.setForeground(Theme.NEON_YELLOW);
        
        NeonButton editBtn = new NeonButton("Edit Profile");
        editBtn.setNeonColor(Theme.NEON_PURPLE);
        editBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        profileCard.add(avatarPanel);
        profileCard.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        profileCard.add(usernameLabel);
        profileCard.add(Box.createVerticalStrut(Theme.PADDING_SMALL / 2));
        profileCard.add(emailLabel);
        profileCard.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        profileCard.add(pointsLabel);
        profileCard.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        profileCard.add(editBtn);
        
        // Right - My Skills
        NeonCard skillsCard = new NeonCard();
        skillsCard.setNeonColor(Theme.NEON_PINK);
        skillsCard.setLayout(new BorderLayout());
        skillsCard.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, Theme.PADDING_LARGE, 
            Theme.PADDING_LARGE, Theme.PADDING_LARGE));
        
        JLabel skillsTitle = new JLabel("My Skills");
        skillsTitle.setFont(Theme.FONT_SUBTITLE);
        skillsTitle.setForeground(Theme.TEXT_PRIMARY);
        skillsTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        JPanel skillsList = new JPanel();
        skillsList.setLayout(new BoxLayout(skillsList, BoxLayout.Y_AXIS));
        skillsList.setOpaque(false);
        
        String[] skills = {"Java Programming", "Web Design", "Digital Marketing"};
        for (String skill : skills) {
            JLabel skillLabel = new JLabel("• " + skill);
            skillLabel.setFont(Theme.FONT_BODY);
            skillLabel.setForeground(Theme.TEXT_SECONDARY);
            skillLabel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_SMALL / 2, 0, 
                Theme.PADDING_SMALL / 2, 0));
            skillsList.add(skillLabel);
        }
        
        NeonButton addSkillBtn = new NeonButton("+ Add Skill");
        addSkillBtn.setNeonColor(Theme.NEON_GREEN);
        addSkillBtn.addActionListener(e -> addSkill());
        
        JScrollPane skillsScroll = new JScrollPane(skillsList);
        skillsScroll.setOpaque(false);
        skillsScroll.getViewport().setOpaque(false);
        skillsScroll.setBorder(null);
        
        JPanel skillsContent = new JPanel(new BorderLayout());
        skillsContent.setOpaque(false);
        skillsContent.add(skillsTitle, BorderLayout.NORTH);
        skillsContent.add(skillsScroll, BorderLayout.CENTER);
        skillsContent.add(addSkillBtn, BorderLayout.SOUTH);
        
        skillsCard.add(skillsContent, BorderLayout.CENTER);
        
        contentPanel.add(profileCard);
        contentPanel.add(skillsCard);
        
        add(title, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void loadProfile() {
        usernameLabel.setText("john_doe");
        emailLabel.setText("john.doe@example.com");
        pointsLabel.setText("Points: 1,250");
    }
    
    private void addSkill() {
        String skill = JOptionPane.showInputDialog(this, "Enter skill name:", "Add Skill", 
            JOptionPane.QUESTION_MESSAGE);
        if (skill != null && !skill.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Skill added: " + skill);
        }
    }
}
