package com.su.elf.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.elf.common.entity.ResponseMap;
import com.su.elf.common.entity.SearchParam;
import com.su.elf.system.service.user.UserService;
import com.su.elf.system.entity.User;
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
 * @date 2018/5/25 下午5:39
 * @version
 */
@RestController
@RequestMapping("/user")
public class UserController {

   // private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseMap getUserList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        List<User> list = userService.getList(param);
        int total = userService.getCount(param);
        JSONObject json = new JSONObject();
        json.put("count", total);
        json.put("list", list);
        return ResponseMap.ok(json);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseMap addUser(@RequestBody User user){
        int id = userService.insertPojo(user);
        JSONObject json = new JSONObject();
        json.put("id", id);
        return ResponseMap.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public ResponseMap deleteUser(@PathVariable int pid){
        int result = userService.deletePojo(pid);
        JSONObject json = new JSONObject();
        json.put("result", result);
        return ResponseMap.ok(json);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMap updateRole(@RequestBody User user){
        int result = userService.updatePojo(user);
        JSONObject json = new JSONObject();
        json.put("result", result);
        return ResponseMap.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public ResponseMap getUser(@PathVariable int pid){
        User user = userService.getPojo(pid);
        JSONObject json = new JSONObject();
        json.put("user", user);
        return ResponseMap.ok(json);
    }

}
