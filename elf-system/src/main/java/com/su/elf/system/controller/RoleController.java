package com.su.elf.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.elf.common.CodeEnum;
import com.su.elf.common.entity.ResponseMap;
import com.su.elf.common.entity.SearchParam;
import com.su.elf.system.entity.Role;
import com.su.elf.system.service.role.RoleService;
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
@RequestMapping("/role")
public class RoleController {

    //private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseMap getRoleList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        List<Role> list = roleService.getList(param);
        int total = roleService.getCount(param);
        JSONObject json = new JSONObject();
        json.put("count", total);
        json.put("list", list);
        return ResponseMap.success(json);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseMap addRole(@RequestBody Role role){
        int id = roleService.insertPojo(role);
        JSONObject json = new JSONObject();
        json.put("id", id);
        return ResponseMap.success(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public ResponseMap deleteRole(@PathVariable int pid){
        int result = roleService.deletePojo(pid);
        JSONObject json = new JSONObject();
        json.put("result", result);
        return ResponseMap.success(json);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMap updateRole(@RequestBody Role role){
        int result = roleService.updatePojo(role);
        JSONObject json = new JSONObject();
        json.put("result", result);
        return ResponseMap.success(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public ResponseMap getRole(@PathVariable int pid){
        Role role = roleService.getPojo(pid);
        JSONObject json = new JSONObject();
        json.put("role", role);
        return ResponseMap.success(json);
    }

    @RequestMapping(value = "/{roleId}/privilege", method = RequestMethod.POST)
    public ResponseMap updateRolePrivilege(@PathVariable int roleId,
                                               @RequestBody List<Integer> privilegeIds){
        System.out.println(roleId);
        System.out.println(privilegeIds);
        if(roleId==0){
            return ResponseMap.failed(CodeEnum.EMPTY_PARAM);
        }
        int result = roleService.updateRolePrivilege(roleId, privilegeIds);
        JSONObject json = new JSONObject();
        json.put("result", result);
        return ResponseMap.success(json);
    }

}
