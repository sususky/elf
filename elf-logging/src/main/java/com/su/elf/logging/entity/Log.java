package com.su.elf.logging.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author surongyao
 * @date 2020-06-02 17:43
 * @desc
 */
@Getter
@Setter
public class Log {

    private Long id;

    /** 日志类型 */
    private String logType;

    /** 操作用户 */
    private String username;

    private String opt;

    /** 方法名 */
    private String method;

    /** 浏览器  */
    private String browser;

    /** 参数 */
    private String os;

    /** 请求ip */
    private String clientIp;

    /** 请求 */
    private String requestParams;

    /** 异常详细  */
    private String exceptionDetail;

    /** 请求耗时 */
    private long time;

    /** 创建日期 */
    private String createTime;
}
