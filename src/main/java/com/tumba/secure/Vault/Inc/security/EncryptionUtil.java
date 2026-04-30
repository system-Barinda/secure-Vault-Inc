package com.tumba.secure.Vault.Inc.security;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {

    private static final String KEY = "1234567890123456";

    public static String encrypt(String data) throws Exception {
        SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    }

    public static String decrypt(String encrypted) throws Exception {
        SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)));
    }
}