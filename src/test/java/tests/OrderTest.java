package tests;

import backend.Order;
import backend.OrderItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
public class OrderTest {
    @Test
    public void testGetTableNo() {
        Order order = new Order(7, List.of());
        assertEquals(7, order.getTableNo());
    }

    @Test
    public void testGetItems() {
        OrderItem item1 = new OrderItem("Pizza", 2, 12.5);
        OrderItem item2 = new OrderItem("Soda", 1, 3.0);
        Order order = new Order(3, List.of(item1, item2));

        List<OrderItem> items = order.getItems();
        assertEquals(2, items.size());
        assertEquals("Pizza", items.get(0).getItemName());
    }

    @Test
    public void testGetTotal() {
        OrderItem item1 = new OrderItem("Burger", 2, 10.0);  // total = 20.0
        OrderItem item2 = new OrderItem("Fries", 1, 5.0);    // total = 5.0
        Order order = new Order(4, List.of(item1, item2));

        double total = order.getTotal();
        assertEquals(25.0, total, 0.01); // tolerans 0.01
    }

    @Test
    public void testToString_NotNull() {
        OrderItem item = new OrderItem("Tea", 1, 2.0);
        Order order = new Order(1, List.of(item));
        String result = order.toString();
        assertNotNull(result);
        assertTrue(result.contains("tableNo=1"));
        assertTrue(result.contains("Tea"));
    }


}