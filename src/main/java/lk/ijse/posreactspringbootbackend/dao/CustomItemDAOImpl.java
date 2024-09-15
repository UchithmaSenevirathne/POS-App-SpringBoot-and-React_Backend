package lk.ijse.posreactspringbootbackend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lk.ijse.posreactspringbootbackend.entity.ItemEntity;

import java.util.List;

public class CustomItemDAOImpl implements CustomItemDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ItemEntity> getByCategoryId(int catId) {
        String sql = "SELECT * FROM item WHERE catId = ?";
        Query query = entityManager.createNativeQuery(sql, ItemEntity.class);
        // Set the positional parameter (1 for the first ?)
        query.setParameter(1, catId);
        return query.getResultList();
    }
}
