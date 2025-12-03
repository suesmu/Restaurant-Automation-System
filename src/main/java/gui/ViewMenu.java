package gui;
import backend.*;
import backend.MenuItem;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewMenu extends JFrame {
    public ViewMenu() {
        setTitle("Restaurant Menu");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        Color bg = new Color(245, 245, 245);
        JPanel cardPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        cardPanel.setBackground(bg);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        List<MenuItem> menuItems = MenuDAO.fetchMenuItems();

        for (MenuItem item : menuItems) {
            JPanel card = new JPanel();
            card.setLayout(new BorderLayout(10, 10));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));

            // 🔹 Görseli yükle
            String imageName = item.getName().toLowerCase().replaceAll(" ", "_") + ".jpg";
            ImageIcon icon = new ImageIcon("images/" + imageName);
            Image img = icon.getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            card.add(imageLabel, BorderLayout.WEST);

            // 🔹 Metin ve butonları dikey hizala
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(Color.WHITE);

            JLabel nameLabel = new JLabel(item.getName());
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
            nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel priceLabel = new JLabel("$" + item.getPrice());
            priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            priceLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

            JButton addButton = new JButton("Add to Order");
            addButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            addButton.setBackground(new Color(60, 130, 200));
            addButton.setForeground(Color.WHITE);
            addButton.setFocusPainted(false);

            addButton.addActionListener(e -> {
                OrderManager manager = new OrderManager();
                Inventory inventory = new Inventory();
                new OrderScreen(manager, inventory);
            });

            infoPanel.add(nameLabel);
            infoPanel.add(priceLabel);
            infoPanel.add(addButton);

            card.add(infoPanel, BorderLayout.CENTER);
            cardPanel.add(card);
        }

        add(cardPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}