package com.baizhi.config;

import com.baizhi.interceptor.SameUrlDataInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//在WebMvcConfig中注册拦截器，
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new SameUrlDataInterceptor());//添加防止重复提交拦截器
    }
}
