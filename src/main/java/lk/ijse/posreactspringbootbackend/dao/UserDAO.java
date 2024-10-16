package lk.ijse.posreactspringbootbackend.dao;

import lk.ijse.posreactspringbootbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<UserEntity, Integer> {

    UserEntity getUserEntitiesByUserId(int userId);

    boolean existsByEmail(String email);

    UserEntity findByEmail(String email);
}
