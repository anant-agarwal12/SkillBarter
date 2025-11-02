package ui;
import ui.core.UserManager;
import ui.panels.*;
import ui.components.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;

public class MainWindow extends JFrame {
    public final CardLayout cardLayout = new CardLayout();
    public final JPanel cards = new JPanel(cardLayout);
    public static final UserManager userManager = new UserManager();

    // 🎨 Neon Palette
    private final Color bgDark = Theme.bgDark;
    private final Color cardDark = Theme.cardDark;
    private final Color textLight = Theme.textLight;
    private final Color neonBlue = Theme.neonBlue;
    private final Color neonPurple = Theme.neonPurple;

    public MainWindow() {
        setTitle("⚡ SkillBarter — Neon Enhanced Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1150, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgDark);

        add(createTopNav(), BorderLayout.NORTH);
        cards.setBackground(bgDark);

        // each panel will be added as a separate file soon
        cards.add(new HomePanel(this), "HOME");
        cards.add(new AboutPanel(), "ABOUT");
        cards.add(new ServicesPanel(this), "SERVICES");
        cards.add(new ModulesPanel(this), "MODULES");
        cards.add(new ContactPanel(), "CONTACT");
        cards.add(new UserManagementPanel(), "USERMANAGEMENT");


        add(cards, BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);
    }

    private JComponent createTopNav() {
        JPanel nav = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, neonPurple, getWidth(), 0, neonBlue);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        nav.setBorder(new EmptyBorder(12, 25, 12, 25));

        JLabel brand = new JLabel("⚡ SkillBarter");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 28));
        brand.setForeground(Color.WHITE);
        nav.add(brand, BorderLayout.WEST);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 0));
        buttons.setOpaque(false);
        String[] names = {"Home", "About", "Services", "Modules", "UserManagement", "Contact"};
        for (String n : names) {
            NeonButton btn = new NeonButton(n);
            btn.addActionListener(e -> cardLayout.show(cards, n.toUpperCase()));
            buttons.add(btn);
        }
        nav.add(buttons, BorderLayout.EAST);
        return nav;
    }

    public void showScheduler() {
        new CalendarDialog(this).setVisible(true);
    }

    private JPanel createFooter() {
        JPanel f = new JPanel(new BorderLayout());
        f.setBackground(new Color(20, 20, 25));
        f.setBorder(new EmptyBorder(10, 20, 10, 20));
        JLabel left = new JLabel("© 2025 SkillBarter | Neon Enhanced Edition");
        left.setForeground(new Color(160, 160, 160));
        JLabel right = new JLabel("Built with 💙 in Java Swing");
        right.setForeground(neonBlue);
        f.add(left, BorderLayout.WEST);
        f.add(right, BorderLayout.EAST);
        return f;
    }
}
