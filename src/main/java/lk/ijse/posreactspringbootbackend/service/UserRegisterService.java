package lk.ijse.posreactspringbootbackend.service;

import lk.ijse.posreactspringbootbackend.customobj.UserResponse;
import lk.ijse.posreactspringbootbackend.dto.UserDTO;

import java.util.List;

public interface UserRegisterService {
    void saveUser(UserDTO userDTO);

    UserDTO getSelectedUser(int userId);

    List<UserDTO> getAllUsers();
}
