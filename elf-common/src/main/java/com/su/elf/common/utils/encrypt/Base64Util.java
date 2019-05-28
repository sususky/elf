package com.su.elf.common.utils.encrypt;


import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * BASE64加解密工具类
 *
 * @Author:cloud
 * @date:2017年8月31日
 */
public class Base64Util {

    /**
     * @param
     * @return
     * @throw Exception
     */
    public static String encode(String content){
        if(StringUtils.isEmpty(content)){
            return null;
        }
        try {
            return Base64.getEncoder().encodeToString(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(byte [] b){
        if(b == null){
            return null;
        }
        return Base64.getEncoder().encodeToString(b);
    }

    /**
     * @param
     * @return
     * @throw Exception
     */
    public static String decode2String(String content){
        if(StringUtils.isEmpty(content)){
            return null;
        }
        byte[] asBytes = Base64.getDecoder().decode(content);
        try {
            return new String(asBytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decode2Byte(String content){
        if(StringUtils.isEmpty(content)){
            return null;
        }
        return Base64.getDecoder().decode(content);
    }


}
