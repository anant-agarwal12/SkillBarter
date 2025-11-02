package ui.panels;

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

    // 🎨 Minimal Clean Palette
    private final Color bgDark = new Color(18, 18, 20);
    private final Color cardColor = new Color(28, 28, 32);
    private final Color inputBG = new Color(38, 38, 42);
    private final Color borderColor = new Color(60, 60, 65);
    private final Color textColor = new Color(230, 230, 235);
    private final Color placeholder = new Color(160, 160, 165);
    private final Color accentBlue = new Color(0, 170, 255);
    private final Color successGreen = new Color(40, 200, 120);
    private final Color errorRed = new Color(220, 80, 80);

    public UserManagementPanel() {
        setLayout(new GridBagLayout());
        setBackground(bgDark);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(cardColor);
        card.setPreferredSize(new Dimension(400, 380));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1, true),
                new EmptyBorder(40, 40, 40, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // ✳️ Title
        JLabel title = new JLabel("User Registration", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        title.setForeground(textColor);
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(title, gbc);

        // Username
        gbc.gridy++;
        card.add(createLabeledField("Username", usernameField = new JTextField()), gbc);

        // Email
        gbc.gridy++;
        card.add(createLabeledField("Email", emailField = new JTextField()), gbc);

        // Password
        gbc.gridy++;
        card.add(createLabeledField("Password", passwordField = new JPasswordField()), gbc);

        // Register button
        gbc.gridy++;
        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        registerBtn.setBackground(accentBlue);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        registerBtn.addActionListener(e -> registerUser());
        card.add(registerBtn, gbc);

        // Status label
        gbc.gridy++;
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusLabel.setForeground(placeholder);
        card.add(statusLabel, gbc);

        add(card);
    }

    private JPanel createLabeledField(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setBackground(cardColor);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(placeholder);
        panel.add(label, BorderLayout.NORTH);

        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBackground(inputBG);
        field.setForeground(textColor);
        field.setCaretColor(accentBlue);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                new EmptyBorder(8, 10, 8, 10)
        ));

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createLineBorder(accentBlue, 1));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createLineBorder(borderColor, 1));
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

            URL url = new URL("http://localhost:8080/api/users");
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
