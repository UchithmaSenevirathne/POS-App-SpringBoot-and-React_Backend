package lk.ijse.posreactspringbootbackend.service.impl;

import lk.ijse.posreactspringbootbackend.dao.ItemDAO;
import lk.ijse.posreactspringbootbackend.dao.OrderDAO;
import lk.ijse.posreactspringbootbackend.dao.OrderItemDAO;
import lk.ijse.posreactspringbootbackend.dao.UserDAO;
import lk.ijse.posreactspringbootbackend.dto.ItemDTO;
import lk.ijse.posreactspringbootbackend.dto.OrderDTO;
import lk.ijse.posreactspringbootbackend.dto.UserOrderDetailsDTO;
import lk.ijse.posreactspringbootbackend.entity.ItemEntity;
import lk.ijse.posreactspringbootbackend.entity.OrderEntity;
import lk.ijse.posreactspringbootbackend.entity.OrderItem;
import lk.ijse.posreactspringbootbackend.entity.UserEntity;
import lk.ijse.posreactspringbootbackend.service.OrderService;
import lk.ijse.posreactspringbootbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    public List<UserOrderDetailsDTO> getUserOrderDetails(int userId) {
        UserEntity user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<OrderEntity> orders = orderDAO.findByUser(user);

        List<UserOrderDetailsDTO> orderDetails = new ArrayList<>();
        for (OrderEntity order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                ItemEntity item = orderItem.getItem();
                UserOrderDetailsDTO dto = new UserOrderDetailsDTO();
                dto.setOrderId(order.getOrder_id());
                dto.setUserId(order.getUser().getUserId());
                dto.setOrderAddress(order.getUser().getAddress());
                dto.setProductId(item.getItemId());
                dto.setProductName(item.getItemName());
                dto.setProductPrice(item.getItemPrice());
                dto.setProductQuantity(orderItem.getQuantity());
                dto.setProductImage(item.getItemImage());
                dto.setOrderTotalPrice(order.getTotal_price());
                dto.setOrderDate(order.getOrder_date());
                orderDetails.add(dto);
            }
        }
        return orderDetails;
    }

    @Override
    public List<UserOrderDetailsDTO> getAllOrders() {
        List<UserOrderDetailsDTO> orderDetails = new ArrayList<>();
        List<OrderEntity> orders = orderDAO.findAll();

        for (OrderEntity order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                ItemEntity item = orderItem.getItem();
                UserOrderDetailsDTO dto = new UserOrderDetailsDTO();
                dto.setOrderId(order.getOrder_id());
                dto.setUserId(order.getUser().getUserId());
                dto.setOrderAddress(order.getUser().getAddress());
                dto.setProductId(item.getItemId());
                dto.setProductName(item.getItemName());
                dto.setProductPrice(item.getItemPrice());
                dto.setProductQuantity(orderItem.getQuantity());
                dto.setProductImage(item.getItemImage());
                dto.setOrderTotalPrice(order.getTotal_price());
                dto.setOrderDate(order.getOrder_date());
                orderDetails.add(dto);
            }
        }
        return orderDetails;
    }

}
