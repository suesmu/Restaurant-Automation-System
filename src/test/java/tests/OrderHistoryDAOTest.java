package tests;

import backend.DatabaseManager;
import backend.OrderHistoryDAO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class OrderHistoryDAOTest {

  @BeforeEach
  public void insertSampleOrderIfNeeded() {
    try (Connection conn = DatabaseManager.getConnection()) {

      // 1. Sipariş oluştur
      PreparedStatement ps1 = conn.prepareStatement("INSERT INTO orders (table_no, created_at) VALUES (?, NOW())", PreparedStatement.RETURN_GENERATED_KEYS);
      ps1.setInt(1, 10);
      ps1.executeUpdate();
      ResultSet rs = ps1.getGeneratedKeys();
      int orderId = -1;
      if (rs.next()) {
        orderId = ((ResultSet) rs).getInt(1);
      }

      // 2. Order item ekle
      if (orderId != -1) {
        PreparedStatement ps2 = conn.prepareStatement("INSERT INTO order_items (order_id, item_name, quantity, price) VALUES (?, ?, ?, ?)");
        ps2.setInt(1, orderId);
        ps2.setString(2, "UnitTestItem");
        ps2.setInt(3, 1);
        ps2.setDouble(4, 9.99);
        ps2.executeUpdate();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testFetchAllOrders_NotEmpty() {
    List<String> orders = OrderHistoryDAO.fetchAllOrders();
    assertNotNull(orders);
    assertFalse(orders.isEmpty(), "fetchAllOrders sonucu boş dönmemeli.");
  }

  @Test
  public void testFetchAllOrders_FormatCheck() {
    List<String> orders = OrderHistoryDAO.fetchAllOrders();
    String first = orders.get(0);
    assertTrue(first.startsWith("Order #"), "İlk satır beklenen formatta başlamıyor.");
    assertTrue(first.contains("Table: "), "Satırda masa bilgisi eksik.");
    assertTrue(first.contains("Item: "), "Satırda ürün bilgisi eksik.");
    assertTrue(first.contains("Time:"), "Satırda zaman bilgisi eksik.");
  }

}
