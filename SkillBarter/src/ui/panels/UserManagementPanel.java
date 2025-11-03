package ui.panels;

import ui.core.Theme;
import ui.components.ModernButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserManagementPanel extends JPanel {

    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    private final Color successGreen = new Color(100, 255, 150);
    private final Color errorRed = new Color(255, 100, 120);

    public UserManagementPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        // Glassmorphism card
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                int w = getWidth();
                int h = getHeight();
                int arc = 30;
                
                // Card background with shadow
                g2.setColor(new Color(0, 0, 0, 8));
                g2.fillRoundRect(2, 2, w - 4, h - 4, arc, arc);
                
                // Card background
                g2.setColor(Theme.bgCard);
                g2.fillRoundRect(0, 0, w, h, arc, arc);
                
                // Border
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2.setStroke(new BasicStroke(1.5f));
                g2.setColor(Theme.borderLight);
                g2.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(450, 450));
        card.setBorder(new EmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // Title
        JLabel title = new JLabel("User Registration", SwingConstants.CENTER);
        title.setFont(Theme.headingS);
        title.setForeground(Theme.textPrimary);
        
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(title, gbc);

        // Username
        gbc.gridy++;
        gbc.gridwidth = 2;
        card.add(createLabeledField("Username", usernameField = new JTextField()), gbc);

        // Email
        gbc.gridy++;
        card.add(createLabeledField("Email", emailField = new JTextField()), gbc);

        // Password
        gbc.gridy++;
        card.add(createLabeledField("Password", passwordField = new JPasswordField()), gbc);

        // Register button
        gbc.gridy++;
        ModernButton registerBtn = new ModernButton("Register", ModernButton.ButtonStyle.PRIMARY);
        registerBtn.addActionListener(e -> registerUser());
        card.add(registerBtn, gbc);

        // Status label
        gbc.gridy++;
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(Theme.bodySmall);
        statusLabel.setForeground(Theme.textMuted);
        card.add(statusLabel, gbc);

        add(card);
    }

    private JPanel createLabeledField(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(Theme.button);
        label.setForeground(Theme.primary);
        panel.add(label, BorderLayout.NORTH);

        field.setFont(Theme.body);
        field.setBackground(Theme.bgCard);
        field.setForeground(Theme.textPrimary);
        field.setCaretColor(Theme.primary);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.borderMedium, 1),
                new EmptyBorder(10, 12, 10, 12)
        ));

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Theme.primary, 2),
                    new EmptyBorder(9, 11, 9, 11)
                ));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Theme.borderMedium, 1),
                    new EmptyBorder(10, 12, 10, 12)
                ));
            }
        });

        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void registerUser() {
        try {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showStatus("⚠️ Please fill in all fields", errorRed);
                return;
            }

            String json = String.format(
                    "{\"username\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}",
                    username, email, password
            );

            URL url = new URL("http://localhost:8081/api/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                showStatus("✅ User registered successfully", successGreen);
                clearFields();
            } else {
                showStatus("❌ Server error: " + responseCode, errorRed);
            }

            conn.disconnect();
        } catch (Exception e) {
            showStatus("🚫 Unable to connect to backend", errorRed);
        }
    }

    private void showStatus(String msg, Color color) {
        statusLabel.setText(msg);
        statusLabel.setForeground(color);
    }

    private void clearFields() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }
}
