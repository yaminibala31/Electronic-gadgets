package dao;

import entity.Inventory;
import java.util.List;

public interface InventoryDAO {
    void addInventory(Inventory inventory);
    void updateQuantity(int productId, int quantity);
    Inventory getInventoryByProductId(int productId);
    List<Inventory> getLowStockProducts(int threshold);
}
