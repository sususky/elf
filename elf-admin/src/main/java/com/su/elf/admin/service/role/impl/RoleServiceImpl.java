package com.su.elf.admin.service.role.impl;

import com.alibaba.fastjson.JSONObject;
import com.su.elf.admin.service.rest.RestService;
import com.su.elf.admin.service.role.RoleService;
import com.su.elf.common.entity.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:35
 * @version
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RestService restService;


    @Override
    public JSONObject getList(SearchParam params) {
        return restService.get("http://system/role?" + params.toString());
    }


    @Override
    public JSONObject getPojo(int id) {
        return restService.get("http://system/role/" + id);
    }

    @Override
    public JSONObject insertPojo(JSONObject pojo) {
        return restService.post("http://system/role", pojo.toJSONString());
    }

    @Override
    public JSONObject updatePojo(JSONObject pojo) {
        return restService.exchange("http://system/role", HttpMethod.PUT, pojo.toJSONString());
    }

    @Override
    public JSONObject deletePojo(int id) {
        return restService.exchange("http://system/role/" + id, HttpMethod.DELETE, id+"");
    }

    @Override
    public JSONObject updateRolePrivilege(int roleId, List<Integer> list) {
        return restService.post("http://system/role/" + roleId + "/privilege", list.toString());
    }
}
