package tests;

import backend.DatabaseManager;
import backend.RecipeDAO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class RecipeDAOTest {

  private static final String TEST_DISH = "TestDish_Unit";
  private static final String TEST_INGREDIENT = "TestIngredient_Unit";

  @BeforeEach
  public void cleanupBefore() {
    try (Connection conn = DatabaseManager.getConnection()) {
      PreparedStatement ps = conn.prepareStatement(
              "DELETE FROM recipe_items WHERE dish_name = ? OR ingredient_name = ?"
      );
      ps.setString(1, TEST_DISH);
      ps.setString(2, TEST_INGREDIENT);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testAddAndRetrieveRecipeItem() {
    // 1. Malzeme ekle
    RecipeDAO.addRecipeItem(TEST_DISH, TEST_INGREDIENT, 3);

    // 2. Malzemeleri geri çek
    Map<String, Integer> result = RecipeDAO.getIngredientsForDish(TEST_DISH);

    // 3. Kontroller
    assertNotNull(result);
    assertTrue(result.containsKey(TEST_INGREDIENT));
    assertEquals(3, result.get(TEST_INGREDIENT));
  }

  @Test
  public void testGetIngredientsForNonexistentDish() {
    Map<String, Integer> result = RecipeDAO.getIngredientsForDish("NoSuchDishXYZ");
    assertNotNull(result);
    assertTrue(result.isEmpty(), "Olmayan yemek için boş map dönmeliydi");
  }

}
