package lk.ijse.posreactspringbootbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private int userId;
    private String name;
    private String address;
    private String contact;
    private String email;
    private String password;
    private String profilePicture;
}
