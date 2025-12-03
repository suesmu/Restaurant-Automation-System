package gui;

import backend.InventoryDAO;

import javax.swing.*;
import java.awt.*;

public class InventoryEditScreen extends JFrame {
  public InventoryEditScreen() {
    setTitle("Update Inventory");
    setSize(400, 220);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridBagLayout());
    mainPanel.setBackground(new Color(250, 250, 250));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 15, 10, 15);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel nameLabel = new JLabel("Ingredient Name:");
    nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    JTextField nameField = new JTextField(15);

    JLabel qtyLabel = new JLabel("Quantity:");
    qtyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    JTextField qtyField = new JTextField(15);

    JButton updateButton = new JButton("Update");
    updateButton.setBackground(new Color(60, 130, 200));
    updateButton.setForeground(Color.WHITE);
    updateButton.setFocusPainted(false);
    updateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
    updateButton.setPreferredSize(new Dimension(160, 35));

    gbc.gridx = 0;
    gbc.gridy = 0;
    mainPanel.add(nameLabel, gbc);

    gbc.gridx = 1;
    mainPanel.add(nameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    mainPanel.add(qtyLabel, gbc);

    gbc.gridx = 1;
    mainPanel.add(qtyField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    mainPanel.add(updateButton, gbc);

    updateButton.addActionListener(e -> {
      String name = nameField.getText().trim();
      try {
        int qty = Integer.parseInt(qtyField.getText().trim());
        InventoryDAO.updateOrInsert(name, qty);
        JOptionPane.showMessageDialog(this, "Inventory updated!");
        nameField.setText("");
        qtyField.setText("");
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid quantity!");
      }
    });

    add(mainPanel);
    setVisible(true);
  }
}