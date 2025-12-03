package tests;

import backend.OrderItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class OrderItemTest {
  @Test
  public void testConstructorAndGetters() {
    OrderItem item = new OrderItem("Cola", 2, 3.5);
    assertEquals("Cola", item.getItemName());
    assertEquals(2, item.getQuantity());
    assertEquals(3.5, item.getUnitPrice(), 0.001);
  }

  @Test
  public void testGetTotalPrice() {
    OrderItem item = new OrderItem("Pizza", 3, 12.0);
    double expectedTotal = 3 * 12.0;
    assertEquals(expectedTotal, item.getTotalPrice(), 0.001);
  }

  @Test
  public void testToStringContainsItemName() {
    OrderItem item = new OrderItem("Burger", 1, 8.0);
    String result = item.toString();
    assertTrue(result.contains("Burger"));
    assertTrue(result.contains("1 x"));
    assertTrue(result.contains("= $8.0"));
  }

  @Test
  public void testZeroQuantityTotal() {
    OrderItem item = new OrderItem("Water", 0, 2.0);
    assertEquals(0.0, item.getTotalPrice(), 0.001);
  }

}
