package lk.ijse.posreactspringbootbackend.service;

import lk.ijse.posreactspringbootbackend.dto.OrderDTO;
import lk.ijse.posreactspringbootbackend.dto.OrderItemDTO;
import lk.ijse.posreactspringbootbackend.dto.RecentOrderDTO;
import lk.ijse.posreactspringbootbackend.dto.UserOrderDetailsDTO;

import java.util.List;

public interface OrderService {
    void placeOrder(OrderDTO orderDTO);

    List<UserOrderDetailsDTO> getUserOrderDetails(int userId);

    List<UserOrderDetailsDTO> getAllOrders();

    List<RecentOrderDTO> getRecentOrderDetails();

    List<OrderItemDTO> getPopularItems();
}
