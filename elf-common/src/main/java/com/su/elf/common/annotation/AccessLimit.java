package com.su.elf.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author surongyao
 * @date 2019-03-22 15:01
 * @desc
 */
//自定义限制注解,加载controller url 上
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    int seconds() default 2;  // 指定时间内（单位秒）
    int maxCount() default 1; // 最多访问多少次
}
