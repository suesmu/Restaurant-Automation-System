package backend;

import java.sql.*;
import java.util.*;

public class RecipeDAO {
  public static Map<String, Integer> getIngredientsForDish(String dishName) {
    Map<String, Integer> ingredients = new HashMap<>();
    try (Connection conn = DatabaseManager.getConnection()) {
      PreparedStatement ps = conn.prepareStatement(
          "SELECT ingredient_name, quantity FROM recipe_items WHERE dish_name = ?"
      );
      ps.setString(1, dishName);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        ingredients.put(rs.getString("ingredient_name"), rs.getInt("quantity"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ingredients;
  }

  public static void addRecipeItem(String dish, String ingredient, int quantity) {
    try (Connection conn = DatabaseManager.getConnection()) {
      PreparedStatement ps = conn.prepareStatement(
          "INSERT INTO recipe_items (dish_name, ingredient_name, quantity) VALUES (?, ?, ?)"
      );
      ps.setString(1, dish);
      ps.setString(2, ingredient);
      ps.setInt(3, quantity);
      ps.executeUpdate();

    }
    catch (SQLException e) {
      e.printStackTrace();

    }

  }

}
