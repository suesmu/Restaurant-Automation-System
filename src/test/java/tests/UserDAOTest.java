package tests;

import backend.User;
import backend.UserDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class UserDAOTest {
  @Test
  public void testAuthenticate_ValidUser() {
    // Bu kullanıcı veritabanında olmalı!
    User user = UserDAO.authenticate("admin", "admin");
    assertNotNull(user, "Kullanıcı doğrulanamadı ama doğrulanmalıydı");
    assertEquals("admin", user.getUsername());
    assertEquals("admin", user.getRole());
  }

  @Test
  public void testAuthenticate_InvalidPassword() {
    User user = UserDAO.authenticate("admin", "wrongpass");
    assertNull(user, "Hatalı şifre ile kullanıcı doğrulanmamalı");
  }

  @Test
  public void testAuthenticate_NonexistentUser() {
    User user = UserDAO.authenticate("nosuchuser", "pass123");
    assertNull(user, "Mevcut olmayan kullanıcı doğrulanmamalı");
  }

}
