package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired CartService cartService;

    @Test
    void findByTeamId() {
        //team 필드에 대해 optional = false, nullable = false 를 주어야 inner join
        //그렇지 않으면 left outer join 을 한다!
        List<Cart> carts = cartService.findByTeamId(4L);

    }
}