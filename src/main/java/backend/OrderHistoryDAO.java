package backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDAO {

  public static List<String> fetchAllOrders() {
    List<String> result = new ArrayList<>();

    try (Connection conn = DatabaseManager.getConnection()) {
      // Sipariş ve öğeleri joinle
      String query = "SELECT o.id, o.table_no, oi.item_name, oi.quantity, oi.price, o.created_at " +
          "FROM orders o JOIN order_items oi ON o.id = oi.order_id ORDER BY o.id DESC";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        String row = "Order #" + rs.getInt("id") +
            " | Table: " + rs.getInt("table_no") +
            " | Item: " + rs.getString("item_name") +
            " x" + rs.getInt("quantity") +
            " ($" + rs.getDouble("price") + ")" +
            " | Time: " + rs.getTimestamp("created_at");
        result.add(row);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }
}
