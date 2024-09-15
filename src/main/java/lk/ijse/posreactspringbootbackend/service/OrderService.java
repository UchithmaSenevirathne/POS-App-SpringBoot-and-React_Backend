package lk.ijse.posreactspringbootbackend.service;

import lk.ijse.posreactspringbootbackend.dto.OrderDTO;

public interface OrderService {
    void saveOrder(OrderDTO orderDTO) throws Exception;

    void placeOrder(OrderDTO orderDTO);
}
