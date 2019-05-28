package com.su.elf.admin.config;

import com.su.elf.common.utils.encrypt.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Desc
 * @author surongyao
 * @date 2018/7/12 下午4:13
 * @version
 */
@ControllerAdvice(basePackages = "com.su.admin.controller")
public class ResponseBodyEncAdv implements ResponseBodyAdvice {

    private final static Logger logger = LoggerFactory.getLogger(ResponseBodyEncAdv.class);

    @Value("${app.ioEncryptOpen}")
    private Integer ioEncryptOpen;

    @Value("${app.aesKey}")
    private String key;


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if(ioEncryptOpen!=null && ioEncryptOpen==1){
            return true;
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                            MediaType mediaType, Class aClass,
                                            ServerHttpRequest serverHttpRequest,
                                            ServerHttpResponse serverHttpResponse) {
        String encryptContent = AESUtil.encrypt(o.toString(), key);
        if(logger.isInfoEnabled()){
            logger.info("返回结果:[{}]， 加密后:[{}]", o.toString(), encryptContent);
        }
        return encryptContent;
    }
}
