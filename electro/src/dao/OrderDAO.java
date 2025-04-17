package dao;

import entity.Orders;
import java.util.List;

public interface OrderDAO {
    void placeOrder(Orders order);
    Orders getOrderById(int orderId);
    List<Orders> getOrdersByCustomerId(int customerId);
    void updateOrderStatus(int orderId, String status);
}
