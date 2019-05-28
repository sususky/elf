package com.su.elf.common.utils.encrypt;

import com.su.elf.common.utils.Hex;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 *
 * @Author: cloud
 * @date: 2017年7月6日
 */
public class MD5Util {

    private final static String MD5_KEY = "MD5";

    private final static String SHA_KEY = "SHA1";

    public static String encrypt(String value){
        return encrypt(value, MD5_KEY);
    }

    public static String encrypt(String value, String key) {
        if(StringUtils.isEmpty(key)){
            key = MD5_KEY;
        }
        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance(key);
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = value.getBytes();
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            // 字符数组转换成字符串返回
            return Hex.byteArrayToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
