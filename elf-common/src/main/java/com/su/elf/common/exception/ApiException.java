package com.su.elf.common.exception;

import com.su.elf.common.CodeEnum;

public class ApiException extends RuntimeException {

    /**
     * 错误编码
     */
    private int errorCode;

    /**
     * 构造一个基本异常.
     *
     * @param message  信息描述
     * @param cause  根异常类（可以存入任何异常）
     */
    public ApiException(String message, Throwable cause){
        super(message, cause);
    }

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
     * @param codeEnum
     */
    public ApiException(CodeEnum codeEnum) {
        super(codeEnum.getMsg());
        setErrorCode(codeEnum.getCode());
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
