package lk.ijse.posreactspringbootbackend.dao;

import lk.ijse.posreactspringbootbackend.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDAO extends JpaRepository<ItemEntity, Integer> {
}
