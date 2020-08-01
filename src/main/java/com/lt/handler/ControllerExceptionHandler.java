package com.lt.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义异常类
 */
@ControllerAdvice //会拦截所以带所有@Controller注解的类
public class ControllerExceptionHandler {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class) //标识做异常处理的注解 Excepiton.class 会拦截
    public ModelAndView handleException(HttpServletRequest request,Exception e) throws Exception {
        logger.error("Request URL : {} , Exception : {}",request.getRequestURI(),e);
        //不拦截带状态码的异常，交给springboot处理
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        ModelAndView view=new ModelAndView();
        view.addObject("url",request.getRequestURI());
        view.addObject("exception",e);
        view.setViewName("error/error");   //error文件夹下的error文件

        return view;
    }

}
