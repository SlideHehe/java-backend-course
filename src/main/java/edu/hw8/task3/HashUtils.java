package edu.hw8.task3;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    private HashUtils() {
    }

    public static String encodeToMd5(String string) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md5.update(StandardCharsets.UTF_8.encode(string));
        return String.format("%032x", new BigInteger(1, md5.digest()));
    }
}
