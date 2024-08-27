package com.side.portfolio.demo.domain;

import com.side.portfolio.demo.status.DeliveryStatus;
import com.side.portfolio.demo.status.OrderStatus;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //ORDERED, ACCEPTED, REJECTED, CANCELED
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //주문 생성
    public static Order createOrder(Team team, Delivery delivery, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setUpTeam(team);
        order.setUpDelivery(delivery);

        for (OrderItem orderItem : orderItems) {
            order.setUpOrderItem(orderItem);
        }

        order.setUpStatus(OrderStatus.ORDERED);
        order.setUpCreatedDate(LocalDateTime.now());
        order.setUpModifiedDate(LocalDateTime.now());

        return order;
    }

    //주문 취소
    public void cancelOrder() {
        if (delivery.getStatus() == DeliveryStatus.DELIVERING) {
            throw new IllegalStateException("can't cancel Order : delivery status is DELIVERING");
        } else if (delivery.getStatus() == DeliveryStatus.DELIVERED) {
            throw new IllegalStateException("can't cancel Order : delivery status is DELIVERED");
        } else if (delivery.getStatus() == DeliveryStatus.CANCELED) {
            throw new IllegalStateException("can't cancel Order : delivery status is CANCELED");
        }

        for (OrderItem orderItem : orderItems) {
            orderItem.cancelOrderItem();
        }

        this.delivery.setUpStatus(DeliveryStatus.CANCELED);
        this.setUpStatus(OrderStatus.CANCELED);
        this.setUpModifiedDate(LocalDateTime.now());

    }

    //주문 전체 가격 계산
    public BigDecimal calTotalPrice() {
        BigDecimal totalPrice = new BigDecimal("0");
        for (OrderItem orderItem : orderItems) {
            totalPrice = totalPrice.add(orderItem.getOrderPrice());
        }
        return totalPrice;
    }

    //주문 전체 수량 조회
    public int calTotalQty() {
        int totalQty = 0;
        for (OrderItem orderItem : orderItems) {
            totalQty += orderItem.getCount();
        }
        return totalQty;
    }

    //==연관관계 편의 메서드==//
    public void setUpTeam(Team team) {
        this.team = team;
        team.getOrders().add(this);
    }

    public void setUpOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
//        orderItem.setUpCreatedDate(LocalDateTime.now());
//        orderItem.setUpModifiedDate(LocalDateTime.now());
        orderItem.setUpOrder(this);
    }

    public void setUpDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setUpOrder(this);
    }
    //==연관관계 편의 메서드 끝==//

    //==변경 메서드 시작==//
    public void setUpStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    public void setUpCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    //==변경 메서드 끝==//

}
