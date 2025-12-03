package gui;

import backend.DatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class OrderHistoryScreen extends JFrame {
  public OrderHistoryScreen() {
    setTitle("Order History");
    setSize(700, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout(10, 10));
    getContentPane().setBackground(new Color(250, 250, 250));
    String[] columnNames = {"Order ID", "Items", "Total"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    JTable table = new JTable(tableModel);
    table.setRowHeight(30);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    table.setEnabled(false);

    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    table.getTableHeader().setBackground(new Color(240, 240, 240));
    table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLUE));

    try (Connection conn = DatabaseManager.getConnection()) {
      String query =
          "SELECT o.id, GROUP_CONCAT(oi.item_name SEPARATOR ', ') AS items, " +
              "SUM(oi.quantity * oi.price) AS total " +
              "FROM orders o " +
              "JOIN order_items oi ON o.id = oi.order_id " +
              "GROUP BY o.id ORDER BY o.id DESC";

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        int orderId = rs.getInt("id");
        String items = rs.getString("items");
        double total = rs.getDouble("total");

        Object[] row = {
            "#" + orderId,
            items,
            total
        };
        tableModel.addRow(row);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    // Renkli total hücre render'ı
    table.getColumnModel().getColumn(2).setCellRenderer(new PriceCellRenderer());

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
    add(scrollPane, BorderLayout.CENTER);

    setVisible(true);
  }

  private static class PriceCellRenderer extends DefaultTableCellRenderer {
    @Override
    protected void setValue(Object value) {
      if (value instanceof Number) {
        double price = ((Number) value).doubleValue();
        setText(String.format("$%.2f", price));
        setHorizontalAlignment(SwingConstants.RIGHT);
        setForeground(price > 25.0 ? new Color(200, 0, 0) : new Color(0, 150, 0));
        setFont(new Font("Segoe UI", Font.BOLD, 14));
      } else {
        setText(value != null ? value.toString() : "");
      }
    }
  }
}