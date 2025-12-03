package backend;
import java.util.List;

public class ReportGenerator {
    public String generateReport(List<Order> orders) {
        StringBuilder sb = new StringBuilder();
        sb.append("Today's Orders:\n");

        for (Order order : orders) {
            sb.append("Table ").append(order.getTableNo()).append(":\n");
            for (OrderItem item : order.getItems()) {
                sb.append(" - ").append(item.toString()).append("\n");
            }
        }

        double total = orders.stream().mapToDouble(Order::getTotal).sum();
        sb.append("Total Revenue: $").append(total).append("\n");
        return sb.toString();
    }
}
