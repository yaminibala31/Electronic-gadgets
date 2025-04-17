package dao;

import dao.OrderDAO;
import entity.Orders;
import connect.Dbutil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private Connection con;

    public OrderDAOImpl() {
        
            try {
				con = Dbutil.getDBConn();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace():
			}
        
    

    @Override
    public void placeOrder(Orders order) {
        String sql = "INSERT INTO Orders (OrderID, CustomerID, OrderDate, TotalAmount, Status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, order.getOrderId());
            ps.setInt(2, order.getCustomer().getCustomerId());
            ps.setDate(3, Date.valueOf(order.getOrderDate()));
            ps.setDouble(4, order.getTotalAmount());
            ps.setString(5, order.getOrderStatus());
            ps.executeUpdate();
            System.out.println("Order placed successfully.");
        } catch (SQLException e) {
            System.err.println("Order insert failed: " + e.getMessage());
        }
    }

    @Override
    public Orders getOrderById(int orderId) {
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Orders(
                        rs.getInt("OrderID"),
                        null, 
                        rs.getDate("OrderDate").toLocalDate(),
                        rs.getDouble("TotalAmount"),
                        rs.getString("Status")
                );
            }
        } catch (SQLException e) {
            System.err.println("Get order failed: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Orders> getOrdersByCustomerId(int customerId) {
        List<Orders> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE CustomerID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Orders order = new Orders(
                        rs.getInt("OrderID"),
                        null, 
                        rs.getDate("OrderDate").toLocalDate(),
                        rs.getDouble("TotalAmount"),
                        rs.getString("Status")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Get orders failed: " + e.getMessage());
        }
        return orders;
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE Orders SET Status = ? WHERE OrderID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
            System.out.println("Order status updated.");
        } catch (SQLException e) {
            System.err.println("Update status failed: " + e.getMessage());
        }
    }
}

