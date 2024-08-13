package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.*;
import com.side.portfolio.demo.repository.OrderJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderJpaRepository orderJpaRepository;
    @Autowired TeamService teamService;
    @Autowired ItemService itemService;
    @Autowired CartService cartService;

    @Test
    void makeOrder() {
        //given
        Team team = teamService.findById(10L);

        Cart cart = Cart.builder()
                .team(teamService.findById(10L))
                .item(itemService.findById(2L))
                .price(new BigDecimal("10000").multiply(new BigDecimal("2")))
                .qty(2)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
        cartService.createCart(cart);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.createOrderItem(cart.getItem(), cart.getPrice(), cart.getQty()));

        //when
        Long orderId = orderService.createOrder(team.getId());
        Order foundOrder = orderJpaRepository.findById(orderId).get();

        //then
        Assertions.assertEquals(OrderStatus.ORDERED, foundOrder.getStatus());
        Assertions.assertEquals(1, foundOrder.getOrderItems().size());
        Assertions.assertEquals(40000, foundOrder.calTotalPrice());
    }

    @Test
    void compareOrderItems() {
        //given
        Team team = teamService.findById(10L);

        Cart cart = Cart.builder()
                .team(team)
                .item(itemService.findById(2L))
                .price(new BigDecimal("10000").multiply(new BigDecimal("2")))
                .qty(2)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
        cartService.createCart(cart);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.createOrderItem(cart.getItem(), cart.getPrice(), cart.getQty()));

        //when
        Long orderId = orderService.createOrder(10L);
        Order order = orderService.findById(orderId);

        //then
        System.out.println("OrderItem 에서 orderId 로 조회=====================================");
        List<OrderItem> orderItems1 = orderService.findOrderItemByOrderId(orderId);
        System.out.println("Order 에서 OrderItems 바로 조회====================================");
        List<OrderItem> orderItems2 = order.getOrderItems();
        System.out.println("================================================================");
        System.out.println(orderItems1);
        System.out.println(orderItems2);
        Assertions.assertEquals(orderItems1, orderItems2);
    }

}