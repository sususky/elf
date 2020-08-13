package com.su.elf.common.entity;

import com.alibaba.fastjson.JSONObject;
import com.su.elf.common.CodeEnum;

import java.util.HashMap;

/**
 * 返回结果对象
 *
 * @author champion
 * @date 2017-6-10 上午10:10:07
 */
public class ResponseMap extends HashMap<String, Object> {

    /**
     * 返回成功
     */
    public static ResponseMap ok() {
        return msg(0, "success");
    }

    /**
     * 返回成功
     */
    public static ResponseMap ok(String message) {
        return msg(0, message);
    }


    /**
     * 返回成功
     */
    public static ResponseMap ok(int code, String message) {
        return msg(code, message);
    }


    /**
     * 返回成功
     */
    public static ResponseMap ok(JSONObject data) {
        ResponseMap map = ok();
        return map.data(data);
    }

    /**
     * 返回失败
     */
    public static ResponseMap error() {
        return msg(-1,"error");
    }
    
    /**
     * 返回失败
     */
    public static ResponseMap error(String message) {
        return msg(-1, message);
    }

    /**
     * 返回失败
     */
    public static ResponseMap error(CodeEnum codeEnum) {
        return msg(codeEnum.getCode(), codeEnum.getMsg());
    }

    /**
     * 返回失败
     */
    public static ResponseMap error(int code, String message) {
        return msg(code, message);
    }


    /**
     * 返回信息
     */
    private static ResponseMap msg(int code, String message) {
        ResponseMap map = new ResponseMap();
        map.put("code", code);
        map.put("message", message);
        return map;
    }

    public ResponseMap data(JSONObject json) {
        super.put("data", json);
        return this;
    }

    public ResponseMap put(String key, Object value){
        super.put(key, value);
        return this;
    }

}
