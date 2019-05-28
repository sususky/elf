package com.su.elf.common.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.su.elf.common.CodeEnum;

/**
 * 返回结果对象
 *
 * @author wangfan
 * @date 2017-6-10 上午10:10:07
 */
public class ResponseMessage extends JSONObject {

    /**
     * 返回成功
     */
    public static ResponseMessage ok() {
        return msg(0, "成功！", null);
    }

    /**
     * 返回成功
     */
    public static ResponseMessage ok(String message) {
        return msg(0, message, null);
    }

    /**
     * 返回成功
     */
    public static ResponseMessage ok(JSON data) {
        return msg(0, "成功！", data);
    }

    /**
     * 返回成功
     */
    public static ResponseMessage ok(int code, String message) {
        return msg(code, message, null);
    }


    /**
     * 返回失败
     */
    public static ResponseMessage error() {
        return msg(-1,"失败！", null);
    }
    
    /**
     * 返回失败
     */
    public static ResponseMessage error(String message) {
        return msg(-1, message, null);
    }

    /**
     * 返回失败
     */
    public static ResponseMessage error(CodeEnum codeEnum) {
        return msg(codeEnum.getCode(),codeEnum.getMsg(), null);
    }

    /**
     * 返回失败
     */
    public static ResponseMessage error(int code, String message) {
        return msg(code, message, null);
    }


    /**
     * 返回信息
     */
    public static ResponseMessage msg(int code, String message, JSON data) {
        ResponseMessage json = new ResponseMessage();
        json.put("code", code);
        json.put("msg", message);
        json.put("data", data);
        return json;
    }

    @Override
    public ResponseMessage put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
