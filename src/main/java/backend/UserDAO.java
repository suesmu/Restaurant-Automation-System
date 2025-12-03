package backend;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import backend.DatabaseManager;
import backend.User;



public class UserDAO {
  public static User authenticate(String username, String password) {
    try (Connection conn = DatabaseManager.getConnection()) {
      PreparedStatement ps = conn.prepareStatement(
              "SELECT role FROM users WHERE username = ? AND password = ?"
      );
      ps.setString(1, username);
      ps.setString(2, password);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        String role = rs.getString("role");
        return new User(username, role);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}