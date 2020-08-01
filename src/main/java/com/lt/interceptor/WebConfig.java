package com.lt.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//使用WebMvcConfigurer可以扩展springmvc的功能
@Configuration //标注这是一个配置类
public class WebConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addInterceptor添加一个拦截器,addPathPatterns拦那些请求
        // 杠双星/**表示拦截任意多层路径下的任意请求
        //excludePathPatterns排序那些请求不拦截
        //静态资源自己不用设置过滤，springboot帮我们做好了
        //springboot已经做好了静态资源映射
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
