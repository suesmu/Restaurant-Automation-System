package backend;

public class OrderItem {
    private String itemName;
    private int quantity;
    private double unitPrice;

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public OrderItem(String itemName, int quantity, double unitPrice) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return quantity * unitPrice;
    }

    public String toString() {
        return quantity + " x " + itemName + " = $" + getTotalPrice();
    }
}
