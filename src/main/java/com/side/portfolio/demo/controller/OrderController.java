package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.service.LoginService;
import com.side.portfolio.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final LoginService loginService;
    private final OrderService orderService;

}
