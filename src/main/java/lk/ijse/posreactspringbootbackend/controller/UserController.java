package lk.ijse.posreactspringbootbackend.controller;

import lk.ijse.posreactspringbootbackend.dto.UserDTO;
import lk.ijse.posreactspringbootbackend.exception.DataPersistFailedException;
import lk.ijse.posreactspringbootbackend.exception.UserNotFoundException;
import lk.ijse.posreactspringbootbackend.service.UserService;
import lk.ijse.posreactspringbootbackend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backend/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerUser(
            @RequestPart("name") String name,
            @RequestPart("address") String address,
            @RequestPart("contact") String contact,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("profilePicture") String profilePicture
    ) {
        try {
            //handle prof.pic
            String base64ProfilePic = AppUtil.toBase64ProfilePic(profilePicture);

            UserDTO userDTO = new UserDTO();

            userDTO.setName(name);
            userDTO.setAddress(address);
            userDTO.setContact(contact);
            userDTO.setEmail(email);
            userDTO.setPassword(password);
            userDTO.setProfilePicture(base64ProfilePic);

            int res = userService.saveUser(userDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUser
            (@PathVariable ("id") int id,
             @RequestPart("name") String updateName,
             @RequestPart("address")String updateAddress,
             @RequestPart("contact") String updateContact,
             @RequestPart("email")String updateEmail,
             @RequestPart("password")String updatePassword,
             @RequestPart("profilePicture")String updateProfilePicture){

        try {
            String updateBase64ProfilePic = AppUtil.toBase64ProfilePic(updateProfilePicture);

            var updateBuidUserDto = new UserDTO();
            updateBuidUserDto.setUserId(id);
            updateBuidUserDto.setName(updateName);
            updateBuidUserDto.setAddress(updateAddress);
            updateBuidUserDto.setContact(updateContact);
            updateBuidUserDto.setEmail(updateEmail);
            updateBuidUserDto.setPassword(updatePassword);
            updateBuidUserDto.setProfilePicture(updateBase64ProfilePic);

            userService.updateUser(updateBuidUserDto);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getSelectedUser(@PathVariable ("id") int userId){
        return userService.getSelectedUser(userId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable ("id") int userId){
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        return userService.deleteUser(userId) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
