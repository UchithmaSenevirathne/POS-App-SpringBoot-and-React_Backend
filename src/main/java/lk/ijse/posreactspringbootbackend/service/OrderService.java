package lk.ijse.posreactspringbootbackend.service;

import lk.ijse.posreactspringbootbackend.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    void placeOrder(OrderDTO orderDTO);
}
