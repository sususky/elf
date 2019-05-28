package com.su.elf.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.su.elf.admin.service.user.UserService;
import com.su.elf.common.entity.ResponseMessage;
import com.su.elf.common.entity.SearchParam;
import com.su.elf.common.exception.CommonException;
import com.su.sso.entity.SsoUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:39
 * @version
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;



    @RequestMapping("/psw")
    public ResponseMessage changePwd(HttpServletRequest request, String oldPsw, String newPsw){
        String token = null;//authService.fetchToken(request);
        String account = null;//authService.getUserAccount(token);
        if(StringUtils.isAnyEmpty(oldPsw, newPsw)){
            throw new IllegalArgumentException("参数为空");
        }
        SsoUser user = userService.getByName(account);
        JSONObject result;
        if(user!=null){
            if(oldPsw.equalsIgnoreCase(user.getPassword())){
                user.setPassword(newPsw);
                JSONObject jsonObject = new JSONObject();
                //jsonObject.put("id", user.getId());
                jsonObject.put("password", newPsw);
                result = userService.updatePojo(jsonObject);
            }else{
                throw new CommonException("旧密码错误");
            }
        }else{
            throw new CommonException("用户不存在");
        }
        logger.info("用户[{}]，修改密码，旧密码[{}]、新密码[{}]", account, oldPsw, newPsw);

        return ResponseMessage.ok(result);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseMessage getUserList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        JSONObject json = userService.getList(param);
        JSONArray array = json.getJSONArray("list");
        for(int i=0;i<array.size();i++){
            array.getJSONObject(i).remove("password");
        }
        return ResponseMessage.ok(array).put("count", json.getInteger("count"));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage addUser(@RequestBody JSONObject user){
        //todo 校验参数

        JSONObject json = userService.insertPojo(user);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public ResponseMessage deleteUser(@PathVariable int pid){
        JSONObject json = userService.deletePojo(pid);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updateRole(@RequestBody JSONObject user){
        JSONObject json = userService.updatePojo(user);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public ResponseMessage getUser(@PathVariable int pid){
        JSONObject json = userService.getPojo(pid);
        return ResponseMessage.ok(json);
    }

}
