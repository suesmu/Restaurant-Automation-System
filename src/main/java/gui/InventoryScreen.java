package gui;

import backend.InventoryDAO;
import backend.Ingredient;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventoryScreen extends JFrame {
  public InventoryScreen() {
    setTitle("Inventory");
    setSize(600, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    Color bgColor = new Color(255, 255, 255);
    setLayout(new BorderLayout(15, 15));
    getContentPane().setBackground(bgColor);

    String[] columns = {"Ingredient", "Quantity"};
    List<Ingredient> ingredients = InventoryDAO.getAllIngredients();

    DefaultTableModel model = new DefaultTableModel(columns, 0);
    JTable table = new JTable(model);
    table.setRowHeight(28);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    table.setEnabled(false);
    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
    table.getTableHeader().setBackground(new Color(255, 255, 255));

    // Quantity kolonu sağa hizalanır
    DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
    rightAlign.setHorizontalAlignment(SwingConstants.RIGHT);
    table.getColumnModel().getColumn(1).setCellRenderer(rightAlign);

    boolean lowStockExists = false;
    StringBuilder lowItems = new StringBuilder();

    for (Ingredient ing : ingredients) {
      model.addRow(new Object[]{ing.getName(), ing.getQuantity()});
      if (ing.getQuantity() < 20) {
        lowStockExists = true;
        lowItems.append(ing.getName()).append(" → ").append(ing.getQuantity()).append("\n");
      }
    }

    JPanel tablePanel = new JPanel(new BorderLayout());
    tablePanel.setBackground(bgColor);
    tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
    tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
    tablePanel.add(table, BorderLayout.CENTER);

    add(tablePanel, BorderLayout.CENTER);

    if (lowStockExists) {
      JTextArea warningArea = new JTextArea("! LOW STOCK !\n\n" + lowItems);
      warningArea.setFocusable(false);
      warningArea.setForeground(Color.RED.darker());
      warningArea.setBackground(new Color(255, 255, 255));
      warningArea.setFont(new Font("Segoe UI", Font.BOLD, 13));
      warningArea.setMargin(new Insets(12, 15, 12, 15));

      JPanel warningPanel = new JPanel(new BorderLayout());
      warningPanel.setBackground(new Color(255, 255, 255));
      warningPanel.setBorder(BorderFactory.createCompoundBorder(
          BorderFactory.createLineBorder(Color.LIGHT_GRAY),
          BorderFactory.createEmptyBorder(8, 12, 8, 12)
      ));
      warningPanel.add(warningArea, BorderLayout.CENTER);

      add(warningPanel, BorderLayout.SOUTH);
    }

    setVisible(true);
  }
}