package ui.panels;

import ui.components.*;
import ui.core.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Wallet panel - Points and transactions
 */
public class WalletPanel extends JPanel {
    private JLabel balanceLabel;
    private JPanel transactionsContainer;
    
    public WalletPanel() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Theme.BG_PRIMARY);
        setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_LARGE, 
            Theme.PADDING_MEDIUM, Theme.PADDING_LARGE));
        
        initUI();
        loadData();
    }
    
    private void initUI() {
        // Header
        JLabel title = new JLabel("Wallet");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.NEON_CYAN);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        // Balance card
        NeonCard balanceCard = new NeonCard();
        balanceCard.setNeonColor(Theme.NEON_YELLOW);
        balanceCard.setLayout(new BorderLayout());
        balanceCard.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, Theme.PADDING_LARGE, 
            Theme.PADDING_LARGE, Theme.PADDING_LARGE));
        
        JLabel balanceTitle = new JLabel("Total Points");
        balanceTitle.setFont(Theme.FONT_BODY);
        balanceTitle.setForeground(Theme.TEXT_SECONDARY);
        
        balanceLabel = new JLabel("0");
        balanceLabel.setFont(Theme.FONT_LARGE);
        balanceLabel.setForeground(Theme.NEON_YELLOW);
        
        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(new BoxLayout(balancePanel, BoxLayout.Y_AXIS));
        balancePanel.setOpaque(false);
        balancePanel.add(balanceTitle);
        balancePanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        balancePanel.add(balanceLabel);
        
        balanceCard.add(balancePanel, BorderLayout.CENTER);
        balanceCard.setPreferredSize(new Dimension(0, 120));
        
        // Bottom section - Transactions
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_LARGE, 0, 0, 0));
        
        JLabel transactionsTitle = new JLabel("Transaction History");
        transactionsTitle.setFont(Theme.FONT_SUBTITLE);
        transactionsTitle.setForeground(Theme.TEXT_PRIMARY);
        transactionsTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PADDING_MEDIUM, 0));
        
        // Transactions list
        transactionsContainer = new JPanel();
        transactionsContainer.setLayout(new BoxLayout(transactionsContainer, BoxLayout.Y_AXIS));
        transactionsContainer.setOpaque(false);
        
        JScrollPane scrollPane = new JScrollPane(transactionsContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        
        bottomPanel.add(transactionsTitle, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Main container
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setOpaque(false);
        mainContainer.add(balanceCard, BorderLayout.NORTH);
        mainContainer.add(bottomPanel, BorderLayout.CENTER);
        
        add(title, BorderLayout.NORTH);
        add(mainContainer, BorderLayout.CENTER);
    }
    
    private void loadData() {
        // Load balance
        balanceLabel.setText("1,250");
        
        // Load transactions
        transactionsContainer.removeAll();
        
        String[][] transactions = {
            {"+150", "Completed session", "2024-01-18", "true"},
            {"-100", "Enrolled in skill", "2024-01-15", "false"},
            {"+200", "Completed session", "2024-01-12", "true"},
            {"+80", "Referral bonus", "2024-01-10", "true"},
            {"-120", "Enrolled in skill", "2024-01-08", "false"}
        };
        
        for (String[] trans : transactions) {
            transactionsContainer.add(createTransactionCard(trans[0], trans[1], trans[2], 
                Boolean.parseBoolean(trans[3])));
            transactionsContainer.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        }
        
        transactionsContainer.revalidate();
        transactionsContainer.repaint();
    }
    
    private JPanel createTransactionCard(String amount, String description, String date, boolean positive) {
        NeonCard card = new NeonCard();
        card.setNeonColor(positive ? Theme.NEON_GREEN : Theme.NEON_PINK);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM, 
            Theme.PADDING_MEDIUM, Theme.PADDING_MEDIUM));
        
        // Left side
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(Theme.FONT_BODY);
        descLabel.setForeground(Theme.TEXT_PRIMARY);
        
        JLabel dateLabel = new JLabel(date);
        dateLabel.setFont(Theme.FONT_BODY);
        dateLabel.setForeground(Theme.TEXT_MUTED);
        
        leftPanel.add(descLabel);
        leftPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL / 2));
        leftPanel.add(dateLabel);
        
        // Right side - amount
        JLabel amountLabel = new JLabel(amount);
        amountLabel.setFont(Theme.FONT_SUBTITLE);
        amountLabel.setForeground(positive ? Theme.NEON_GREEN : Theme.NEON_PINK);
        
        card.add(leftPanel, BorderLayout.WEST);
        card.add(amountLabel, BorderLayout.EAST);
        
        return card;
    }
}
