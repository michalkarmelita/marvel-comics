package com.michalkarmelita.marvelcomics.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    private static String convertToString(byte[] data) {
        StringBuilder builder = new StringBuilder();
        for (byte aData : data) {
            builder.append(Integer.toString((aData & 0xff) + 0x100, 16).substring(1));
        }
        return builder.toString();
    }

    public static String generateMd5Hash(String text)  {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("Md5");
            byte[] md5hash;
            md.update(text.getBytes(), 0, text.length());
            md5hash = md.digest();
            return convertToString(md5hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
