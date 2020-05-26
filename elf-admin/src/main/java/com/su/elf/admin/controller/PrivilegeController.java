package com.su.elf.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.su.elf.admin.service.privilege.PrivilegeService;
import com.su.elf.common.entity.ResponseMessage;
import com.su.elf.common.entity.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:40
 * @version
 */
@RestController
@RequestMapping("/privilege")
public class PrivilegeController {

    //private static final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    @Autowired
    private PrivilegeService privilegeService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseMessage getPrivilegeList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        JSONObject json = privilegeService.getList(param);
        return ResponseMessage.ok(json.getJSONArray("list")).put("count", json.getInteger("count"));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage addPrivilege(@RequestBody JSONObject privilege){
        JSONObject jsonObject = privilegeService.insertPojo(privilege);
        return ResponseMessage.ok(jsonObject);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public ResponseMessage deletePrivilege(@PathVariable int pid){
        JSONObject json = privilegeService.deletePojo(pid);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updatePrivilege(@RequestBody JSONObject privilege){
        JSONObject json = privilegeService.updatePojo(privilege);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public ResponseMessage getPrivilege(@PathVariable int pid){
        JSONObject json = privilegeService.getPojo(pid);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    public ResponseMessage getPrivilegeListByRole(@PathVariable int roleId){
        JSONArray list = new JSONArray();
        SearchParam param = new SearchParam();
        param.setLimit(0);
        JSONObject json = privilegeService.getList(param);
        if(json!=null){
            JSONArray all = json.getJSONArray("list");
            List<String> own = privilegeService.getPrivilegeByRoleId(roleId);
            if(all!=null){
                JSONObject o;
                for(int i=0;i<all.size();i++){
                    o = all.getJSONObject(i);
                    if(own!=null && own.contains(o.getString("link"))){
                        o.put("checked", true);
                    }else{
                        o.put("checked", false);
                    }

                    o.put("name", o.getString("privilegeName"));
                    o.put("pId", o.getInteger("parentId"));
                    o.put("open", true);
                    o.remove("privilegeName");
                    o.remove("parentId");
                    o.remove("createTime");
                    o.remove("seq");
                    o.remove("hasChild");
                    o.remove("category");
                    o.remove("subprivileges");
                    o.remove("link");
                    o.remove("parentName");
                    list.add(o);
                }
            }
        }

        return ResponseMessage.ok(list);
    }

}
