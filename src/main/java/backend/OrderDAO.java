package backend;

import java.sql.*;
import java.util.List;

public class OrderDAO {
  public static void saveOrder(Order order) {
    try (Connection conn = DatabaseManager.getConnection()) {
      // Siparişi ekle
      PreparedStatement ps = conn.prepareStatement(
          "INSERT INTO orders (table_no) VALUES (?)",
          Statement.RETURN_GENERATED_KEYS
      );
      ps.setInt(1, order.getTableNo());
      ps.executeUpdate();

      // Order ID'yi al
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) {
        int orderId = rs.getInt(1);

        // Siparişe ait ürünleri order_items tablosuna ekle
        for (OrderItem item : order.getItems()) {
          PreparedStatement itemPs = conn.prepareStatement(
              "INSERT INTO order_items (order_id, item_name, quantity, price) VALUES (?, ?, ?, ?)"
          );
          itemPs.setInt(1, orderId);
          itemPs.setString(2, item.getItemName());
          itemPs.setInt(3, item.getQuantity());
          itemPs.setDouble(4, item.getTotalPrice() / item.getQuantity());
          itemPs.executeUpdate();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
