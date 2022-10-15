package controller.util.passhash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordStorage {

    public static String getSecurePassword(String password) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static boolean checkSecurePassword(String passwordToCheck,
                                              String passwordHash) {

        String checkPassHash = getSecurePassword(passwordToCheck);

        return passwordHash.equals(checkPassHash);
    }

    public static void main(String[] args) {
        System.out.println(PasswordStorage.getSecurePassword("qwerty12345"));
    }

    String s = "f6ee94ecb014f74f887b9dcc52daecf73ab3e3333320cadd98bcb59d895c52f5";

}
