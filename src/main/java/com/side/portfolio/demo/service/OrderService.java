package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.*;
import com.side.portfolio.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Long createOrder(Long teamId) {
        
        //팀, 장바구니 정보 조회
        Team team = teamJpaRepository.findById(teamId).get();
        List<Cart> carts = cartJpaRepository.findByTeam_Id(teamId);


        List<OrderItem> orderItems = new ArrayList<>();
        for (Cart cart : carts) {

            //상품 정보 조회
            Long itemId = cart.getItem().getId();
            Item item = itemJpaRepository.findById(itemId).get();

            //상품 재고 변경
            //TODO 주문상품 생성 시, 상품 재고보다 많이 주문 시, 에러 처리!
            item.subtractQty(cart.getQty());

            //주문 상품 생성
            orderItems.add(OrderItem.createOrderItem(item, cart.getPrice(), cart.getQty()));

            //장바구니 모두 비우기
            cartJpaRepository.deleteAllByTeam_Id(teamId);
        }

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setUpAddress(team.getAddress());
        delivery.setUpStatus(DeliveryStatus.PROCESSED);

        //주문 생성
        Order order = Order.createOrder(team, delivery, orderItems);

        //주문 저장
        orderJpaRepository.save(order);

        return order.getId();
    }

    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderJpaRepository.findById(orderId).get();
        order.cancelOrder();
    }

    //주문 전체 조회
    public List<Order> findAll() {
        return orderJpaRepository.findAll();
    }

    //주문 전체 페이징 조회
    public Page<Order> findByTeam_Id(Long teamId, Pageable pageable) {
        Page<Order> result = orderJpaRepository.findByTeam_Id(teamId, pageable);
        return result;
    }

    //주문 ID로 주문 조회
    public Order findById(Long orderId) {
        return orderJpaRepository.findById(orderId).get();
    }

    //주문 ID로 주문 상품 조회
    public List<OrderItem> findOrderItemByOrderId(Long orderId) {
        return orderItemJpaRepository.findByOrder_Id(orderId);
    }


}
