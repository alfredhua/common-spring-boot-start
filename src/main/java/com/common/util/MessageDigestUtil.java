package com.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MessageDigestUtil {


    public static final String ENCODING = "UTF-8";


    public static String base64AndMD5(String str) throws UnsupportedEncodingException {
        if (str == null) {
            throw new IllegalArgumentException("inStr can not be null");
        }
        return base64AndMD5(toBytes(str));
    }

    public static String resetPassword() throws Exception{
        return base64AndMD5("000000");
    }


    private static String base64AndMD5(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes can not be null");
        }
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(bytes);
            return Base64.getEncoder().encodeToString(md.digest());
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("unknown algorithm MD5");
        }
    }

     static byte[] toBytes(final String str) throws UnsupportedEncodingException {
            return  str == null? null:str.getBytes(ENCODING);
    }

}
