package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.*;
import com.side.portfolio.demo.repository.CartJpaRepository;
import com.side.portfolio.demo.repository.ItemJpaRepository;
import com.side.portfolio.demo.repository.OrderJpaRepository;
import com.side.portfolio.demo.repository.TeamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final TeamJpaRepository teamJpaRepository;
    private final CartJpaRepository cartJpaRepository;

    /**
     * 주문 생성
     * @param teamId
     * @param itemId
     * @param count
     * @return
     */
    @Transactional
    public Long makeOrder(Long teamId, Long itemId, int count) {
        
        //팀, 상품, 장바구니 정보 조회
        Team team = teamJpaRepository.findById(teamId).get();
        Item item = itemJpaRepository.findById(itemId).get();
//        List<Cart> carts = cartJpaRepository.findByTeam_Id(teamId);
        
        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(team.getAddress());
        delivery.setStatus(DeliveryStatus.PROCESSED);

        //주문상품 생성
        OrderItem orderItem = OrderItem.makeOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.makeOrder(team, delivery, orderItem);

        //주문 저장
        orderJpaRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     * @param orderId
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderJpaRepository.findById(orderId).get();
        order.cancelOrder();
    }
}