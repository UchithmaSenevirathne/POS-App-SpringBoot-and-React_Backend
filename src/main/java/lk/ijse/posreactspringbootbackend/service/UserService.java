package lk.ijse.posreactspringbootbackend.service;

import lk.ijse.posreactspringbootbackend.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    int saveUser(UserDTO userDTO);

    UserDTO getSelectedUser(int userId);

    List<UserDTO> getAllUsers();

    void updateUser(UserDTO updateBuidUserDto);

    void deleteUser(int userId);

    UserDetails loadUserByUsername(String email);

    UserDTO loadUserDetailsByUsername(String email);
}
