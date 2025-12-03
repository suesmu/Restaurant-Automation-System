package backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

  public static List<MenuItem> fetchMenuItems() {
    List<MenuItem> items = new ArrayList<>();
    try (Connection conn = DatabaseManager.getConnection()) {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM menu_items");
      while (rs.next()) {
        items.add(new MenuItem(rs.getString("name"), rs.getDouble("price")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return items;
  }

  public static void addMenuItem(String name, double price) {
    try (Connection conn = DatabaseManager.getConnection()) {
      PreparedStatement ps = conn.prepareStatement("INSERT INTO menu_items (name, price) VALUES (?, ?)");
      ps.setString(1, name);
      ps.setDouble(2, price);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
