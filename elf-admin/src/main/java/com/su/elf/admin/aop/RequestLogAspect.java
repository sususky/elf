package com.su.elf.admin.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * aop 对输入和返回进行日志记录
 *
 * @author surongyao
 * @date 2018-05-28 19:33
 * @desc
 */
@Aspect
@Component
//@Order(2)
public class RequestLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);

    @Value("${app.aopLogOpen}")
    Integer isOpen;

    @Pointcut("execution(public * com.su.admin.controller.*.*(..))")
    public void addLog(){}


    @Around("addLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object o = null;

        if(isOpen !=null && isOpen==1){
            if(logger.isDebugEnabled()){
                logger.debug("AOP Around 获取日志开启");
            }
            if(logger.isInfoEnabled()){
                StringBuilder sb = new StringBuilder("请求入参: [");
                sb.append(pjp.getTarget().getClass().getName());
                sb.append(".").append(pjp.getSignature().getName());

                Object [] args =  pjp.getArgs();
                if(args!=null){
                    sb.append("(");
                    int j = 0;
                    for(int i=0; i<args.length; i++) {
                        if(args[i] != null) {
                            String className = args[i].getClass().getSimpleName();
                            // HttpServletRequest and HttpServletResponse not need
                            if(className.indexOf("HttpServletRequest")>=0
                                    || className.indexOf("HttpServletResponse")>=0
                                    || className.indexOf("ResponseFacade")>=0){
                                continue;
                            }
                            if(j > 0){
                                sb.append(",");
                            }
                            sb.append(className).append(":");
                            sb.append(args[i]);
                            j = 1;
                        }
                    }
                    sb.append(")]");
                }
                logger.info(sb.toString());
            }

            o = pjp.proceed();
            if(logger.isInfoEnabled()){
                if(o!=null){
                    logger.info("方法返回: [{}]", o.toString());
                }else{
                    logger.info("没有返回值");
                }

            }

        }else{
            if(logger.isDebugEnabled()){
                logger.debug("AOP Around 获取日志关闭");
            }
            o = pjp.proceed();
        }

        return o;
    }

}
