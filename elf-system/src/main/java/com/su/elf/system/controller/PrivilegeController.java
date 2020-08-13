package com.su.elf.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.elf.common.entity.ResponseMap;
import com.su.elf.common.entity.SearchParam;
import com.su.elf.system.entity.Privilege;
import com.su.elf.system.service.privilege.PrivilegeService;

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
    public ResponseMap getPrivilegeList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        List<Privilege> list = privilegeService.getList(param);
        int total = privilegeService.getCount(param);
        JSONObject json = new JSONObject();
        json.put("count", total);
        json.put("list", list);
        return ResponseMap.ok(json);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseMap addPrivilege(@RequestBody Privilege privilege){
        int id = privilegeService.insertPojo(privilege);
        JSONObject json = new JSONObject();
        json.put("id", id);
        return ResponseMap.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public ResponseMap deletePrivilege(@PathVariable int pid){
        int result = privilegeService.deletePojo(pid);
        JSONObject json = new JSONObject();
        json.put("result", result);
        return ResponseMap.ok(json);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMap updatePrivilege(@RequestBody Privilege privilege){
        int result = privilegeService.updatePojo(privilege);
        JSONObject json = new JSONObject();
        json.put("result", result);
        return ResponseMap.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public ResponseMap getPrivilege(@PathVariable int pid){
        Privilege privilege = privilegeService.getPojo(pid);
        JSONObject json = new JSONObject();
        json.put("privilege", privilege);
        return ResponseMap.ok(json);
    }

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    public ResponseMap getPrivilegeListByRole(@PathVariable int roleId){
        List<Privilege> list = privilegeService.getPrivilegeByRoleId(roleId);
        JSONObject json = new JSONObject();
        json.put("list", list);
        return ResponseMap.ok(json);
    }

}
