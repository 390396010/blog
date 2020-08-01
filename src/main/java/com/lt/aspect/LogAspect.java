package com.lt.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 *      进行日志处理
 */

@Aspect  //表示当前类是一个切面类
@Component
public class LogAspect {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.lt.web.*.*(..))")
    private void log(){}                  //切面的方法

    //* 前置通知
    @Before("log()")   //里面写切面
    public void doBefore(JoinPoint joinPoint){
        //通过request获取路径和请求
        ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //通过切面的类 获取 方法 和内容
        String classMethod=joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        ReqeustLog reqeustLog=new ReqeustLog(url,ip,classMethod,args);
        logger.info("Rquest  ----- {}",reqeustLog);
    }

    //最终通知
    @After("log()")
    public void doAfter() {
        logger.info("-----doAfter-----");

    }
    //后置通知  捕获用户返回值的内容
    @AfterReturning(returning = "result",pointcut = "log()")  //returning返回什么  pointcut切面
    public void doAtfertRturning(Object result){
        //捕获各个方法返回的内容
        logger.info("Result : {}",result);
    }

    //内部类  用于记录日志内容
    private class ReqeustLog {
        private String url;   //请求
        private String ip;   //访问者
        private String classMethod;  //调用方法
        private Object[] args;   //参数

        public ReqeustLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "ReqeustLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
