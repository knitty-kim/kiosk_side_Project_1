package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart-list")
    public String cartList(Model model) {
        log.info("Cart Controller");


        return "/main";
    }



}
