package lk.ijse.posreactspringbootbackend.util;

import java.util.Base64;

public class AppUtil {
    public static String toBase64ProfilePic(String image){
        return Base64.getEncoder().encodeToString(image.getBytes()); //image eke bytes tika aragena(profilepic eka string wunata eke byte type eka thamai ganne) eka Base64 bawata path karanawa
    }
}
