package com.su.elf.admin.service.rest;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;

/**
 * @Desc
 * @author surongyao
 * @date 2018/7/25 下午3:11
 * @version
 */
public interface RestService {

    JSONObject get(String url);

    JSONObject post(String url, String param);

    /**
     * 发送/获取 服务端数据(主要用于解决发送put,delete方法无返回值问题).
     *
     * @param url      绝对地址
     * @param method   请求方式
     * @return 返回结果(响应体)
     */
    JSONObject exchange(String url, HttpMethod method, String param);

}
