package com.side.portfolio.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private BigDecimal orderPrice;
    private int count;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //주문상품 생성
    public static OrderItem createOrderItem(Item item, BigDecimal orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setUpItem(item);
        orderItem.setUpOrderPrice(orderPrice);
        orderItem.setUpCount(count);
        return orderItem;
    }

    //주문상품 취소
    public void cancelOrderItem() {
        getItem().addQty(count);
    }

    //==변경 메서드 시작==//
    public void setUpOrder(Order order) {
        this.order = order;
    }

    public void setUpItem(Item item) {
        this.item = item;
    }

    public void setUpOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setUpCount(int count) {
        this.count = count;
    }

    public void setUpCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    //==변경 메서드 끝==//

}
