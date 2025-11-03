package ui.panels;

import ui.components.*;
import ui.core.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Marketplace panel - Browse and search skills
 */
public class MarketplacePanel extends JPanel {
    private JTextField searchField;
    private JPanel skillsContainer;
    
    public MarketplacePanel() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Theme.BG_PRIMARY);
        setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_LARGE, 
            Theme.PADDING_MEDIUM, Theme.PADDING_LARGE));
        
        initUI();
        loadSkills();
    }
    
    private void initUI() {
        // Header with search
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        JLabel title = new JLabel("Skill Marketplace");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.NEON_CYAN);
        
        // Search bar
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchField = new JTextField();
        searchField.setFont(Theme.FONT_BODY);
        searchField.setForeground(Theme.TEXT_PRIMARY);
        searchField.setBackground(Theme.BG_CARD);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_NEON, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, Theme.PADDING_MEDIUM, 
                Theme.PADDING_SMALL, Theme.PADDING_MEDIUM)
        ));
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.addActionListener(e -> filterSkills());
        
        NeonButton searchBtn = new NeonButton("Search");
        searchBtn.setNeonColor(Theme.NEON_CYAN);
        searchBtn.setPreferredSize(new Dimension(100, 30));
        searchBtn.addActionListener(e -> filterSkills());
        
        JPanel searchContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        searchContainer.setOpaque(false);
        searchContainer.add(searchField);
        searchContainer.add(Box.createHorizontalStrut(Theme.PADDING_SMALL));
        searchContainer.add(searchBtn);
        
        header.add(title, BorderLayout.WEST);
        header.add(searchContainer, BorderLayout.EAST);
        
        // Skills grid
        skillsContainer = new JPanel(new GridLayout(0, 3, Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        skillsContainer.setOpaque(false);
        
        JScrollPane scrollPane = new JScrollPane(skillsContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadSkills() {
        skillsContainer.removeAll();
        
        // Sample skills
        String[][] skills = {
            {"Java Programming", "John Doe", "4.8", "150", "Online", "Programming"},
            {"Digital Marketing", "Jane Smith", "4.6", "120", "Online", "Marketing"},
            {"UI/UX Design", "Mike Johnson", "4.9", "180", "In-Person", "Design"},
            {"Data Science", "Sarah Lee", "4.7", "200", "Online", "Programming"},
            {"Photography", "Alex Brown", "4.5", "100", "In-Person", "Creative"},
            {"Cooking Basics", "Emma Wilson", "4.8", "80", "In-Person", "Lifestyle"}
        };
        
        for (String[] skill : skills) {
            SkillCard card = new SkillCard(skill[0], skill[1], Double.parseDouble(skill[2]), 
                Integer.parseInt(skill[3]), skill[4], () -> {
                    JOptionPane.showMessageDialog(this, "Selected: " + skill[0]);
                });
            skillsContainer.add(card);
        }
        
        skillsContainer.revalidate();
        skillsContainer.repaint();
    }
    
    private void filterSkills() {
        String query = searchField.getText().toLowerCase();
        if (query.isEmpty()) {
            loadSkills();
            return;
        }
        
        skillsContainer.removeAll();
        
        String[][] skills = {
            {"Java Programming", "John Doe", "4.8", "150", "Online", "Programming"},
            {"Digital Marketing", "Jane Smith", "4.6", "120", "Online", "Marketing"},
            {"UI/UX Design", "Mike Johnson", "4.9", "180", "In-Person", "Design"},
            {"Data Science", "Sarah Lee", "4.7", "200", "Online", "Programming"},
            {"Photography", "Alex Brown", "4.5", "100", "In-Person", "Creative"},
            {"Cooking Basics", "Emma Wilson", "4.8", "80", "In-Person", "Lifestyle"}
        };
        
        for (String[] skill : skills) {
            if (skill[0].toLowerCase().contains(query) || 
                skill[1].toLowerCase().contains(query) || 
                skill[5].toLowerCase().contains(query)) {
                SkillCard card = new SkillCard(skill[0], skill[1], Double.parseDouble(skill[2]), 
                    Integer.parseInt(skill[3]), skill[4], () -> {
                        JOptionPane.showMessageDialog(this, "Selected: " + skill[0]);
                    });
                skillsContainer.add(card);
            }
        }
        
        skillsContainer.revalidate();
        skillsContainer.repaint();
    }
}
