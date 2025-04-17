package dao;

import dao.InventoryDAO;
import entity.Inventory;
import entity.Product;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAOImpl implements InventoryDAO {

    private Connection con;

    public InventoryDAOImpl() {
        try {
            con = DBConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Connection Error: " + e.getMessage());
        }
    }

    @Override
    public void addInventory(Inventory inventory) {
        String sql = "INSERT INTO Inventory (InventoryID, ProductID, QuantityInStock, LastStockUpdate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, inventory.getInventoryId());
            ps.setInt(2, inventory.getProduct().getProductId());
            ps.setInt(3, inventory.getQuantityInStock());
            ps.setDate(4, Date.valueOf(inventory.getLastStockUpdate()));
            ps.executeUpdate();
            System.out.println("Inventory added.");
        } catch (SQLException e) {
            System.err.println("Add Inventory Failed: " + e.getMessage());
        }
    }

    @Override
    public void updateQuantity(int productId, int quantity) {
        String sql = "UPDATE Inventory SET QuantityInStock = ?, LastStockUpdate = CURRENT_DATE WHERE ProductID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.executeUpdate();
            System.out.println("Inventory updated.");
        } catch (SQLException e) {
            System.err.println("Update Inventory Failed: " + e.getMessage());
        }
    }

    @Override
    public Inventory getInventoryByProductId(int productId) {
        String sql = "SELECT * FROM Inventory i JOIN Products p ON i.ProductID = p.ProductID WHERE i.ProductID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getDouble("Price")
                );
                return new Inventory(
                        rs.getInt("InventoryID"),
                        product,
                        rs.getInt("QuantityInStock"),
                        rs.getDate("LastStockUpdate").toLocalDate()
                );
            }
        } catch (SQLException e) {
            System.err.println("Fetch Inventory Failed: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Inventory> getLowStockProducts(int threshold) {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT * FROM Inventory i JOIN Products p ON i.ProductID = p.ProductID WHERE i.QuantityInStock < ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, threshold);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getDouble("Price")
                );
                Inventory inventory = new Inventory(
                        rs.getInt("InventoryID"),
                        product,
                        rs.getInt("QuantityInStock"),
                        rs.getDate("LastStockUpdate").toLocalDate()
                );
                list.add(inventory);
            }
        } catch (SQLException e) {
            System.err.println("Low Stock Query Failed: " + e.getMessage());
        }
        return list;
    }
}

