package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Cart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired CartService cartService;

    @Test
    @DisplayName("Cart 객체를 team_id로 조회하는 SQL 확인")
    void findByTeamId() {
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.ASC, "createdDate");
        //연관관계 주인 객체의 외래키 필드에
        //optional = false, nullable = false 를 해야 inner join 가능!
        //그렇지 않으면 Optional 로 반환하기 위해 left outer join 을 한다!
        Page<Cart> carts = cartService.findByTeamId(4L, pageable);

        System.out.println("page size : " + carts.getSize());
        System.out.println("total page : " + carts.getTotalPages());
        System.out.println("total count : " + carts.getTotalElements());

    }
}