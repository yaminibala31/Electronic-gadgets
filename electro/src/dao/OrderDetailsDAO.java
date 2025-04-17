package dao;

import entity.OrderDetails;
import java.util.List;

public interface OrderDetailsDAO {
    void addOrderDetail(OrderDetails orderDetail);
    List<OrderDetails> getOrderDetailsByOrderId(int orderId);
    void updateOrderDetailQuantity(int orderDetailId, int quantity);
    void deleteOrderDetail(int orderDetailId);
}

