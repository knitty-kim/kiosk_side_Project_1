package com.side.portfolio.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private float orderPrice;
    private int count;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //정적 팩토리 메서드
    public static OrderItem makeOrderItem(Item item, float orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        return orderItem;
    }

    //비즈니스 로직 메서드
    //주문상품 취소
    public void cancelOrderItem() {
        getItem().plusQty(count);
    }

    //조회 메서드
    //주문상품 전체 가격 조회
    public float getFinalPrice() {
        return getOrderPrice() * getCount();
    }
}
