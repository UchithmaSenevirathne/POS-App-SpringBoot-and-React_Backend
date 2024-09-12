package lk.ijse.posreactspringbootbackend.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class AppUtil {
    public static String toBase64ProfilePic(String image){
        return Base64.getEncoder().encodeToString(image.getBytes());
    }

    public static String toProfilePic(String base64ProfilePic){
        return new String(Base64.getDecoder().decode(base64ProfilePic));
    }
}
