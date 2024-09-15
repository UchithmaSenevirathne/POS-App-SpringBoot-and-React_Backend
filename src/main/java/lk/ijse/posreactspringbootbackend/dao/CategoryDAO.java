package lk.ijse.posreactspringbootbackend.dao;

import lk.ijse.posreactspringbootbackend.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDAO extends JpaRepository<CategoryEntity, Integer> {

}
