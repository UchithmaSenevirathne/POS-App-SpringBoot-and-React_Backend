package lk.ijse.posreactspringbootbackend.dao;

import lk.ijse.posreactspringbootbackend.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDAO extends JpaRepository<ItemEntity, Integer>, CustomItemDAO {

    ItemEntity getItemEntitiesByItemId(int itemId);

}
