package com.su.elf.admin.config;

import com.su.elf.common.utils.encrypt.AESUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @Desc 请求解密
 * @author surongyao
 * @date 2018/7/12 下午2:18
 * @version
 */
@ControllerAdvice(basePackages = "com.su.admin.controller")
public class RequestBodyDecAdv implements RequestBodyAdvice {

    private final static Logger logger = LoggerFactory.getLogger(RequestBodyDecAdv.class);
    @Value("${app.ioEncryptOpen}")
    private Integer ioEncryptOpen;

    @Value("${app.aesKey}")
    private String key;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type,
                                      Class<? extends HttpMessageConverter<?>> aClass) {
        if(ioEncryptOpen!=null && ioEncryptOpen==1){
            return true;
        }
        return false;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage,
                                                     MethodParameter methodParameter, Type type,
                                                     Class<? extends HttpMessageConverter<?>> aClass)
            throws IOException {

        try {
            return new DecryptHttpInputMessage(httpInputMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return httpInputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage,
                                          MethodParameter methodParameter, Type type,
                                          Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage,
                                            MethodParameter methodParameter, Type type,
                                            Class<? extends HttpMessageConverter<?>> aClass) {
        return httpInputMessage;
    }


    class DecryptHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;

        public DecryptHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            key = key!=null?key:"123";

            String encryptContent = IOUtils.toString(inputMessage.getBody(), "UTF-8");
            String decryptContent = AESUtil.decrypt(encryptContent, key);
            if(logger.isInfoEnabled()){
                logger.info("加密报文:[{}], 解密报文:[{}]", encryptContent, decryptContent);
            }
            this.body = IOUtils.toInputStream(decryptContent, "UTF-8");
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }

}
