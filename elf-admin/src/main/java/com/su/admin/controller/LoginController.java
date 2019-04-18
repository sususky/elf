package com.su.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.admin.service.privilege.PrivilegeService;
import com.su.admin.service.user.UserService;
import com.su.common.CodeEnum;
import com.su.common.entity.ResponseMessage;
import com.su.common.redis.RedisDao;
import com.su.common.utils.CaptchaUtil;
import com.su.common.utils.RegexUtil;
import com.su.sso.entity.SsoUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;


    @Autowired
    PrivilegeService privilegeService;

    @Autowired
    private RedisDao redisDao;


    @RequestMapping("/captcha")
    public void captcha(HttpServletResponse response, String uuid)
            throws Exception{
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生产验证码字符串并保存到session中
        CaptchaUtil cu = new CaptchaUtil(200, 60, 5);
        String createText = cu.getCode();
        // request.getSession().setAttribute("verifyCode", createText);
        logger.info("生成验证码[{}]", createText);
        if(StringUtils.isNotEmpty(uuid)){
            redisDao.set(uuid, createText);
        }
        ServletOutputStream responseOutputStream = response.getOutputStream();
        cu.write(responseOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseMessage login(HttpServletRequest request, HttpServletResponse response, String account,
                        String password, String captcha, String uuid){

        String text = null; //(String) session.getAttribute("verifyCode");
        if(StringUtils.isNotEmpty(uuid)){
            text = redisDao.get(uuid);
        }

        if(StringUtils.isEmpty(text) || !text.equalsIgnoreCase(captcha)){
            return ResponseMessage.error(CodeEnum.ILLEGAL_PARAM.getCode(), "验证码不正确");
        }

        //校验用户名密码
        if(!RegexUtil.isRegexMatch(account, "^[A-Za-z0-9@#$-_.]{1,64}$")){
            return ResponseMessage.error(CodeEnum.ILLEGAL_PARAM.getCode(), "用户名格式不合法");
        }

        SsoUser user = userService.getByName(account);

        if(user!=null){
            if(!StringUtils.isAnyEmpty(password, user.getPassword())
                    && user.getPassword().equals(password)){
//                if(user.getIsSuper()!=1){
//                    List<String> list = privilegeService.getPrivilegeByRoleId(user.getRoleId());
//                    if(list!=null && list.size()>0){
//                        user.setPrivileges(list);
//                    }
//                }
                String token = null ;// authService.generateToken(request, user);
                Cookie cookie = new Cookie("token", token);
                cookie.setPath("/");
                cookie.setMaxAge(-1);
                response.addCookie(cookie);
                // 添加到登录日志
                userService.addLoginLog(request, account, "登录成功");

                JSONObject jsonObject = new JSONObject();
                //jsonObject.put("id", user.getId());
                jsonObject.put("token", token);
                user.setPassword(null);
                jsonObject.put("user", user);
                //jsonObject.put("privileges", user.getPrivaleges());
                return ResponseMessage.ok(jsonObject);//.put("token", "").put("user", user);
            }else{
                userService.addLoginLog(request, account, "密码错误");
                return ResponseMessage.error(CodeEnum.ILLEGAL_PARAM.getCode(), "密码错误");
            }
        }else{
            return ResponseMessage.error(CodeEnum.NO_USER.getCode(), "用户不存在");
        }

    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseMessage logout(HttpServletRequest request){
        String token = null; //authService.fetchToken(request);
        if(StringUtils.isNotEmpty(token)){
            //authService.expireToken(token);
        }

        return ResponseMessage.ok();
    }



}
