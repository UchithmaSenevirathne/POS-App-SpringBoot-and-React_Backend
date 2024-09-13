package lk.ijse.posreactspringbootbackend.dao;

import lk.ijse.posreactspringbootbackend.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDAO extends JpaRepository<OrderEntity, Integer> {
}
