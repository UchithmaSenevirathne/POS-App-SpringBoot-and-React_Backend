package lk.ijse.posreactspringbootbackend.service.impl;

import lk.ijse.posreactspringbootbackend.customobj.UserErrorResponse;
import lk.ijse.posreactspringbootbackend.customobj.UserResponse;
import lk.ijse.posreactspringbootbackend.dao.UserDAO;
import lk.ijse.posreactspringbootbackend.dto.UserDTO;
import lk.ijse.posreactspringbootbackend.entity.UserEntity;
import lk.ijse.posreactspringbootbackend.exception.DataPersistFailedException;
import lk.ijse.posreactspringbootbackend.service.UserRegisterService;
import lk.ijse.posreactspringbootbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveUser(UserDTO userDTO) {
        UserEntity savedUser = userDAO.save(mapping.convertToUserEntity(userDTO));

        if (savedUser == null && savedUser.getUserId() == 0) {
            throw new DataPersistFailedException("User not saved");
        }
    }

    @Override
    public UserDTO getSelectedUser(int userId) {
        return mapping.convertToUserDTO(userDAO.getUserEntitiesByUserId(userId));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mapping.convertToUserDTOList(userDAO.findAll());
    }

    @Override
    public void updateUser(UserDTO updateBuidUserDto) {

    }

}
