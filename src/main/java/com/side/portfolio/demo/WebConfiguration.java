package com.side.portfolio.demo;

import com.side.portfolio.demo.interceptor.LogInCheckInterceptor;
import com.side.portfolio.demo.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/assets/**", "/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LogInCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/assets/**", "/css/**", "/*.ico", "/error",
                        "/", "/login", "/logout");
    }
}
