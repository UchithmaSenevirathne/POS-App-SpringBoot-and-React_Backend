package lk.ijse.posreactspringbootbackend.service.impl;

import lk.ijse.posreactspringbootbackend.customobj.UserErrorResponse;
import lk.ijse.posreactspringbootbackend.customobj.UserResponse;
import lk.ijse.posreactspringbootbackend.dao.UserDAO;
import lk.ijse.posreactspringbootbackend.dto.UserDTO;
import lk.ijse.posreactspringbootbackend.entity.UserEntity;
import lk.ijse.posreactspringbootbackend.exception.DataPersistFailedException;
import lk.ijse.posreactspringbootbackend.exception.UserNotFoundException;
import lk.ijse.posreactspringbootbackend.service.UserRegisterService;
import lk.ijse.posreactspringbootbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Optional<UserEntity> tmpUserEntity = userDAO.findById(updateBuidUserDto.getUserId());
        if(!tmpUserEntity.isPresent()){
            throw new UserNotFoundException("User Not Found");
        }else {
            tmpUserEntity.get().setName(updateBuidUserDto.getName());
            tmpUserEntity.get().setAddress(updateBuidUserDto.getAddress());
            tmpUserEntity.get().setContact(updateBuidUserDto.getContact());
            tmpUserEntity.get().setEmail(updateBuidUserDto.getEmail());
            tmpUserEntity.get().setPassword(updateBuidUserDto.getPassword());
            tmpUserEntity.get().setProfilePicture(updateBuidUserDto.getProfilePicture());
        }
    }

    @Override
    public void deleteUser(int userId) {
        Optional<UserEntity> tmpUserEntity = userDAO.findById(userId);
        if(!tmpUserEntity.isPresent()){
            throw new UserNotFoundException("User Not Found");
        }else {
            userDAO.deleteById(userId);
        }
    }

}
