package com.su.elf.admin.service.privilege.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.su.elf.admin.entity.Privilege;
import com.su.elf.admin.service.privilege.PrivilegeService;
import com.su.elf.admin.service.rest.RestService;
import com.su.elf.common.entity.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:32
 * @version
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private RestService restService;

    @Override
    public List<String> getPrivilegeByRoleId(int roleId) {

        JSONObject json = restService.get("http://system/privilege/role/" + roleId);
        if(json!=null){
            JSONArray array = json.getJSONArray("list");
            if(array!=null && array.size()>0){
                List<String> list = new ArrayList<>();
                for(int i=0;i<array.size();i++){
                    list.add(array.getJSONObject(i).getString("link"));
                }
                return list;
            }
        }
        return null;
    }

    @Override
    public List<Privilege> getPrivileges() {
        JSONObject json = getList(null);
        if(json!=null){
            JSONArray array = json.getJSONArray("list");
            if(array!=null && array.size()>0){
                Gson gson = new Gson();
                List<Privilege> list = gson.fromJson(array.toJSONString(),
                        new TypeToken<List<Privilege>>() {}.getType());

                return list;
            }

        }
        return null;
    }

    @Override
    public JSONObject getList(SearchParam params) {
        return restService.get("http://system/privilege?" + params.toString());
    }


    @Override
    public JSONObject getPojo(int id) {
        return restService.get("http://system/privilege/" + id);
    }

    @Override
    public JSONObject insertPojo(JSONObject pojo) {
        return restService.post("http://system/privilege", pojo.toJSONString());
    }

    @Override
    public JSONObject updatePojo(JSONObject pojo) {
        return restService.exchange("http://system/privilege", HttpMethod.PUT, pojo.toJSONString());
    }

    @Override
    public JSONObject deletePojo(int id) {
        return restService.exchange("http://system/privilege/" + id, HttpMethod.DELETE, id+"");
    }

}
