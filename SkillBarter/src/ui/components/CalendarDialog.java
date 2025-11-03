package ui.components;

import ui.core.Theme;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.YearMonth;

public class CalendarDialog extends JDialog {

    public CalendarDialog(JFrame parent) {
        super(parent, "📅 Smart Scheduler", true);
        setSize(480, 520);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(0, 0));
        
        // Custom glassmorphism background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                int w = getWidth();
                int h = getHeight();
                
                // Background
                g2.setColor(Theme.bgPrimary);
                g2.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setOpaque(false);
        setContentPane(mainPanel);

        // Glassmorphism header
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Header background
                g2.setColor(Theme.bgSecondary);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                // Bottom border
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                g2.setStroke(new BasicStroke(1.5f));
                g2.setColor(Theme.borderLight);
                g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        };
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JComboBox<String> monthBox = createStyledComboBox(String.class);
        String[] months = new String[12];
        for (int i = 0; i < 12; i++) {
            String name = java.time.Month.of(i + 1).name();
            months[i] = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            monthBox.addItem(months[i]);
        }
        monthBox.setSelectedIndex(LocalDate.now().getMonthValue() - 1);

        JComboBox<Integer> yearBox = createStyledComboBox(Integer.class);
        int currentYear = LocalDate.now().getYear();
        for (int y = currentYear - 5; y <= currentYear + 5; y++) yearBox.addItem(y);
        yearBox.setSelectedItem(currentYear);

        topPanel.add(monthBox);
        topPanel.add(yearBox);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        final JPanel[] calendarPanel = {createCalendarPanel(YearMonth.now())};
        mainPanel.add(calendarPanel[0], BorderLayout.CENTER);

        Runnable refreshCalendar = () -> {
            YearMonth ym = YearMonth.of((int) yearBox.getSelectedItem(), monthBox.getSelectedIndex() + 1);
            mainPanel.remove(calendarPanel[0]);
            calendarPanel[0] = createCalendarPanel(ym);
            mainPanel.add(calendarPanel[0], BorderLayout.CENTER);
            revalidate();
            repaint();
        };

        monthBox.addActionListener(e -> refreshCalendar.run());
        yearBox.addActionListener(e -> refreshCalendar.run());
    }
    
    private <T> JComboBox<T> createStyledComboBox(Class<T> type) {
        JComboBox<T> box = new JComboBox<>();
        box.setBackground(Theme.bgCard);
        box.setForeground(Theme.primary);
        box.setFont(Theme.button);
        box.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.borderMedium, 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        box.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setForeground(Theme.primary);
                setBackground(Theme.bgCard);
                setHorizontalAlignment(CENTER);
                return this;
            }
        });
        return box;
    }

    private JPanel createCalendarPanel(YearMonth month) {
        JPanel panel = new JPanel(new GridLayout(0, 7, 8, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Calendar panel background
                g2.setColor(new Color(0, 0, 0, 5));
                g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, Theme.radiusL, Theme.radiusL);
                
                g2.setColor(Theme.bgCard);
                g2.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, Theme.radiusL, Theme.radiusL);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 25, 25, 25));

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String d : days) {
            JLabel dayLabel = new JLabel(d, SwingConstants.CENTER);
            dayLabel.setForeground(Theme.secondary);
            dayLabel.setFont(Theme.body);
            dayLabel.setOpaque(false);
            panel.add(dayLabel);
        }

        LocalDate firstDay = month.atDay(1);
        int offset = firstDay.getDayOfWeek().getValue() % 7;
        int daysInMonth = month.lengthOfMonth();
        int today = LocalDate.now().getDayOfMonth();
        boolean isThisMonth = LocalDate.now().getMonth() == month.getMonth()
                && LocalDate.now().getYear() == month.getYear();

        for (int i = 0; i < offset; i++) {
            JLabel empty = new JLabel("");
            empty.setOpaque(false);
            panel.add(empty);
        }

        for (int day = 1; day <= daysInMonth; day++) {
            JLabel dayLabel = new DayLabel(String.valueOf(day), isThisMonth && day == today);
            final int dayNum = day;
            
            dayLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    ((DayLabel) dayLabel).setHovered(true);
                    dayLabel.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    ((DayLabel) dayLabel).setHovered(false);
                    dayLabel.repaint();
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(panel,
                            "📅 You selected: " + dayNum + " " + month.getMonth() + " " + month.getYear(),
                            "Date Selected",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
            panel.add(dayLabel);
        }
        return panel;
    }
    
    private class DayLabel extends JLabel {
        private boolean isToday;
        private boolean hovered;
        
        public DayLabel(String text, boolean isToday) {
            super(text, SwingConstants.CENTER);
            this.isToday = isToday;
            this.hovered = false;
            setOpaque(false);
            setForeground(Theme.textPrimary);
            setFont(Theme.body);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        
        public void setHovered(boolean hovered) {
            this.hovered = hovered;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int w = getWidth();
            int h = getHeight();
            
            if (isToday || hovered) {
                // Background with hover effect
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, hovered ? 0.9f : 1.0f));
                if (isToday) {
                    g2.setPaint(Theme.getPrimaryGradient(w, h));
                } else {
                    g2.setColor(hovered ? Theme.primaryLight : Theme.bgHover);
                }
                g2.fillRoundRect(0, 0, w, h, Theme.radiusS, Theme.radiusS);
                
                setForeground(isToday ? Theme.textInverse : Theme.textPrimary);
            } else {
                // Default background
                g2.setColor(Theme.bgCard);
                g2.fillRoundRect(0, 0, w, h, Theme.radiusS, Theme.radiusS);
                
                setForeground(Theme.textPrimary);
            }
            
            super.paintComponent(g);
        }
    }
}
