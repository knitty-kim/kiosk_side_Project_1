package com.side.portfolio.demo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private int price;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //정적 팩토리 메서드
    public static Cart makeCart(Item item, int itemPrice, int qty) {
        Cart cart = new Cart();
        cart.setItem(item);
        cart.setPrice(itemPrice);
        cart.setQty(qty);
        return cart;
    }

}