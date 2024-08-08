package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Delivery;
import com.side.portfolio.demo.domain.Order;
import com.side.portfolio.demo.domain.OrderItem;
import com.side.portfolio.demo.service.CartService;
import com.side.portfolio.demo.service.LoginService;
import com.side.portfolio.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final LoginService loginService;
    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/order")
    public String makeOrder(Model model, HttpServletRequest request) {
        log.info("Order Controller");

        //TODO 유효 계정인지 확인

        HttpSession session = request.getSession();

        String types = (String) session.getAttribute("types");
        Long teamId = (Long) session.getAttribute("id");

        if (types.equals("team") || types.equals("master")) {

            //주문 생성
            Long orderId = orderService.makeOrder(teamId);

            //주문 조회
            Order order = orderService.findByOrderId(orderId);

            //배송 데이터 조회
            Delivery delivery = order.getDelivery();

            //주문 상품 데이터 조회
            //List<OrderItem> orderItems = orderService.findOrderItemByOrderId(orderId);
            List<OrderItem> orderItems = order.getOrderItems();

            //주문 총 가격
            int totalPrice = order.getTotalPrice();
            //주문 총 수량
            int totalQty = order.getTotalQty();

            model.addAttribute("order", order);
            model.addAttribute("orderItems", orderItems);
            model.addAttribute("delivery", delivery);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalQty", totalQty);

        }

        return "pay/order";
    }
}
