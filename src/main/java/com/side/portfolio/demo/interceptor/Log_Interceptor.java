package com.side.portfolio.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.side.portfolio.demo.SessionConst.LOG_ID;

@Slf4j
public class Log_Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String logId = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, logId);

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            log.info("preHandle handler={}", handler);
//            log.info("preHandle handlerMethod={}", handlerMethod.getMethod().getName());
        } else if (handler instanceof ResourceHttpRequestHandler) {
            ResourceHttpRequestHandler requestHandler = (ResourceHttpRequestHandler) handler;
//            log.info("preHandle handler={}", handler);
//            log.info("preHandle requestHandler={}", requestHandler);
        }

        log.info("preHandle [{}], [{}]", logId, requestURI);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
//        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);

        log.info("afterCompletion [{}], [{}], [{}]", logId, requestURI, handler);

        if (ex != null) {
            log.error("afterCompletion error", ex);
        }

    }
}
