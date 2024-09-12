package lk.ijse.posreactspringbootbackend.service;

import lk.ijse.posreactspringbootbackend.dto.UserDTO;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);

    UserDTO getSelectedUser(int userId);

    List<UserDTO> getAllUsers();

    void updateUser(UserDTO updateBuidUserDto);

    void deleteUser(int userId);
}
