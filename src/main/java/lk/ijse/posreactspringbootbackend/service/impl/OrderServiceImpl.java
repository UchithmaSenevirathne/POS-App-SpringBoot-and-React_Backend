package lk.ijse.posreactspringbootbackend.service.impl;

import lk.ijse.posreactspringbootbackend.dao.ItemDAO;
import lk.ijse.posreactspringbootbackend.dao.OrderDAO;
import lk.ijse.posreactspringbootbackend.dao.OrderItemDAO;
import lk.ijse.posreactspringbootbackend.dao.UserDAO;
import lk.ijse.posreactspringbootbackend.dto.ItemDTO;
import lk.ijse.posreactspringbootbackend.dto.OrderDTO;
import lk.ijse.posreactspringbootbackend.entity.ItemEntity;
import lk.ijse.posreactspringbootbackend.entity.OrderEntity;
import lk.ijse.posreactspringbootbackend.entity.OrderItem;
import lk.ijse.posreactspringbootbackend.entity.UserEntity;
import lk.ijse.posreactspringbootbackend.service.OrderService;
import lk.ijse.posreactspringbootbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private OrderItemDAO orderItemDAO;

    @Override
    public void placeOrder(OrderDTO orderDTO) {
        UserEntity user = userDAO.findById(orderDTO.getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));

        List<ItemEntity> items = itemDAO.findAllById(orderDTO.getItemQuantities().keySet());

        // Create and save the order
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setTotal_price(orderDTO.getTotal_price());
        order.setOrder_date(orderDTO.getOrder_date());

        // Create OrderItems and reduce quantities
        List<OrderItem> orderItems = items.stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setItem(item);
                    orderItem.setQuantity(orderDTO.getItemQuantities().get(item.getItemId()));
                    item.setItemQuantity(item.getItemQuantity() - orderItem.getQuantity());
                    itemDAO.save(item); // Save updated item quantity
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        orderDAO.save(order);
    }

}
