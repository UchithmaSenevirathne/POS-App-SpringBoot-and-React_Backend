package lk.ijse.posreactspringbootbackend.controller;

import lk.ijse.posreactspringbootbackend.dto.UserDTO;
import lk.ijse.posreactspringbootbackend.service.UserRegisterService;
import lk.ijse.posreactspringbootbackend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserRegisterController {

    @Autowired
    private final UserRegisterService userRegisterService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveUser(
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

            userRegisterService.saveUser(userDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
