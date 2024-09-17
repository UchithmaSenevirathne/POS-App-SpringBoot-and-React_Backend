package lk.ijse.posreactspringbootbackend.dao;

import lk.ijse.posreactspringbootbackend.dto.OrderItemDTO;
import lk.ijse.posreactspringbootbackend.entity.OrderEntity;
import lk.ijse.posreactspringbootbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findByUser(UserEntity user);

    @Query("SELECT i.itemId, i.itemName, i.itemPrice, i.itemImage, i.itemQuantity " +
            "FROM ItemEntity i " +
            "JOIN OrderItem oi ON i.itemId = oi.item.itemId " +
            "GROUP BY i.itemId ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> findPopularItemsWithQuantity();

}
