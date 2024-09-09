package lk.ijse.posreactspringbootbackend.controller;

import lk.ijse.posreactspringbootbackend.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

        }
    }
}
