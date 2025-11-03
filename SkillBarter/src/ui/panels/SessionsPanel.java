package ui.panels;

import ui.components.*;
import ui.core.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Sessions panel - Manage learning sessions
 */
public class SessionsPanel extends JPanel {
    private JPanel activeContainer;
    private JPanel pastContainer;
    
    public SessionsPanel() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Theme.BG_PRIMARY);
        setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_LARGE, 
            Theme.PADDING_MEDIUM, Theme.PADDING_LARGE));
        
        initUI();
        loadSessions();
    }
    
    private void initUI() {
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        JLabel title = new JLabel("My Sessions");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.NEON_CYAN);
        
        NeonButton newSessionBtn = new NeonButton("New Session");
        newSessionBtn.setNeonColor(Theme.NEON_GREEN);
        newSessionBtn.addActionListener(e -> createNewSession());
        
        header.add(title, BorderLayout.WEST);
        header.add(newSessionBtn, BorderLayout.EAST);
        
        // Tabbed pane for active/past sessions
        JTabbedPane tabs = new JTabbedPane();
        tabs.setOpaque(false);
        tabs.setBackground(Theme.BG_PRIMARY);
        tabs.setForeground(Theme.TEXT_PRIMARY);
        tabs.setFont(Theme.FONT_BODY);
        
        // Active sessions
        activeContainer = new JPanel();
        activeContainer.setLayout(new BoxLayout(activeContainer, BoxLayout.Y_AXIS));
        activeContainer.setOpaque(false);
        activeContainer.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, 0, 0, 0));
        
        JScrollPane activeScroll = new JScrollPane(activeContainer);
        activeScroll.setOpaque(false);
        activeScroll.getViewport().setOpaque(false);
        activeScroll.setBorder(null);
        
        // Past sessions
        pastContainer = new JPanel();
        pastContainer.setLayout(new BoxLayout(pastContainer, BoxLayout.Y_AXIS));
        pastContainer.setOpaque(false);
        pastContainer.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, 0, 0, 0));
        
        JScrollPane pastScroll = new JScrollPane(pastContainer);
        pastScroll.setOpaque(false);
        pastScroll.getViewport().setOpaque(false);
        pastScroll.setBorder(null);
        
        tabs.addTab("Active", activeScroll);
        tabs.addTab("Past Sessions", pastScroll);
        
        add(header, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }
    
    private void loadSessions() {
        activeContainer.removeAll();
        pastContainer.removeAll();
        
        // Sample active sessions
        String[][] active = {
            {"Java Programming", "John Doe", "2024-01-20 14:00", "Online"},
            {"Digital Marketing", "Jane Smith", "2024-01-22 10:00", "In-Person"}
        };
        
        for (String[] session : active) {
            activeContainer.add(createSessionCard(session[0], session[1], session[2], session[3], true));
            activeContainer.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        }
        
        // Sample past sessions
        String[][] past = {
            {"UI/UX Design", "Mike Johnson", "2024-01-15 15:00", "Completed"},
            {"Data Science", "Sarah Lee", "2024-01-10 11:00", "Completed"}
        };
        
        for (String[] session : past) {
            pastContainer.add(createSessionCard(session[0], session[1], session[2], session[3], false));
            pastContainer.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        }
        
        activeContainer.revalidate();
        pastContainer.revalidate();
    }
    
    private JPanel createSessionCard(String skill, String teacher, String datetime, String status, boolean active) {
        NeonCard card = new NeonCard();
        card.setNeonColor(active ? Theme.NEON_CYAN : Theme.NEON_PURPLE);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM, 
            Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        
        // Left side - skill info
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        
        JLabel skillLabel = new JLabel(skill);
        skillLabel.setFont(Theme.FONT_SUBTITLE);
        skillLabel.setForeground(Theme.TEXT_PRIMARY);
        
        JLabel teacherLabel = new JLabel("with " + teacher);
        teacherLabel.setFont(Theme.FONT_BODY);
        teacherLabel.setForeground(Theme.TEXT_SECONDARY);
        
        JLabel dateLabel = new JLabel(datetime);
        dateLabel.setFont(Theme.FONT_BODY);
        dateLabel.setForeground(Theme.TEXT_MUTED);
        
        leftPanel.add(skillLabel);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL / 2));
        leftPanel.add(teacherLabel);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL / 2));
        leftPanel.add(dateLabel);
        
        // Right side - status/actions
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);
        
        if (active) {
            NeonButton joinBtn = new NeonButton("Join");
            joinBtn.setNeonColor(Theme.NEON_GREEN);
            joinBtn.setPreferredSize(new Dimension(80, 30));
            joinBtn.addActionListener(e -> joinSession(skill));
            
            NeonButton cancelBtn = new NeonButton("Cancel");
            cancelBtn.setNeonColor(Theme.NEON_PINK);
            cancelBtn.setPreferredSize(new Dimension(80, 30));
            cancelBtn.addActionListener(e -> cancelSession(skill));
            
            rightPanel.add(joinBtn);
            rightPanel.add(Box.createHorizontalStrut(Theme.PADDING_SMALL));
            rightPanel.add(cancelBtn);
        } else {
            JLabel statusLabel = new JLabel(status);
            statusLabel.setFont(Theme.FONT_BODY);
            statusLabel.setForeground(Theme.TEXT_MUTED);
            rightPanel.add(statusLabel);
        }
        
        card.add(leftPanel, BorderLayout.WEST);
        card.add(rightPanel, BorderLayout.EAST);
        
        return card;
    }
    
    private void createNewSession() {
        JOptionPane.showMessageDialog(this, "Create new session feature coming soon!");
    }
    
    private void joinSession(String skill) {
        JOptionPane.showMessageDialog(this, "Joining session: " + skill);
    }
    
    private void cancelSession(String skill) {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Cancel session: " + skill + "?", "Confirm", 
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Session cancelled");
            loadSessions();
        }
    }
}
