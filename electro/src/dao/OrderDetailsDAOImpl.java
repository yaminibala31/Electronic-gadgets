package dao;

import dao.OrderDetailsDAO;
import entity.OrderDetails;
import entity.Orders;
import entity.Product;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    private Connection con;

    public OrderDetailsDAOImpl() {
        try {
            con = DBConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Connection Error: " + e.getMessage());
        }
    }

    @Override
    public void addOrderDetail(OrderDetails orderDetail) {
        String sql = "INSERT INTO OrderDetails (OrderDetailID, OrderID, ProductID, Quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderDetail.getOrderDetailId());
            ps.setInt(2, orderDetail.getOrder().getOrderId());
            ps.setInt(3, orderDetail.getProduct().getProductId());
            ps.setInt(4, orderDetail.getQuantity());
            ps.executeUpdate();
            System.out.println("Order detail added.");
        } catch (SQLException e) {
            System.err.println("Add OrderDetail Failed: " + e.getMessage());
        }
    }

    @Override
    public List<OrderDetails> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetails> details = new ArrayList<>();
        String sql = "SELECT od.*, p.ProductName, p.Description, p.Price FROM OrderDetails od JOIN Products p ON od.ProductID = p.ProductID WHERE od.OrderID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getDouble("Price")
                );

                Orders order = new Orders(orderId, null); // minimal for link

                OrderDetails detail = new OrderDetails(
                        rs.getInt("OrderDetailID"),
                        order,
                        product,
                        rs.getInt("Quantity")
                );

                details.add(detail);
            }
        } catch (SQLException e) {
            System.err.println("Fetch OrderDetails Failed: " + e.getMessage());
        }
        return details;
    }

    @Override
    public void updateOrderDetailQuantity(int orderDetailId, int quantity) {
        String sql = "UPDATE OrderDetails SET Quantity = ? WHERE OrderDetailID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, orderDetailId);
            ps.executeUpdate();
            System.out.println("Order detail quantity updated.");
        } catch (SQLException e) {
            System.err.println("Update OrderDetail Failed: " + e.getMessage());
        }
    }

    @Override
    public void deleteOrderDetail(int orderDetailId) {
        String sql = "DELETE FROM OrderDetails WHERE OrderDetailID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderDetailId);
            ps.executeUpdate();
            System.out.println("Order detail deleted.");
        } catch (SQLException e) {
            System.err.println("Delete OrderDetail Failed: " + e.getMessage());
        }
    }
}
