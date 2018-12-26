package com.tmj.tms.utility;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESEncryptor {
    public String encrypt(String text) {
        String key = "Bar12345Bar12345"; //This was not kept in Database to stop the direct exposure to 3rd party
        if (text == null || key == null) {
            throw new NullPointerException("Given Text/Key should not be null");
        }
        String encryptedText = null;
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] encryptedHouseBlSeq = cipher.doFinal(text.getBytes());
            encryptedText = encoder.encodeToString(encryptedHouseBlSeq);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e.getMessage());
        }
        return encryptedText;
    }

    public String decrypt(String text) {
        String key = "Bar12345Bar12345";
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        String decryptedText = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            Base64.Decoder decoder = Base64.getDecoder();
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            decryptedText = new String(cipher.doFinal(decoder.decode(text)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
}
