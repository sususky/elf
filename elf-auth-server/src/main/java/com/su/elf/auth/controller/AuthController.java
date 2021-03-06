package com.su.elf.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.netflix.discovery.converters.Auto;
import com.su.elf.auth.client.config.JwtProperties;
import com.su.elf.auth.client.entity.AuthUser;
import com.su.elf.auth.client.jwt.JwtTokenUtil;
import com.su.elf.auth.config.RsaProperties;
import com.su.elf.auth.service.OnlineUserService;
import com.su.elf.auth.service.UserService;
import com.su.elf.common.CodeEnum;
import com.su.elf.common.entity.ResponseMessage;
import com.su.elf.common.redis.RedisDao;
import com.su.elf.common.utils.RegexUtil;
import com.su.elf.common.utils.encrypt.RSAUtil;
import com.su.elf.logging.annotation.LogRecord;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author surongyao
 * @date 2019-01-10 10:55
 * @desc
 */
@Api(tags = "系统：系统授权接口")
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String CAPTCHA_KEY_PREFIX = "captcha:";

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private OnlineUserService onlineUserService;


    @ApiOperation("获取验证码")
    @RequestMapping("/captcha")
    public ResponseMessage captcha() throws Exception{
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(200, 60);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        String uuid = CAPTCHA_KEY_PREFIX + UUID.randomUUID().toString().replace("-", "");
        // 保存
        redisDao.set(uuid, result, jwtProperties.getCodeExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        JSONObject imgResult = new JSONObject(2){{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};

        return ResponseMessage.ok(imgResult);
    }

    @LogRecord("用户登录")
    @ApiOperation("登录授权")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseMessage login(HttpServletRequest request, @RequestBody JSONObject args){
        if(args==null || args.isEmpty()){
            return ResponseMessage.error(CodeEnum.EMPTY_PARAM);
        }
        if(jwtProperties.getCaptchaOpen()!=null && jwtProperties.getCaptchaOpen()==1){
            String uuid = args.getString("uuid");
            String captcha = args.getString("captcha");
            String text = null; //(String) session.getAttribute("verifyCode");
            if(StringUtils.isAnyEmpty(uuid, captcha)){
                return ResponseMessage.error(CodeEnum.EMPTY_PARAM);
            }else{
                text = redisDao.get(CAPTCHA_KEY_PREFIX + uuid);
                // 清除验证码
                redisDao.del(CAPTCHA_KEY_PREFIX + uuid);
            }

            if(StringUtils.isEmpty(text) || !text.equalsIgnoreCase(captcha)){
                return ResponseMessage.error(CodeEnum.ILLEGAL_PARAM.getCode(), "验证码不正确");
            }
        }

        String username = args.getString("username");
        String password = args.getString("password");
        if(StringUtils.isAnyEmpty(username, password)){
            return ResponseMessage.error(CodeEnum.EMPTY_PARAM);
        }

        //校验用户名密码
        if(!RegexUtil.isRegexMatch(username, "^[A-Za-z0-9@#$-_.]{1,64}$")){
            return ResponseMessage.error(CodeEnum.ILLEGAL_PARAM.getCode(), "用户名格式不合法");
        }

        // 密码解密
        try {
            password = RSAUtil.decryptByPrivateKey(RsaProperties.privateKey, password);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        AuthUser user = userService.loadUserByUsername(username);
        if(user!=null){
            if(StringUtils.isNotEmpty(user.getPassword()) && bcryptPasswordEncoder.matches(password, user.getPassword())){
                // 生成令牌
                String token = jwtTokenUtil.createToken(user);
                // 保存在线信息
                onlineUserService.save(user, token, request);
                // 返回 token 与 用户信息
                JSONObject json = new JSONObject();
                json.put("token", token);
                json.put("user", user);
                return ResponseMessage.ok(json);
            }else{
                return ResponseMessage.error(CodeEnum.ILLEGAL_PARAM.getCode(), "密码错误");
            }
        }else{
            return ResponseMessage.error(CodeEnum.NO_USER.getCode(), "用户不存在");
        }


    }

    @ApiOperation("退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public ResponseMessage logout(HttpServletRequest request){
        onlineUserService.logout(request.getHeader("token"));
        return ResponseMessage.ok();
    }

//    @RequestMapping("/oauth/logout")
//    public ResponseMessage logout(HttpServletRequest request) {
//        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String token = request.getParameter("access_token");
//        if (StringUtils.isNotEmpty(token)) {
//            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
//            if (oAuth2AccessToken != null) {
//                tokenStore.removeAccessToken(oAuth2AccessToken);
//                if (logger.isInfoEnabled()) {
//                    logger.info("user logout, token:[{}] is invalid", token);
//                }
//            }
//        }
//
//        return ResponseMessage.ok();
//    }


}
