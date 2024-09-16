package lk.ijse.posreactspringbootbackend.service.impl;

import lk.ijse.posreactspringbootbackend.dao.ItemDAO;
import lk.ijse.posreactspringbootbackend.dao.OrderDAO;
import lk.ijse.posreactspringbootbackend.dao.UserDAO;
import lk.ijse.posreactspringbootbackend.dto.OrderDTO;
import lk.ijse.posreactspringbootbackend.entity.ItemEntity;
import lk.ijse.posreactspringbootbackend.entity.OrderEntity;
import lk.ijse.posreactspringbootbackend.entity.UserEntity;
import lk.ijse.posreactspringbootbackend.service.OrderService;
import lk.ijse.posreactspringbootbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void placeOrder(OrderDTO orderDTO) {
        UserEntity user = userDAO.findById(orderDTO.getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));

        List<ItemEntity> items = itemDAO.findAllById(orderDTO.getItemQuantities().keySet());

        // Create and save the order
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setItems(items);
        order.setTotal_price(orderDTO.getTotal_price());
        order.setOrder_date(orderDTO.getOrder_date());
        orderDAO.save(order);

        // Reduce item quantities based on the ordered quantity for each item
        for (ItemEntity item : items) {
            // Get the ordered quantity for this item from the map
            int orderedQuantity = orderDTO.getItemQuantities().get(item.getItemId());

            // Reduce the item quantity in the database by the ordered amount
            item.setItemQuantity(item.getItemQuantity() - orderedQuantity);
            itemDAO.save(item);
        }
    }

    @Override
    public List<OrderDTO> getOrdersByUser(int userId) {
        return List.of();
    }
}
