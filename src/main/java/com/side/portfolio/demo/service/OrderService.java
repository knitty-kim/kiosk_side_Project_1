package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.*;
import com.side.portfolio.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;
    private final TeamJpaRepository teamJpaRepository;
    private final CartJpaRepository cartJpaRepository;
    private final ItemJpaRepository itemJpaRepository;

    /**
     * 주문 생성
     * @param teamId
     * @return
     */
    @Transactional
    public Long makeOrder(Long teamId) {
        
        //팀, 장바구니 정보 조회
        Team team = teamJpaRepository.findById(teamId).get();
        List<Cart> carts = cartJpaRepository.findByTeam_Id(teamId);

        //TODO 주문상품 생성 시, 상품 재고보다 많이 주문 시, 에러 처리!

        List<OrderItem> orderItems = new ArrayList<>();
        for (Cart cart : carts) {

            //상품 정보 조회
            Long itemId = cart.getItem().getId();
            Item item = itemJpaRepository.findById(itemId).get();

            //상품 재고 변경
            item.minusQty(cart.getQty());

            //주문 상품 생성
            orderItems.add(OrderItem.makeOrderItem(item, cart.getPrice(), cart.getQty()));
        }

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(team.getAddress());
        delivery.setStatus(DeliveryStatus.PROCESSED);

        //주문 생성
        Order order = Order.makeOrder(team, delivery, orderItems);

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

    public Order findByOrderId(Long orderId) {
        Order order = orderJpaRepository.findById(orderId).get();
        return order;
    }

    //주문 ID로 주문 상품 조회
    public List<OrderItem> findOrderItemByOrderId(Long orderId) {
        List<OrderItem> orderItems = orderItemJpaRepository.findByOrder_Id(orderId);
        return orderItems;
    }


}
