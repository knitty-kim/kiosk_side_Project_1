package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.Message;
import com.side.portfolio.demo.SessionConst;
import com.side.portfolio.demo.domain.Delivery;
import com.side.portfolio.demo.domain.Order;
import com.side.portfolio.demo.domain.OrderItem;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.dto.condition.OrderedItemDto;
import com.side.portfolio.demo.dto.condition.OrderedItemSearchCond;
import com.side.portfolio.demo.service.LogInService;
import com.side.portfolio.demo.service.OrderService;
import com.side.portfolio.demo.service.TeamService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final LogInService logInService;
    private final TeamService teamService;

    //주문 목록
    @GetMapping("/order-list")
    public String orderList(Model model, HttpServletRequest request,
                            @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("orderList");

        HttpSession session = request.getSession();

        String types = (String) session.getAttribute(SessionConst.LOGIN_TYPES);

        if (types.equals("team") || types.equals("master")) {
            Long teamId = (Long) session.getAttribute(SessionConst.LOGIN_ID);

            if (types.equals("team")) {
                Team team = teamService.findById(teamId);
                model.addAttribute("tickets", team.getTickets());
            }

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
                endPage = 0;
            }

            model.addAttribute("endPage", endPage);

            model.addAttribute("curPage", orders.getNumber());

        } else if (types.equals("seller") || types.equals("master")) {
            Long sellerId = (Long) session.getAttribute(SessionConst.LOGIN_ID);

            Page<Order> orders = orderService.findByTeam_Id(sellerId, pageable);
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
                endPage = 0;
            }

            model.addAttribute("endPage", endPage);

            model.addAttribute("curPage", orders.getNumber());
        }

        return "pay/orders";
    }

    //주문 등록
    @GetMapping("/orders/add")
    public String createOrder(Model model, HttpServletRequest request) {

        log.info("createOrder");

        //TODO 유효 계정인지 확인

        HttpSession session = request.getSession();

        String types = (String) session.getAttribute(SessionConst.LOGIN_TYPES);
        Long teamId = (Long) session.getAttribute(SessionConst.LOGIN_ID);

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

    //상세 주문 내역
    @GetMapping("/orders/{orderId}")
    public String readOrder(@PathVariable Long orderId, Model model) {

        log.info("readOrder");

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

    //상세 주문 취소
    @ResponseBody
    @PostMapping("/orders/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "OK";
    }

    //주문 수락
    @ResponseBody
    @PostMapping("/orders/accept/{orderId}")
    public String acceptOrder(@PathVariable Long orderId) {
        orderService.acceptOrder(orderId);
        return "OK";
    }

    //상세 주문 거절
    @ResponseBody
    @PostMapping("/orders/reject/{orderId}")
    public String rejectOrder(@PathVariable Long orderId) {
        orderService.rejectOrder(orderId);
        return "OK";
    }

    //주문된 목록
    //판매자 ID로 주문 상품 조회
    @GetMapping("/ordered-list/{sellerId}")
    public String orderedList(Model model, HttpServletRequest request,
                              @PathVariable Long sellerId, OrderedItemSearchCond cond) {

        if (logInService.invalidAccess(request.getSession(), sellerId)) {
            model.addAttribute("data", new Message("잘못된 접근입니다!", "/"));
            return "message";
        }

        List<OrderedItemDto> content = orderService.findOrderItemBySeller_Id(sellerId, cond);
        
        //Map<주문번호, 주문내용>으로 그룹화
        Map<Long, List<OrderedItemDto>> groupedOrders = content.stream()
                .collect(Collectors.groupingBy(OrderedItemDto::getOrderId));


        model.addAttribute("groupedOrders", groupedOrders);
        model.addAttribute("cond", cond);

        return "pay/sellerOrders";
    }

}
