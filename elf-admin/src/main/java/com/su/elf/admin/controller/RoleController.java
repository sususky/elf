package com.su.elf.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.elf.admin.service.role.RoleService;
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
@RequestMapping("/role")
public class RoleController {

    //private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseMessage getRoleList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        JSONObject json = roleService.getList(param);
        return ResponseMessage.ok(json.getJSONArray("list")).put("count", json.getInteger("count"));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage addRole(@RequestBody JSONObject role){
        JSONObject json = roleService.insertPojo(role);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public ResponseMessage deleteRole(@PathVariable int pid){
        JSONObject json = roleService.deletePojo(pid);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updateRole(@RequestBody JSONObject role){
        JSONObject json = roleService.updatePojo(role);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public ResponseMessage getRole(@PathVariable int pid){
        JSONObject json = roleService.getPojo(pid);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{roleId}/privilege", method = RequestMethod.POST)
    public ResponseMessage updateRolePrivilege(@PathVariable int roleId,
                                               @RequestBody List<Integer> privilegeIds){
        JSONObject json = roleService.updateRolePrivilege(roleId, privilegeIds);
        return ResponseMessage.ok(json);
    }

}
