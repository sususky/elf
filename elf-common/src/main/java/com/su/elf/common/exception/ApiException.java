package com.su.elf.common.exception;

import com.su.elf.common.CodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {

    /**
     * 错误编码
     */
    private int errorCode;

    public ApiException(String message){
        super(message);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode 错误编码
     * @param message 信息描述
     */
    public ApiException(int errorCode, String message) {
        super(message);
        setErrorCode(errorCode);
    }

    /**
     * 构造一个基本异常.
     *
     * @param message  信息描述
     * @param cause  根异常类（可以存入任何异常）
     */
    public ApiException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * 构造一个基本异常.
     *
     * @param codeEnum
     */
    public ApiException(CodeEnum codeEnum) {
        super(codeEnum.getMsg());
        setErrorCode(codeEnum.getCode());
    }


}
