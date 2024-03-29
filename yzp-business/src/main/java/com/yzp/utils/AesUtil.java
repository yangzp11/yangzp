package com.yzp.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2023/4/28 11:06
 */
@Slf4j
public class AesUtil {
    private static final String SECRET_KEY = "yzpsecretkey1234";
    private static final String INIT_VECTOR = "abcdefghijklmnop";

    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            log.error("加密AES：{}， 失败，{}", value, ex.getMessage());
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original);
        } catch (Exception ex) {
            log.error("解密AES：{}， 失败，{}", encrypted, ex.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        String password = "108604";
        String encrypted = encrypt(password);
        System.out.println("Encrypted: " + encrypted);
        String decrypted = decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}