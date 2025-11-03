package ui.components;

import ui.core.UserManager;
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
            if (userManager.login(username.getText(), new String(password.getPassword()))) {
                JOptionPane.showMessageDialog(this, "✅ Welcome, " + username.getText());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid credentials");
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
            User registeredUser = userManager.register(username.getText(), new String(password.getPassword()), email.getText());
            if (registeredUser != null) {
                JOptionPane.showMessageDialog(this, "🎉 Registered successfully! You can now log in.");
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Username already exists.");
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
