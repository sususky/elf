package com.su.elf.auth.client.service.impl;

import com.su.elf.auth.client.service.IAuthService;
import com.su.elf.common.cache.CacheData;
import com.su.elf.common.redis.RedisDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.WeakHashMap;

/**
 * @author surongyao
 * @date 2019-05-28 16:58
 * @desc
 */
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private RedisDao redisDao;

    // 缓存token校验
    private WeakHashMap<String, CacheData> weakHashMap = new WeakHashMap<>();

    @Override
    public String fetchToken(HttpServletRequest request) {
        String token = request.getParameter("token");
        if(StringUtils.isEmpty(token)){
            // 如果没有cookie, 从header里取
            token = request.getHeader("token");
        }
        if(StringUtils.isEmpty(token)){
            Cookie[] cookies = request.getCookies();
            if(cookies!=null && cookies.length>0) {
                for (Cookie ck : cookies) {
                    if (ck.getName().equals("token")) {
                        token = ck.getValue();
                        break;
                    }
                }
            }
        }

        return token;
    }

    @Override
    public boolean ignoreAuth(String url) {
        return false;
    }

    @Override
    public boolean isValidToken(String token) {
        return false;
    }

    @Override
    public boolean hasPermission(String token, String url) {
        return false;
    }

    @Override
    public List<String> getPrivileges(String token) {
        return null;
    }

    @Override
    public boolean isSuperAdmin(String token) {
        if(StringUtils.isNotEmpty(token)){
            String isSuper = redisDao.hget(token, "is_super");
            if(StringUtils.isNotEmpty(isSuper) && isSuper.equals("1")){
                if(log.isDebugEnabled()){
                    log.debug("token:{}, 用户是超管", token);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReadOnly(String token) {
        String readOnly = redisDao.hget(token, "readOnly");
        if(StringUtils.isNotEmpty(readOnly) && readOnly.equals("1")){
            if(log.isDebugEnabled()){
                log.debug("token:{}, 只读用户", token);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getUserAccount(String token) {
        if(StringUtils.isNotEmpty(token)){
            String user = redisDao.hget(token, "userAccount");
            if(StringUtils.isNotEmpty(user)){
                return user;
            }
        }
        return null;
    }

}
