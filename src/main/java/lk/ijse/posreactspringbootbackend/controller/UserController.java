package lk.ijse.posreactspringbootbackend.controller;

import lk.ijse.posreactspringbootbackend.dto.AuthDTO;
import lk.ijse.posreactspringbootbackend.dto.ResponseDTO;
import lk.ijse.posreactspringbootbackend.dto.UserDTO;
import lk.ijse.posreactspringbootbackend.exception.DataPersistFailedException;
import lk.ijse.posreactspringbootbackend.exception.UserNotFoundException;
import lk.ijse.posreactspringbootbackend.service.UserService;
import lk.ijse.posreactspringbootbackend.service.impl.UserServiceImpl;
import lk.ijse.posreactspringbootbackend.util.AppUtil;
import lk.ijse.posreactspringbootbackend.util.JwtUtil;
import lk.ijse.posreactspringbootbackend.util.VarList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backend/user")
@CrossOrigin
public class UserController {

    private final UserServiceImpl userService;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    //constructor injection
    public UserController(JwtUtil jwtUtil, UserServiceImpl userService, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> registerUser(
            @RequestPart("name") String name,
            @RequestPart("address") String address,
            @RequestPart("contact") String contact,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("profilePicture") String profilePicture
    ) {
        System.out.println("save control-start");
        try {
            System.out.println("save control-try");
            //handle prof.pic
//            String base64ProfilePic = AppUtil.toBase64ProfilePic(profilePicture);

            UserDTO userDTO = new UserDTO();

            userDTO.setName(name);
            userDTO.setAddress(address);
            userDTO.setContact(contact);
            userDTO.setEmail(email);
            userDTO.setPassword(password);
            userDTO.setProfilePicture(profilePicture);

            int res = userService.saveUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    System.out.println("save control-switch");
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setEmail(userDTO.getEmail());
                    authDTO.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid Credentials", e.getMessage()));
        }

        UserDTO loadedUser = userService.loadUserDetailsByUsername(userDTO.getEmail());
        if (loadedUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        // Check user role
        if ("ADM".equals(loadedUser.getUserRole())) {
            // Handle admin login
            String token = jwtUtil.generateToken(loadedUser);
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
            }

            AuthDTO authDTO = new AuthDTO();
            authDTO.setEmail(loadedUser.getEmail());
            authDTO.setToken(token);
            authDTO.setRole("ADM");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Admin Login Success", authDTO));
        } else if ("USER".equals(loadedUser.getUserRole())) {
            // Handle user login
            String token = jwtUtil.generateToken(loadedUser);
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
            }

            AuthDTO authDTO = new AuthDTO();
            authDTO.setEmail(loadedUser.getEmail());
            authDTO.setToken(token);
            authDTO.setRole("USER");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "User Login Success", authDTO));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDTO(VarList.Forbidden, "Invalid Role", null));
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

    @GetMapping("/details")
    public ResponseEntity<UserDTO> getUserDetails(@RequestParam String email) {
        UserDTO userDTO = userService.loadUserDetailsByUsername(email);
//        String profilePic = AppUtil.toProfilePic(userDTO.getProfilePicture());
//        userDTO.setProfilePicture(profilePic);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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

    @GetMapping("/id/{email}")
    public ResponseEntity<Integer> getUserIdByEmail(@PathVariable("email") String email) {
        int userId = userService.getUserIdByEmail(email);
        return ResponseEntity.ok(userId); // Send back the userId as a response
    }
}
