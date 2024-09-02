package com.side.portfolio.demo.domain;

import com.side.portfolio.demo.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "cart_qty")
    private int qty;

    @Column(name = "cart_price")
    private BigDecimal price;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //== 변경 메서드 시작==//
    public void setUpQty(int qty) {
        if (qty <= 0) {
            throw new NotEnoughStockException("quantity can't not be under ZERO");
        }
        this.qty = qty;
    }

    public void setUpPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price can't be under ZERO");
        }
        this.price = price;
    }

    public void setUpModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    //== 변경 메서드 끝==//


    @Builder
    public Cart(Team team, Item item, BigDecimal price, int qty,
                LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.team = team;
        this.item = item;
        this.price = price;
        this.qty = qty;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}