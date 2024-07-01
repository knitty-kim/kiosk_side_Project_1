package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Item;
import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/seller-list")
    public String sellerList(Model model) {

        model.addAttribute("sellers", sellerService.findAll());
        return "basic/sellers";
    }

}
