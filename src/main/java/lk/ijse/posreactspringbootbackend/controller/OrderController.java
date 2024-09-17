package lk.ijse.posreactspringbootbackend.controller;

import lk.ijse.posreactspringbootbackend.dto.OrderDTO;
import lk.ijse.posreactspringbootbackend.dto.OrderItemDTO;
import lk.ijse.posreactspringbootbackend.dto.RecentOrderDTO;
import lk.ijse.posreactspringbootbackend.dto.UserOrderDetailsDTO;
import lk.ijse.posreactspringbootbackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backend/orders")
@CrossOrigin
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> placeOrder(@RequestBody OrderDTO orderDTO) {
        try {
            orderService.placeOrder(orderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/user/{userId}/details")
    public List<UserOrderDetailsDTO> getUserOrderDetails(@PathVariable int userId) {
        try {
            return orderService.getUserOrderDetails(userId);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping
    public List<UserOrderDetailsDTO> getAllOrders() {
        try {
            return orderService.getAllOrders();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/recent")
    public List<RecentOrderDTO> getRecentOrderDetails() {
        try {
            return orderService.getRecentOrderDetails();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/popular")
    public List<OrderItemDTO> getPopularItems() {
        return orderService.getPopularItems();
    }
}
