package com.su.elf.admin.interceptor;

import com.su.elf.common.CodeEnum;
import com.su.elf.common.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Desc
 * @author surongyao
 * @date 2018/7/12 下午4:38
 * @version
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Value("${auth.excludes}")
    String excludes;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                       Object handler) throws Exception {

        // 跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, "
                + "x-requested-with, token");
        //response.setHeader("Access-Control-Max-Age", "60");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String uri = request.getServletPath();

        if("/".equals(uri)){
            return true;
        }

        if(request.getMethod().equalsIgnoreCase("options")){
            return true;
        }

        if(StringUtils.isNotEmpty(excludes)){
            String [] excludeArray = excludes.split(",");
            String [] uris = uri.split("/");
            for(String exclude:excludeArray){
                if(exclude.trim().equals(uris[1]) ){
                    return true;
                }
            }
        }

        boolean flag = true;

        if(flag){
            logger.info("uri: [{}]校验通过", uri);
            return true;
        }
        logger.warn("uri: [{}]校验失败", uri);
        throw new CommonException(CodeEnum.NO_PERMISSION);

        //return true;
    }
}
