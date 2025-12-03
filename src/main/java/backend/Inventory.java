package backend;

import java.util.HashMap;

public class Inventory {
    private HashMap<String, Integer> stock = new HashMap<>();

    public Inventory() {

    }

    public boolean isAvailable(String item, int quantity) {
        return stock.getOrDefault(item, 0) >= quantity;
    }

    public void deduct(String item, int quantity) {
        if (isAvailable(item, quantity)) {
            stock.put(item, stock.get(item) - quantity);
        }
    }

    public HashMap<String, Integer> getStock() {
        return stock;
    }
}
