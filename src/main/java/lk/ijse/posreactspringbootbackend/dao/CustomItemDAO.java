package lk.ijse.posreactspringbootbackend.dao;

import lk.ijse.posreactspringbootbackend.entity.ItemEntity;

import java.util.List;

public interface CustomItemDAO {
    List<ItemEntity> getByCategoryId(int catId);
}
