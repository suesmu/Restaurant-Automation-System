package gui;

import backend.*;

import javax.swing.*;
import java.awt.*;

public class MainMenuScreen extends JFrame {
    private final OrderManager orderManager = new OrderManager();
    private final Inventory inventory = new Inventory();
    private final User user;

    public MainMenuScreen(User user) {
        this.user = user;

        setTitle("Main Menu - " + user.getUsername() + " (" + user.getRole() + ")");
        setSize(450, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(248, 248, 248));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(welcomeLabel);

        panel.add(createStyledButton("Create Order", () -> new OrderScreen(orderManager, inventory)));
        panel.add(createStyledButton("View Menu", ViewMenu::new));

        if (user.getRole().equals("admin")) {
            panel.add(createStyledButton("Inventory", InventoryScreen::new));
            panel.add(createStyledButton("Reports", () -> new ReportScreen(orderManager)));
            panel.add(createStyledButton("Order History", OrderHistoryScreen::new));
            panel.add(createStyledButton("Add Menu Item", MenuAddScreen::new));
            panel.add(createStyledButton("Edit Inventory", InventoryEditScreen::new));
        }

        add(panel);
        setVisible(true);
    }

    private JPanel createStyledButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(60, 130, 200));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.addActionListener(e -> action.run());
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.add(Box.createVerticalStrut(8));
        wrapper.add(btn);
        wrapper.add(Box.createVerticalStrut(8));

        return wrapper;
    }
}