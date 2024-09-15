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
    public void saveOrder(OrderDTO orderDTO) throws Exception {
        UserEntity user = userDAO.findById(orderDTO.getUser_id())
                .orElseThrow(() -> new Exception("User not found"));

        List<ItemEntity> items = itemDAO.findAllById(orderDTO.getItemIds());

        double totalPrice = items.stream().mapToDouble(ItemEntity::getItemPrice).sum();

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setItems(items);
        order.setQuantity(orderDTO.getQuantity());
        order.setTotal_price(totalPrice);
        order.setOrder_date(orderDTO.getOrder_date());

        orderDAO.save(order);
    }

    @Override
    public void placeOrder(OrderDTO orderDTO) {
        UserEntity user = userDAO.findById(orderDTO.getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));

        List<ItemEntity> items = itemDAO.findAllById(orderDTO.getItemIds());

        // Create and save the order
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setItems(items);
        order.setQuantity(orderDTO.getQuantity());
        order.setTotal_price(orderDTO.getTotal_price());
        order.setOrder_date(orderDTO.getOrder_date());
        orderDAO.save(order);

        // Reduce item quantities
        for (ItemEntity item : items) {
            item.setItemQuantity(item.getItemQuantity() - 1); // Adjust as needed
            itemDAO.save(item);
        }
    }
}
