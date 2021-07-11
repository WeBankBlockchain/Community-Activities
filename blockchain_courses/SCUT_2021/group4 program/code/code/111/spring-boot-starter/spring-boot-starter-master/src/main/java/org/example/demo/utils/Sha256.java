package org.example.demo.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256 {

//    public static void main(String[] args) throws NoSuchAlgorithmException {
//        String originalString = "123456";
//        String sha256string = getSha256String(originalString);
//        System.out.println(sha256string);
//    }

    public static String getSha256String(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash=digest.digest(data.getBytes(StandardCharsets.UTF_8));
        String sha256string = bytesToHex(encodedhash);
        return sha256string;
    }

    //turn byte[] into String
    //把byte[]格式转换成十六进制字符串String
    private static String bytesToHex(byte[] hash)
    {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1)
                { hexString.append('0'); }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
