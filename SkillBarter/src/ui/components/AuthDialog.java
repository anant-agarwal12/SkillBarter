package ui.components;

import ui.core.*;
import ui.models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Modern authentication dialog with login and register tabs
 */
public class AuthDialog extends JDialog {
    private UserManager userManager;
    private User authenticatedUser = null;
    private boolean isAdmin = false;
    
    public AuthDialog(JFrame parent, UserManager userManager) {
        super(parent, "🔐 SkillBarter - Login", true);
        this.userManager = userManager;
        
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG_PRIMARY);
        setResizable(false);
        
        initUI();
    }
    
    private void initUI() {
        // Header
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_XL, Theme.PADDING_XL, 
            Theme.PADDING_MEDIUM, Theme.PADDING_XL));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        
        JLabel title = new JLabel("SkillBarter");
        title.setFont(Theme.FONT_LARGE);
        title.setForeground(Theme.NAVY_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Skill Exchange Platform");
        subtitle.setFont(Theme.FONT_BODY);
        subtitle.setForeground(Theme.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        header.add(title);
        header.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        header.add(subtitle);
        
        // Tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.setOpaque(true);
        tabs.setBackground(Theme.BG_PRIMARY);
        tabs.setForeground(Theme.TEXT_PRIMARY);
        tabs.setFont(Theme.FONT_BODY);
        
        tabs.addTab("👤 User Login", createLoginPanel(false));
        tabs.addTab("📝 User Register", createRegisterPanel(false));
        tabs.addTab("🔧 Admin Login", createLoginPanel(true));
        tabs.addTab("⚙️ Admin Register", createRegisterPanel(true));
        
        add(header, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }
    
    private JPanel createLoginPanel(boolean admin) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_XL, Theme.PADDING_XL, 
            Theme.PADDING_XL, Theme.PADDING_XL));
        
        JLabel usernameLabel = new JLabel("Username or Email:");
        usernameLabel.setFont(Theme.FONT_BODY);
        usernameLabel.setForeground(Theme.TEXT_PRIMARY);
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField usernameField = new JTextField();
        usernameField.setFont(Theme.FONT_BODY);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, Theme.PADDING_MEDIUM, 
                Theme.PADDING_SMALL, Theme.PADDING_MEDIUM)
        ));
        
        panel.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(Theme.PADDING_XS));
        panel.add(usernameField);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(Theme.FONT_BODY);
        passwordLabel.setForeground(Theme.TEXT_PRIMARY);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(Theme.FONT_BODY);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, Theme.PADDING_MEDIUM, 
                Theme.PADDING_SMALL, Theme.PADDING_MEDIUM)
        ));
        
        panel.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(Theme.PADDING_XS));
        panel.add(passwordField);
        
        ModernButton loginBtn = new ModernButton(admin ? "🔧 Admin Login" : "🔐 Login", 
            ModernButton.ButtonStyle.PRIMARY);
        loginBtn.setBackground(admin ? Theme.WARNING_YELLOW : Theme.NAVY_PRIMARY);
        loginBtn.setForeground(Theme.TEXT_LIGHT);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        loginBtn.setPreferredSize(new Dimension(0, 45));
        
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Please fill in all fields", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                User user = admin ? APIClient.adminLogin(username, password) : 
                    APIClient.login(username, password);
                if (user != null) {
                    authenticatedUser = user;
                    userManager.setCurrentUser(user);
                    isAdmin = admin;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Invalid credentials", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panel.add(Box.createVerticalStrut(Theme.PADDING_LARGE));
        panel.add(loginBtn);
        
        // Allow Enter key to trigger login
        passwordField.addActionListener(e -> loginBtn.doClick());
        
        return panel;
    }
    
    private JPanel createRegisterPanel(boolean admin) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_XL, Theme.PADDING_XL, 
            Theme.PADDING_XL, Theme.PADDING_XL));
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(Theme.FONT_BODY);
        usernameLabel.setForeground(Theme.TEXT_PRIMARY);
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField usernameField = new JTextField();
        usernameField.setFont(Theme.FONT_BODY);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, Theme.PADDING_MEDIUM, 
                Theme.PADDING_SMALL, Theme.PADDING_MEDIUM)
        ));
        
        panel.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(Theme.PADDING_XS));
        panel.add(usernameField);
        
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(Theme.FONT_BODY);
        emailLabel.setForeground(Theme.TEXT_PRIMARY);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField emailField = new JTextField();
        emailField.setFont(Theme.FONT_BODY);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, Theme.PADDING_MEDIUM, 
                Theme.PADDING_SMALL, Theme.PADDING_MEDIUM)
        ));
        
        panel.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        panel.add(emailLabel);
        panel.add(Box.createVerticalStrut(Theme.PADDING_XS));
        panel.add(emailField);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(Theme.FONT_BODY);
        passwordLabel.setForeground(Theme.TEXT_PRIMARY);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(Theme.FONT_BODY);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Theme.PADDING_SMALL, Theme.PADDING_MEDIUM, 
                Theme.PADDING_SMALL, Theme.PADDING_MEDIUM)
        ));
        
        panel.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(Theme.PADDING_XS));
        panel.add(passwordField);
        
        ModernButton registerBtn = new ModernButton(admin ? "⚙️ Admin Register" : "📝 Register", 
            ModernButton.ButtonStyle.PRIMARY);
        registerBtn.setBackground(admin ? Theme.WARNING_YELLOW : Theme.NAVY_PRIMARY);
        registerBtn.setForeground(Theme.TEXT_LIGHT);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        registerBtn.setPreferredSize(new Dimension(0, 45));
        
        registerBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Please fill in all fields", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (password.length() < 4) {
                JOptionPane.showMessageDialog(this, "❌ Password must be at least 4 characters", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                User user = admin ? APIClient.adminRegister(username, email, password) : 
                    APIClient.register(username, email, password);
                if (user != null) {
                    authenticatedUser = user;
                    userManager.setCurrentUser(user);
                    isAdmin = admin;
                    JOptionPane.showMessageDialog(this, "✅ Registration successful!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Registration failed", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panel.add(Box.createVerticalStrut(Theme.PADDING_LARGE));
        panel.add(registerBtn);
        
        // Allow Enter key to trigger register
        passwordField.addActionListener(e -> registerBtn.doClick());
        
        return panel;
    }
    
    public User getAuthenticatedUser() {
        return authenticatedUser;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }
}

