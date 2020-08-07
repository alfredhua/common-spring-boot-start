package com.common.util;

import org.apache.commons.codec.binary.Hex;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @auth guozhenhua
 * @date 2018/11/09
 */

public class SignUtil {
    public static String sign(String sign){
        if(StringUtils.isEmpty(sign)){
            return null;
        }else {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(sign.getBytes());
                return Base64.getEncoder().encodeToString(new String(Hex.encodeHex(md.digest())).getBytes(StandardCharsets.UTF_8));
            }catch (Exception e){
                throw new RuntimeException("sign error",e);
            }
        }
    }

}