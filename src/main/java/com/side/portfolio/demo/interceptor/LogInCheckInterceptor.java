package com.side.portfolio.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.side.portfolio.demo.SessionConst.LOGIN_ID;

@Slf4j
public class LogInCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("LogInCheckInterceptor requestURI={}", requestURI);
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(LOGIN_ID) == null) {
            log.info("invalid User Request!");
            log.info("redirect to LogIn page!");
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }

        return true;
    }
}
