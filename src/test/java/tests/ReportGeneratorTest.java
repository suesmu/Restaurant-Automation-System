package tests;

import backend.Order;
import backend.OrderItem;
import backend.ReportGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class ReportGeneratorTest {

  @Test
  public void testGenerateReport_WithSingleOrder() {
    OrderItem item1 = new OrderItem("Burger", 2, 10.0);  // toplam 20.0
    Order order = new Order(5, List.of(item1));

    ReportGenerator generator = new ReportGenerator();
    String report = generator.generateReport(List.of(order));

    assertTrue(report.contains("Table 5"));
    assertTrue(report.contains("Burger"));
    assertTrue(report.contains("2 x Burger = $20.0"));
    assertTrue(report.contains("Total Revenue: $20.0"));
  }

  @Test
  public void testGenerateReport_WithMultipleOrders() {
    OrderItem i1 = new OrderItem("Pizza", 1, 15.0);
    OrderItem i2 = new OrderItem("Soda", 3, 2.0); // toplam 21.0
    Order o1 = new Order(1, List.of(i1));
    Order o2 = new Order(2, List.of(i2));

    ReportGenerator g = new ReportGenerator();
    String r = g.generateReport(List.of(o1, o2));

    assertTrue(r.contains("Table 1"));
    assertTrue(r.contains("Table 2"));
    assertTrue(r.contains("Pizza"));
    assertTrue(r.contains("Soda"));
    assertTrue(r.contains("Total Revenue: $21.0"));
  }

  @Test
  public void testGenerateReport_WithNoOrders() {
    ReportGenerator generator = new ReportGenerator();
    String report = generator.generateReport(List.of());

    assertTrue(report.contains("Today's Orders"));
    assertTrue(report.contains("Total Revenue: $0.0"));
  }

}
