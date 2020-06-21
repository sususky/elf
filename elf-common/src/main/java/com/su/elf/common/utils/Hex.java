package com.su.elf.common.utils;

/**
 * @author surongyao
 * @date 2018-05-24 14:48
 * @desc
 */
public class Hex {

    /**
     * 字节数组转为16进制字符串
     *
     * @param
     * @return
     * @throw Exception
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resuleSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            resuleSb.append(hex);
        }
        return resuleSb.toString();
    }

    /**
     * 16进制字符串转为字节数组
     *
     * @param s
     * @return
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return b;
    }

}
