package com.su.elf.admin.interceptor;

import com.su.elf.common.annotation.AccessLimit;
import com.su.elf.common.redis.RedisDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @author surongyao
 * @date 2018/7/12 下午4:38
 * @version
 */
@Slf4j
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {

    private static final String ACCESS_LIMIT_KEY_PREX = "accecc_limit:";

    @Autowired
    RedisDao redisDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                       Object handler) throws Exception {
        String uri = request.getServletPath();
        String clientIp = request.getRemoteHost();
        // 使用注解
        if (handler instanceof HandlerMethod){
            AccessLimit accessLimit = ((HandlerMethod) handler).getMethodAnnotation(AccessLimit.class);
            if (accessLimit != null) {
                // 多少秒内
                int seconds = accessLimit.seconds();
                // 允许访问最大次数
                int maxCount = accessLimit.maxCount();

                StringBuilder sb = new StringBuilder();
                sb.append(ACCESS_LIMIT_KEY_PREX).append(request.getSession().getId()).append(":");
                sb.append(uri.replace("/", "."));
                String key = sb.toString();
                long count = redisDao.increase(key, 1);
                if (count == 1) {
                    redisDao.expire(key, seconds, TimeUnit.SECONDS);
                }
                if (count > maxCount) {
                    log.warn("用户IP[" + clientIp + "]访问地址[" + uri + "]超过了限定的次数["
                            + maxCount + "]");
                    // 超过次数，权限拒绝访问，访问太频繁！
                    return false;
                }else{
                    redisDao.increase(key, 1);
                }
            }
        }

        return true;
    }

}
