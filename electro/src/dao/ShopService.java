package dao;

import entity.*;
import exception.*;
import java.util.*;

public class ShopService {
    private List<Product> products = new ArrayList<>();
    private List<Orders> orders = new ArrayList<>();
    private Map<Integer, Inventory> inventoryMap = new TreeMap<>();

    public void addProduct(Product p) throws InvalidDataException {
        for (Product existing : products) {
            if (existing.getProductName().equalsIgnoreCase(p.getProductName())) {
                throw new InvalidDataException("Duplicate product name.");
            }
        }
        products.add(p);
    }

    public void updateInventory(int productId, int quantity) throws InsufficientStockException {
        Inventory inv = inventoryMap.get(productId);
        if (inv == null) throw new InsufficientStockException("Product not found in inventory.");
        if (!inv.isProductAvailable(quantity)) throw new InsufficientStockException("Not enough stock.");
        inv.removeFromInventory(quantity);
    }

    public void addOrder(Orders order) {
        orders.add(order);
    }

    public void sortOrdersByDate() {
        orders.sort(Comparator.comparing(Orders::getOrderDate));
    }

    public List<Product> searchProductsByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getProductName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }
    
    public List<Orders> getOrders() {
        return orders;
    }
}

