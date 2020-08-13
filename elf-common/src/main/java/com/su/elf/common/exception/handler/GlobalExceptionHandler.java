package com.su.elf.common.exception.handler;

import com.su.elf.common.CodeEnum;
import com.su.elf.common.Constants;
import com.su.elf.common.entity.ResponseMap;
import com.su.elf.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseMap Handle(Exception e){

        if (e instanceof ApiException){
            log.error(e.getMessage());
            ApiException exception = (ApiException) e;
            return ResponseMap.error(exception.getErrorCode(), exception.getMessage());
        } else if(e instanceof ResourceAccessException){
            log.error(e.getMessage());
            return ResponseMap.error(Constants.SERVER_ERROR, "服务调用失败");
        } else if (e instanceof HttpRequestMethodNotSupportedException){
            log.error(e.getMessage());
            return ResponseMap.error(CodeEnum.METHOD_NOT_SUPPORTED);
        } else {
            //将系统异常以打印出来
            log.error(e.getMessage(), e);
            return ResponseMap.error(Constants.SERVER_ERROR, "内部错误");
        }

    }

}
