package com.su.sso.handler;

import com.su.common.Constants;
import com.su.common.entity.ResponseMessage;
import com.su.common.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SsoExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(SsoExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseMessage Handle(Exception e){

        if (e instanceof CommonException){
            logger.error(e.getMessage());
            CommonException exception = (CommonException) e;
            return ResponseMessage.error(exception.getErrorCode(), exception.getMessage());
        } else {
            //将系统异常以打印出来
            logger.error(e.getMessage(), e);
            return ResponseMessage.error(Constants.SERVER_ERROR, "服务器开小差了");
        }

    }

}
