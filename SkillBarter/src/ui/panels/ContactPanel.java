package ui.panels;

import ui.components.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ContactPanel extends JPanel {

    public ContactPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.bgDark);
        setBorder(new EmptyBorder(60, 100, 60, 100));

        JLabel heading = new JLabel("Contact Us ✉️");
        heading.setFont(Theme.headingFont);
        heading.setForeground(Theme.neonBlue);
        add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(4, 2, 15, 15));
        form.setOpaque(false);

        JLabel[] labels = {
                new JLabel("Name:"),
                new JLabel("Email:"),
                new JLabel("Message:")
        };
        for (JLabel l : labels) l.setForeground(Theme.textLight);

        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextArea message = new JTextArea(4, 20);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(message);

        JButton send = new JButton("Send Message");
        send.setBackground(Theme.neonBlue);
        send.setForeground(Color.BLACK);
        send.setFocusPainted(false);
        send.setFont(new Font("Segoe UI", Font.BOLD, 14));

        send.addActionListener(e -> {
            String n = name.getText().trim();
            String eMail = email.getText().trim();
            String msg = message.getText().trim();

            if (n.isEmpty() || eMail.isEmpty() || msg.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "✅ Message Sent Successfully!\nWe'll get back to you soon.");
                name.setText("");
                email.setText("");
                message.setText("");
            }
        });

        form.add(labels[0]); form.add(name);
        form.add(labels[1]); form.add(email);
        form.add(labels[2]); form.add(scroll);
        form.add(new JLabel()); form.add(send);

        add(form, BorderLayout.CENTER);
    }
}
