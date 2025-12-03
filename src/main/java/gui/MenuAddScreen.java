package gui;

import backend.*;

import javax.swing.*;
import java.awt.*;

public class MenuAddScreen extends JFrame {
  public MenuAddScreen() {
    setTitle("Add Menu Item");
    setSize(500, 320);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(new Color(250, 250, 250));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(12, 12, 12, 12);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

    JLabel nameLabel = new JLabel("Dish Name:");
    nameLabel.setFont(labelFont);
    JTextField nameField = new JTextField(20);

    JLabel priceLabel = new JLabel("Price:");
    priceLabel.setFont(labelFont);
    JTextField priceField = new JTextField(20);

    JLabel ingredientsLabel = new JLabel("Ingredients (name:qty, ...):");
    ingredientsLabel.setFont(labelFont);
    JTextField ingredientsField = new JTextField(20);

    JButton addButton = new JButton("Add");
    addButton.setBackground(new Color(60, 130, 200));
    addButton.setForeground(Color.WHITE);
    addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
    addButton.setFocusPainted(false);
    addButton.setPreferredSize(new Dimension(150, 35));

    // Add components to panel
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(nameLabel, gbc);
    gbc.gridx = 1;
    panel.add(nameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(priceLabel, gbc);
    gbc.gridx = 1;
    panel.add(priceField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(ingredientsLabel, gbc);
    gbc.gridx = 1;
    panel.add(ingredientsField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(addButton, gbc);

    // Action
    addButton.addActionListener(e -> {
      String name = nameField.getText().trim();
      String priceStr = priceField.getText().trim();
      String ingredientsInput = ingredientsField.getText().trim();

      if (name.isEmpty() || priceStr.isEmpty() || ingredientsInput.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields must be filled.");
        return;
      }

      double price;
      try {
        price = Double.parseDouble(priceStr);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid price.");
        return;
      }

      MenuDAO.addMenuItem(name, price);
      String[] parts = ingredientsInput.split(",");
      for (String part : parts) {
        String[] ing = part.trim().split(":");
        if (ing.length != 2) continue;
        try {
          String ingName = ing[0].trim();
          int qty = Integer.parseInt(ing[1].trim());
          RecipeDAO.addRecipeItem(name, ingName, qty);
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(this, "Invalid ingredient quantity in: " + part);
        }
      }

      JOptionPane.showMessageDialog(this, "Item & ingredients added!");
      nameField.setText("");
      priceField.setText("");
      ingredientsField.setText("");
    });

    add(panel);
    setVisible(true);
  }
}