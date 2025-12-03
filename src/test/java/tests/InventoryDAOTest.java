package tests;

import backend.DatabaseManager;
import backend.Ingredient;
import backend.InventoryDAO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class InventoryDAOTest {

  private static final String TEST_ITEM = "TestIngredient";

  @BeforeEach
  public void cleanUp() {
    try (Connection conn = DatabaseManager.getConnection()) {
      PreparedStatement ps = conn.prepareStatement("DELETE FROM inventory WHERE name = ?");
      ps.setString(1, TEST_ITEM);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testUpdateOrInsert_InsertNewIngredient() {
    InventoryDAO.updateOrInsert(TEST_ITEM, 100);

    List<Ingredient> list = InventoryDAO.getAllIngredients();
    boolean found = list.stream()
            .anyMatch(i -> i.getName().equals(TEST_ITEM) && i.getQuantity() == 100);

    assertTrue(found, "Yeni eklenen malzeme listede bulunamadı.");
  }

  @Test
  public void testUpdateOrInsert_UpdateExistingIngredient() {
    InventoryDAO.updateOrInsert(TEST_ITEM, 50);  // önce ekle
    InventoryDAO.updateOrInsert(TEST_ITEM, 80);  // sonra güncelle

    List<Ingredient> list = InventoryDAO.getAllIngredients();
    Ingredient ingredient = list.stream()
            .filter(i -> i.getName().equals(TEST_ITEM))
            .findFirst()
            .orElse(null);

    assertNotNull(ingredient);
    assertEquals(80, ingredient.getQuantity(), "Güncellenen miktar doğru değil.");
  }

  @Test
  public void testDeduct_DecreasesQuantity() {
    InventoryDAO.updateOrInsert(TEST_ITEM, 30);
    InventoryDAO.deduct(TEST_ITEM, 10);

    List<Ingredient> list = InventoryDAO.getAllIngredients();
    Ingredient ing = list.stream()
            .filter(i -> i.getName().equals(TEST_ITEM))
            .findFirst()
            .orElse(null);

    assertNotNull(ing);
    assertEquals(20, ing.getQuantity(), "Malzeme miktarı doğru şekilde azalmadı.");
  }

  @Test
  public void testIsAvailable_TrueCase() {
    InventoryDAO.updateOrInsert(TEST_ITEM, 20);
    boolean result = InventoryDAO.isAvailable(TEST_ITEM, 15);
    assertTrue(result, "Yeterli stok olduğu halde isAvailable false döndü.");
  }

  @Test
  public void testIsAvailable_FalseCase() {
    InventoryDAO.updateOrInsert(TEST_ITEM, 5);
    boolean result = InventoryDAO.isAvailable(TEST_ITEM, 10);
    assertFalse(result, "Yetersiz stok olduğu halde isAvailable true döndü.");
  }

  @Test
  public void testGetAllIngredients_NotEmpty() {
    List<Ingredient> list = InventoryDAO.getAllIngredients();
    assertNotNull(list);
  }

}
