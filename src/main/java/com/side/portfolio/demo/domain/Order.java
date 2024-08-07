package com.side.portfolio.demo.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //ORDERED, CANCELED
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //==연관관계 편의 메서드==//
    public void setTeam(Team team) {
        this.team = team;
        team.getOrders().add(this);
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
        seller.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void setStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    //==연관관계 편의 메서드 끝==//

    //정적 팩토리 메서드
    public static Order makeOrder(Team team, Delivery delivery, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setTeam(team);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDERED);
        order.setCreatedDate(LocalDateTime.now());

        return order;
    }

    //주문 취소 메서드
    public void cancelOrder() {
        if (delivery.getStatus() == DeliveryStatus.DELIVERING) {
            throw new IllegalStateException("can't cancel Order : delivery status is DELIVERING");
        } else if (delivery.getStatus() == DeliveryStatus.DELIVERED) {
            throw new IllegalStateException("can't cancel Order : delivery status is DELIVERED");
        } else if (delivery.getStatus() == DeliveryStatus.CANCELED) {
            throw new IllegalStateException("can't cancel Order : delivery status is CANCELED");
        }

        this.setStatus(OrderStatus.CANCELED);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancelOrderItem();
        }
    }

    //주문 전체 가격 조회
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
