package tests;

import backend.DatabaseManager;
import backend.OrderDAO;
import backend.OrderItem;
import backend.Order;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class OrderDAOTest {

  private static final String TEST_ITEM_NAME = "UnitTestItem";
  private static final int TEST_TABLE_NO = 99;

  @BeforeEach
  public void cleanUp() {
    try (Connection conn = DatabaseManager.getConnection()) {
      PreparedStatement ps1 = conn.prepareStatement(
              "DELETE FROM order_items WHERE item_name = ?"
      );
      ps1.setString(1, TEST_ITEM_NAME);
      ps1.executeUpdate();

      PreparedStatement ps2 = conn.prepareStatement(
              "DELETE FROM orders WHERE table_no = ?"
      );
      ps2.setInt(1, TEST_TABLE_NO);
      ps2.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSaveOrder_AddsToDatabase() {
    // 1. Sipariş objesi hazırla
    OrderItem item = new OrderItem(TEST_ITEM_NAME, 2, 10.0);
    Order order = new Order(TEST_TABLE_NO, List.of(item));

    // 2. OrderDAO ile kaydet
    OrderDAO.saveOrder(order);

    // 3. order_items tablosunda arama
    boolean orderItemExists = false;

    try (Connection conn = DatabaseManager.getConnection()) {
      PreparedStatement ps = conn.prepareStatement(
              "SELECT * FROM order_items WHERE item_name = ?"
      );
      ps.setString(1, TEST_ITEM_NAME);
      ResultSet rs = ps.executeQuery();
      orderItemExists = rs.next();
    } catch (Exception e) {
      e.printStackTrace();
    }

    assertTrue(orderItemExists, "Sipariş öğesi veritabanına eklenmemiş gibi görünüyor.");
  }


}
