package com.su.elf.common;

/**
 * @Desc
 * @author surongyao
 * @date 2018/8/23 上午11:21
 * @version
 */
public enum CodeEnum {

    SUCCESS(0, "成功"),
    FAILED(-1, "失败"),

    ILLEGAL_PARAM(100, "参数非法"),
    EMPTY_PARAM(101, "参数为空"),

    UN_AUTH(401, "未登录"),
    NO_USER(402, "用户不存在"),
    NO_PERMISSION(403, "没有权限"),

    SQL_ERROR(510, "数据库错误"),
    SQL_INDEX_CONFLICT(511, "主键冲突");

    private int code;
    private String msg;

    CodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
