package ui.components;

import ui.core.UserManager;
import ui.core.APIClient;
import ui.models.User;
import javax.swing.*;
import java.awt.*;

public class LegacyUserAuthDialog extends JDialog {
    private final UserManager userManager;

    public LegacyUserAuthDialog(JFrame parent, UserManager userManager) {
        super(parent, "👤 User Management", true);
        this.userManager = userManager;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(20, 20, 25));

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Login", createLoginPanel());
        tabs.addTab("Register", createRegisterPanel());

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(new Color(25, 25, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> {
            String usernameText = username.getText().trim();
            String passwordText = new String(password.getPassword());
            
            if (usernameText.isEmpty() || passwordText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Please fill in all fields", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                User user = APIClient.login(usernameText, passwordText);
                if (user != null) {
                    userManager.setCurrentUser(user);
                    JOptionPane.showMessageDialog(this, "✅ Welcome, " + user.getUsername());
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

        panel.add(new JLabel("Username:"));
        panel.add(username);
        panel.add(new JLabel("Password:"));
        panel.add(password);
        panel.add(new JLabel());
        panel.add(loginBtn);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBackground(new Color(25, 25, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JTextField email = new JTextField();

        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(e -> {
            String usernameText = username.getText().trim();
            String passwordText = new String(password.getPassword());
            String emailText = email.getText().trim();
            
            if (usernameText.isEmpty() || passwordText.isEmpty() || emailText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Please fill in all fields", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (passwordText.length() < 4) {
                JOptionPane.showMessageDialog(this, "❌ Password must be at least 4 characters", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                User registeredUser = APIClient.register(usernameText, emailText, passwordText);
                if (registeredUser != null) {
                    userManager.setCurrentUser(registeredUser);
                    JOptionPane.showMessageDialog(this, "🎉 Registered successfully! You can now log in.", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ Registration failed", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel("Username:"));
        panel.add(username);
        panel.add(new JLabel("Password:"));
        panel.add(password);
        panel.add(new JLabel("Email:"));
        panel.add(email);
        panel.add(new JLabel());
        panel.add(registerBtn);

        return panel;
    }
}
