package com.side.portfolio.demo;

import com.side.portfolio.demo.interceptor.LogIn_Interceptor;
import com.side.portfolio.demo.interceptor.Log_Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
        //로그 인터셉터
        registry.addInterceptor(new Log_Interceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/assets/**", "/css/**", "/*.ico", "/error");

        //로그인 인터셉터
        registry.addInterceptor(new LogIn_Interceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/assets/**", "/css/**", "/*.ico", "/error",
                        "/", "/login", "/logout", "/signup", "/ordered-list/**",
                        "/notice-list/**", "/images/**", "/about",
                        "/api/**",
                        "/seller/**",
                        "/team/**");
    }
}
