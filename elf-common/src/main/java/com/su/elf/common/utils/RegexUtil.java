package com.su.elf.common.utils;

import java.util.regex.Pattern;

/**
 * @author surongyao
 * @Type
 * @Desc 正则表达式校验
 * @date 2018-03-09 09:41
 */
public class RegexUtil {

    // 简体中文的正则表达式。
    private static final String REGEX_SIMPLE_CHINESE = "^[\u4E00-\u9FA5]+$";

    // 字母数字的正则表达式。
    private static final String REGEX_ALPHANUMERIC = "[a-zA-Z0-9]+";

    // 整数或浮点数的正则表达式。
    private static final String REGEX_NUMERIC = "(\\+|-){0,1}(\\d+)([.]?)(\\d*)";

    // 整数或浮点数的正则表达式。
    private static final String REGEX_INTEGER = "^[-\\+]?[\\d]+$";

    // 身份证号码的正则表达式。
    private static final String REGEX_ID_CARD = "(\\d{14}|\\d{17})(\\d|x|X)";

    // 电子邮箱的正则表达式。
    private static final String REGEX_EMAIL = ".+@.+\\.[a-z]+";

    // 是否ip地址
    private static final String REGEX_IP = "([1-9]|[1-9]\\\\d|1\\\\d{2}|2[0-4]\\\\d|25[0-5])(\\\\.(\\\\d|[1-9]" +
            "\\\\d|1\\\\d{2}|2[0-4]\\\\d|25[0-5])){3}";

    // 校验手机号
    private static final String REGEX_PHONE = "^1[3|4|5|8][0-9]\\d{8}$";

    // URL链接
    private static final String REGEX_URL = "^(f|ht){1}(tp|tps):\\\\/\\\\/([\\\\w-]+\\\\.)+[\\\\w-]+" +
            "(\\\\/[\\\\w ./?%&=]*)?";



    /**
     * 判断字符串是否匹配了正则表达式。
     *
     * @param str   字符串
     * @param regex 正则表达式
     * @return true/false
     */
    public static boolean isRegexMatch(String str, String regex) {
        return str != null && Pattern.compile(regex).matcher(str).matches();
    }

    /**
     * 判断输入的字符串是否符合Email格式.
     * @param email 传入的字符串
     * @return 符合Email格式返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return isRegexMatch(email, REGEX_EMAIL);
    }

    /**
     * 判断输入的字符串是否为纯汉字
     * @param value 传入的字符串
     * @return
     */
    public static boolean isChinese(String value) {
        return isRegexMatch(value, REGEX_SIMPLE_CHINESE);
    }

    /**
     * 判断是否为浮点数，包括double和float
     * @param value 传入的字符串
     * @return
     */
    public static boolean isDouble(String value) {
        return isRegexMatch(value, REGEX_NUMERIC);
    }

    /**
     * 判断是否为整数
     * @param value 传入的字符串
     * @return
     */
    public static boolean isInteger(String value) {
        return isRegexMatch(value, REGEX_INTEGER);
    }

    /**
     * 判断是否为手机号
     * @param value 传入的字符串
     * @return
     */
    public static boolean isMobileNumber(String value) {
        return isRegexMatch(value, REGEX_PHONE);
    }

    /**
     * 判断是否为数字或字母
     * @param value 传入的字符串
     * @return
     */
    public static boolean isCharOrNumber(String value) {
        return isRegexMatch(value, REGEX_ALPHANUMERIC);
    }

    /**
     * 判断是否为数字或字母
     * @param value 传入的字符串
     * @return
     */
    public static boolean isIP(String value) {
        return isRegexMatch(value, REGEX_IP);
    }

}
