package com.su.elf.common.exception;

/**
 * @author champion
 * @date 2020/6/10 11:16 上午
 * @desc
 **/
public class UtilException extends RuntimeException{

    private static final long serialVersionUID = 8247610319171014183L;

    public UtilException(String message) {
        super(message);
    }


    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
