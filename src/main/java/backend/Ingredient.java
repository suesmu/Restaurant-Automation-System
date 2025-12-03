package backend;

public class Ingredient {
    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    private String name;
    private int quantity;

    public Ingredient(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}