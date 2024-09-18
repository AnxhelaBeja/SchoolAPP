package com.example.appSchool.model;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
@Component
public class GradeUtils {
    private static final String ALGORITHM = "AES";
    private static final byte[] KEY = "MySuperSecretKey".getBytes();

    public static String encryptGrade(int grade) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(String.valueOf(grade).getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting grade", e);
        }
    }

    public static int decryptGrade(String encryptedGrade) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedData = Base64.getDecoder().decode(encryptedGrade);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return Integer.parseInt(new String(decryptedData));
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting grade", e);
        }
    }
}


