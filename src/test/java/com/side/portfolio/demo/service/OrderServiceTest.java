package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Item;
import com.side.portfolio.demo.domain.Order;
import com.side.portfolio.demo.domain.OrderStatus;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.repository.OrderJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderJpaRepository orderJpaRepository;
    @Autowired TeamService teamService;
    @Autowired ItemService itemService;

    @Test
    void makeOrder() {
        //given
        Team team = teamService.findById(10L);
        Item item = itemService.findById(5L);
        int count = 2;

        //when
        Long orderId = orderService.makeOrder(team.getId(), item.getId(), count);
        Order foundOrder = orderJpaRepository.findById(orderId).get();

        //then
        Assertions.assertEquals(OrderStatus.ORDERED, foundOrder.getStatus());
        Assertions.assertEquals(1, foundOrder.getOrderItems().size());
        Assertions.assertEquals(item.getPrice() * 2, foundOrder.getTotalPrice());
    }

    @Test
    void cancelOrder() {
    }
}