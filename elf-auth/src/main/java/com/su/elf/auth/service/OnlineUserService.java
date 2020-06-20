package com.su.elf.auth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.su.elf.auth.entity.AuthUser;
import com.su.elf.auth.client.jwt.JwtProperties;
import com.su.elf.auth.server.entity.OnlineUser;
import com.su.elf.common.redis.RedisDao;
import com.su.elf.common.utils.PageUtil;
import com.su.elf.logging.entity.ClientUserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author surongyao
 * @date 2020-06-01 17:25
 * @desc
 */
@Slf4j
@Service
public class OnlineUserService {

    @Autowired
    private JwtProperties properties;
    @Autowired
    private RedisDao redisDao;

    public void save(AuthUser user, String token, HttpServletRequest request) {
        ClientUserAgent agentGetter = new ClientUserAgent(request);
        OnlineUser onlineUser = new OnlineUser();
        onlineUser.setUserName(user.getUsername());
        onlineUser.setBrowser(agentGetter.getBrowser());
        onlineUser.setIp(agentGetter.getIpAddr());
        onlineUser.setOs(agentGetter.getOS());
        onlineUser.setToken(token);
        onlineUser.setLoginTime(new Date());
        redisDao.set(properties.getOnlineKey() + token, JSON.toJSONString(onlineUser),
                properties.getTokenValidityInSeconds(), TimeUnit.SECONDS);
    }

    public void checkLoginOnUser(String userName, String igoreToken) {
        List<OnlineUser> onlineUsers = getAll(userName);
        if(onlineUsers ==null || onlineUsers.isEmpty()){
            return;
        }
        for(OnlineUser onlineUser : onlineUsers){
            if(onlineUser.getUserName().equals(userName)){
                try {
                    String token = onlineUser.getToken();
                    if(StringUtils.isNotBlank(igoreToken)&&!igoreToken.equals(token)){
                        this.kickOut(token);
                    }else if(StringUtils.isBlank(igoreToken)){
                        this.kickOut(token);
                    }
                } catch (Exception e) {
                    log.error("checkUser is error",e);
                }
            }
        }
    }

    public void kickOut(String key) {
        key = properties.getOnlineKey() + key;
        redisDao.del(key);
    }

    public void logout(String token) {
        String key = properties.getOnlineKey() + token;
        redisDao.del(key);
    }

    public OnlineUser getOne(String key) {
        String json = redisDao.get(key);
        return JSONObject.parseObject(json, OnlineUser.class);
    }

    public Map<String, Object> getAll(String filter, Pageable pageable) {
        List<OnlineUser> onlineUsers = getAll(filter);
        return PageUtil.toPage(
                PageUtil.toPage(pageable.getPageNumber(), pageable.getPageSize(), onlineUsers),
                onlineUsers.size()
        );
    }

    public void download(List<OnlineUser> all, HttpServletResponse response) throws IOException {

    }

    public List<OnlineUser> getAll(String filter){
        List<String> keys = redisDao.scan(properties.getOnlineKey() + "*");
        Collections.reverse(keys);
        List<OnlineUser> onlineUserDtos = new ArrayList<>();
        for (String key : keys) {
            OnlineUser onlineUser = getOne(key);
            if(StringUtils.isNotBlank(filter)){
                if(onlineUser.getUserName().contains(filter)){
                    onlineUserDtos.add(onlineUser);
                }
            } else {
                onlineUserDtos.add(onlineUser);
            }
        }
        onlineUserDtos.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineUserDtos;
    }

}
