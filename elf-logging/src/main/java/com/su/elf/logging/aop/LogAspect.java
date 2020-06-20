package com.su.elf.logging.aop;

import com.su.elf.logging.entity.ClientUserAgent;
import com.su.elf.logging.entity.Log;
import com.su.elf.logging.service.LogService;
import com.su.elf.logging.annotation.LogRecord;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author surongyao
 * @date 2020-06-02 17:31
 * @desc
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private final LogService logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.su.elf.logging.annotation.LogRecord)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());

        result = joinPoint.proceed();

        Log log = new Log();
        log.setLogType("info");
        log.setTime(System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setMethod(getMethod(joinPoint));
        log.setRequestParams(getParam(joinPoint));
        log.setOpt(getAnnotationDes(joinPoint));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ClientUserAgent agentGetter = new ClientUserAgent(request);
        log.setBrowser(agentGetter.getBrowser());
        log.setClientIp(agentGetter.getIpAddr());
        log.setUsername(getUsername());
        log.setOs(agentGetter.getOS());

        logService.save(log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Log log = new Log();
        log.setLogType("error");
        log.setExceptionDetail(e.getMessage());
        log.setTime(System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setMethod(getMethod(joinPoint));
        log.setRequestParams(getParam(joinPoint));
        log.setOpt(getAnnotationDes(joinPoint));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ClientUserAgent agentGetter = new ClientUserAgent(request);
        log.setBrowser(agentGetter.getBrowser());
        log.setClientIp(agentGetter.getIpAddr());
        log.setUsername(getUsername());
        log.setOs(agentGetter.getOS());

        logService.save(log);
    }

    private String getUsername() {
        try {
            return "user";
        }catch (Exception e){
            return "";
        }
    }

    private String getMethod(JoinPoint joinPoint) {
        StringBuilder method = new StringBuilder("");
        method.append(joinPoint.getTarget().getClass().getName());
        method.append(".").append(joinPoint.getSignature().getName());
        return method.toString();
    }

    private String getParam(JoinPoint joinPoint) {
        StringBuilder params = new StringBuilder("");
        Object [] args =  joinPoint.getArgs();
        if(args!=null){
            int j = 0;
            for(int i=0; i<args.length; i++) {
                if(args[i] != null) {
                    String className = args[i].getClass().getSimpleName();
                    // HttpServletRequest and HttpServletResponse not need
                    if(className.indexOf("JSONObject")>=0){
                        if(j > 0){
                            params.append(",");
                        }
                        params.append(":");
                        params.append(args[i]);
                        j = 1;
                    }
                }
            }
        }
        return params.toString();
    }

    private String getAnnotationDes(JoinPoint joinPoint){
        LogRecord lr = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(LogRecord.class);
        return lr.value();
    }

}
