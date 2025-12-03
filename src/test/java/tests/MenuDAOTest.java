package tests;

import backend.MenuDAO;
import backend.MenuItem;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class MenuDAOTest {
  private static final String TEST_DISH = "TestDish123";
  private static final double TEST_PRICE = 42.5;

  @BeforeEach
  public void cleanUpBefore() {
    // Önceden varsa test verisini sil
    try (Connection conn = backend.DatabaseManager.getConnection()) {
      PreparedStatement ps = conn.prepareStatement("DELETE FROM menu_items WHERE name = ?");
      ps.setString(1, TEST_DISH);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testAddMenuItemAndFetch() {
    // Ekle
    MenuDAO.addMenuItem(TEST_DISH, TEST_PRICE);

    // Tüm item’ları çek
    List<MenuItem> items = MenuDAO.fetchMenuItems();


    boolean found = items.stream()
            .anyMatch(i -> i.getName().equals(TEST_DISH) && i.getPrice() == TEST_PRICE);

    assertTrue(found, "Eklenen yemek fetch ile geri gelmedi!");
  }

  @Test
  public void testFetchMenuItems_NotEmpty() {
    List<MenuItem> items = MenuDAO.fetchMenuItems();
    assertNotNull(items);
  }

}
