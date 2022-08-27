package com.cy.store.config;

import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**处理器拦截器的注册**/
@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {

    /**配置拦截器**/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建自定义的拦截器对象
        HandlerInterceptor interceptor=new LoginInterceptor();
        //配置白名单，存放在List集合
        List<String> patters=new ArrayList<>();
        patters.add("/bootstrap3/**");
        patters.add("/css/**");
        patters.add("/images/**");
        patters.add("/js/**");
        patters.add("/web/register.html");
        patters.add("/web/login.html");
        patters.add("/web/index.html");
        patters.add("/web/product.html");
        patters.add("/users/reg");
        patters.add("/users/login");

        //完成拦截器的注册
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(patters);

    }
}
