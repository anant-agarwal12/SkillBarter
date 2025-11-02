package ui.components;

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
        setSize(420, 450);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(15, 15, 18));
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(new Color(20, 20, 25));
        topPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JComboBox<String> monthBox = new JComboBox<>();
        String[] months = new String[12];
        for (int i = 0; i < 12; i++) {
            String name = java.time.Month.of(i + 1).name();
            months[i] = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            monthBox.addItem(months[i]);
        }
        monthBox.setSelectedIndex(LocalDate.now().getMonthValue() - 1);

        JComboBox<Integer> yearBox = new JComboBox<>();
        int currentYear = LocalDate.now().getYear();
        for (int y = currentYear - 5; y <= currentYear + 5; y++) yearBox.addItem(y);
        yearBox.setSelectedItem(currentYear);

        for (JComboBox<?> box : new JComboBox[]{monthBox, yearBox}) {
            box.setBackground(new Color(30, 30, 35));
            box.setForeground(Theme.neonBlue);
            box.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }

        topPanel.add(monthBox);
        topPanel.add(yearBox);
        add(topPanel, BorderLayout.NORTH);

        final JPanel[] calendarPanel = {createCalendarPanel(YearMonth.now())};
        add(calendarPanel[0], BorderLayout.CENTER);

        Runnable refreshCalendar = () -> {
            YearMonth ym = YearMonth.of((int) yearBox.getSelectedItem(), monthBox.getSelectedIndex() + 1);
            getContentPane().remove(calendarPanel[0]);
            calendarPanel[0] = createCalendarPanel(ym);
            add(calendarPanel[0], BorderLayout.CENTER);
            revalidate();
            repaint();
        };

        monthBox.addActionListener(e -> refreshCalendar.run());
        yearBox.addActionListener(e -> refreshCalendar.run());
    }

    private JPanel createCalendarPanel(YearMonth month) {
        JPanel panel = new JPanel(new GridLayout(0, 7, 5, 5));
        panel.setBackground(new Color(20, 20, 25));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String d : days) {
            JLabel dayLabel = new JLabel(d, SwingConstants.CENTER);
            dayLabel.setForeground(Theme.neonPurple);
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            panel.add(dayLabel);
        }

        LocalDate firstDay = month.atDay(1);
        int offset = firstDay.getDayOfWeek().getValue() % 7;
        int daysInMonth = month.lengthOfMonth();
        int today = LocalDate.now().getDayOfMonth();
        boolean isThisMonth = LocalDate.now().getMonth() == month.getMonth()
                && LocalDate.now().getYear() == month.getYear();

        for (int i = 0; i < offset; i++) panel.add(new JLabel(""));

        for (int day = 1; day <= daysInMonth; day++) {
            JLabel dayLabel = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            dayLabel.setOpaque(true);
            dayLabel.setForeground(Color.WHITE);
            dayLabel.setBackground(new Color(30, 30, 35));
            dayLabel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 55)));

            if (isThisMonth && day == today) {
                dayLabel.setBackground(Theme.neonBlue);
                dayLabel.setForeground(Color.BLACK);
            }

            dayLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    dayLabel.setBackground(Theme.neonPurple);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (isThisMonth && Integer.parseInt(dayLabel.getText()) == today) {
                        dayLabel.setBackground(Theme.neonBlue);
                        dayLabel.setForeground(Color.BLACK);
                    } else {
                        dayLabel.setBackground(new Color(30, 30, 35));
                        dayLabel.setForeground(Color.WHITE);
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(panel,
                            "📅 You selected: " + dayLabel.getText() + " " + month.getMonth() + " " + month.getYear(),
                            "Date Selected",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
            panel.add(dayLabel);
        }
        return panel;
    }
}
