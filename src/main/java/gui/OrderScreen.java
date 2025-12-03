package gui;

import backend.*;
import backend.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class OrderScreen extends JFrame {
    private final JTextField tableField = new JTextField(15);
    private final JTextField qtyField = new JTextField(15);
    private final JTextArea outputArea = new JTextArea();
    private final List<String> orders = new ArrayList<>();
    private final OrderManager orderManager;
    private final Inventory inventory;

    public OrderScreen(OrderManager orderManager, Inventory inventory) {
        this.orderManager = orderManager;
        this.inventory = inventory;

        setTitle("📝 Create Order");
        setSize(480, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color background = new Color(250, 250, 250);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(background);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel tableLabel = new JLabel("Table No:");
        tableLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(tableLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(tableField, gbc);

        JLabel itemLabel = new JLabel("Item:");
        itemLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(itemLabel, gbc);

        JComboBox<String> itemBox = new JComboBox<>();
        for (MenuItem menuItem : MenuDAO.fetchMenuItems()) {
            itemBox.addItem(menuItem.getName());
        }
        gbc.gridx = 1;
        formPanel.add(itemBox, gbc);

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(qtyLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(qtyField, gbc);

        JButton submit = new JButton("Add Order");
        submit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submit.setBackground(new Color(60, 130, 200));
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submit.setPreferredSize(new Dimension(140, 40));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(submit, gbc);

        add(formPanel, BorderLayout.CENTER);

        submit.addActionListener(e -> {
            try {
                String item = itemBox.getSelectedItem().toString();
                int qty = Integer.parseInt(qtyField.getText().trim());
                int table = Integer.parseInt(tableField.getText().trim());

                Map<String, Integer> ingredients = RecipeDAO.getIngredientsForDish(item);
                boolean allAvailable = true;
                for (String ingredient : ingredients.keySet()) {
                    int totalQty = ingredients.get(ingredient) * qty;
                    if (!InventoryDAO.isAvailable(ingredient, totalQty)) {
                        allAvailable = false;
                        break;
                    }
                }

                if (allAvailable) {
                    double price = 0.0;
                    for (MenuItem menuItem : MenuDAO.fetchMenuItems()) {
                        if (menuItem.getName().equals(item)) {
                            price = menuItem.getPrice();
                            break;
                        }
                    }

                    OrderItem orderItem = new OrderItem(item, qty, price);
                    Order order = new Order(table, Collections.singletonList(orderItem));
                    orderManager.addOrder(order);
                    OrderDAO.saveOrder(order);

                    for (String ingredient : ingredients.keySet()) {
                        int totalQty = ingredients.get(ingredient) * qty;
                        InventoryDAO.deduct(ingredient, totalQty);
                    }

                    JOptionPane.showMessageDialog(this, "✔ Order placed:\n" + orderItem.toString());
                    tableField.setText("");
                    qtyField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "❗ Not enough stock for: " + item);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "⚠ Invalid input!");
            }
        });

        setVisible(true);
    }
}