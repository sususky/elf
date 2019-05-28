package com.su.elf.admin.service.log.impl;

import com.alibaba.fastjson.JSONObject;
import com.su.elf.admin.service.log.LogService;
import com.su.elf.admin.service.rest.RestService;
import com.su.elf.common.entity.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;


/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:32
 * @version
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private RestService restService;


    @Override
    public JSONObject getList(SearchParam params) {
        return restService.get("http://system/log?" + params.toString());
    }


    @Override
    public JSONObject getPojo(int id) {
        return restService.get("http://system/log/" + id);
    }

    @Override
    public JSONObject insertPojo(JSONObject pojo) {
        return restService.post("http://system/log", pojo.toJSONString());
    }

    @Override
    public JSONObject updatePojo(JSONObject pojo) {
        return restService.exchange("http://system/log", HttpMethod.PUT, pojo.toJSONString());
    }

    @Override
    public JSONObject deletePojo(int id) {
        return restService.exchange("http://system/log/" + id, HttpMethod.DELETE, id+"");
    }

}
