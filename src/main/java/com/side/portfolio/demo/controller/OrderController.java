package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Delivery;
import com.side.portfolio.demo.domain.Order;
import com.side.portfolio.demo.domain.OrderItem;
import com.side.portfolio.demo.service.CartService;
import com.side.portfolio.demo.service.LoginService;
import com.side.portfolio.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final LoginService loginService;
    private final OrderService orderService;
    private final CartService cartService;

    //주문 목록
    @GetMapping("/order-list")
    public String orderList(Model model, HttpServletRequest request,
                            @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("orderList");

        HttpSession session = request.getSession();

        String types = (String) session.getAttribute("types");
        Long teamId = (Long) session.getAttribute("id");

        if (types.equals("team") || types.equals("master")) {
            Page<Order> orders = orderService.findByTeam_Id(teamId, pageable);
            model.addAttribute("orders", orders);

            model.addAttribute("prev", orders.getPageable().previousOrFirst().getPageNumber());
            model.addAttribute("next", orders.getPageable().next().getPageNumber());

            model.addAttribute("hasPrev", orders.hasPrevious());
            model.addAttribute("hasNext", orders.hasNext());

            int groupSize = 3; //화면에 보여질 페이지 개수
            int curPageGrp = (int) Math.floor((double) orders.getNumber() / groupSize); //현재 페이지가 속한 그룹 번호
            model.addAttribute("startPage", Math.max(0, ((curPageGrp) * groupSize)));

            int endPage = Math.min(orders.getTotalPages() - 1, ((curPageGrp + 1) * groupSize) - 1);
            if (endPage == -1) {
                model.addAttribute("endPage", 0);
            } else {
                model.addAttribute("endPage", endPage);
            }

            model.addAttribute("curPage", orders.getNumber());
        }

        return "pay/orders";
    }

    /**
     * 주문 등록
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/orders/add")
    public String createOrder(Model model, HttpServletRequest request) {
        log.info("createOrder");

        //TODO 유효 계정인지 확인

        HttpSession session = request.getSession();

        String types = (String) session.getAttribute("types");
        Long teamId = (Long) session.getAttribute("id");

        if (types.equals("team") || types.equals("master")) {

            //주문 생성
            Long orderId = orderService.createOrder(teamId);

            //주문 조회
            Order order = orderService.findById(orderId);

            //배송 데이터 조회
            Delivery delivery = order.getDelivery();

            //주문 상품 데이터 조회
            List<OrderItem> orderItems = order.getOrderItems();

            //주문 총 가격
            BigDecimal totalPrice = order.calTotalPrice();

            //주문 총 수량
            int totalQty = order.calTotalQty();

            model.addAttribute("order", order);
            model.addAttribute("orderItems", orderItems);
            model.addAttribute("delivery", delivery);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalQty", totalQty);

        }

        return "pay/order";
    }

    @GetMapping("/orders/{orderId}")
    public String readOrder(@PathVariable Long orderId, Model model) {

        //주문 조회
        Order order = orderService.findById(orderId);

        //배송 데이터 조회
        Delivery delivery = order.getDelivery();

        //주문 상품 데이터 조회
        List<OrderItem> orderItems = order.getOrderItems();

        //주문 총 가격
        BigDecimal totalPrice = order.calTotalPrice();

        //주문 총 수량
        int totalQty = order.calTotalQty();

        model.addAttribute("order", order);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("delivery", delivery);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalQty", totalQty);

        return "pay/order";
    }
}
