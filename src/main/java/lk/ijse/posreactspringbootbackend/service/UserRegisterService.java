package lk.ijse.posreactspringbootbackend.service;

import lk.ijse.posreactspringbootbackend.dto.UserDTO;

public interface UserRegisterService {
    void saveUser(UserDTO userDTO);
}
