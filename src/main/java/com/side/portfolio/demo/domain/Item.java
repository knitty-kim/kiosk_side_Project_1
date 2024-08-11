package com.side.portfolio.demo.domain;

import com.side.portfolio.demo.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String name;

    @Column(name = "item_price")
    private BigDecimal price;

    @Column(name = "item_qty")
    private int qty;

    //OPEN, CLOSED
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    private String remark;
    private String img1;
    private String img2;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    //Builder.Default 미사용 시, 빌더 패턴 적용했을 때, orderItems = null이 된다
    //@Builder.Default
    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<Cart> carts = new ArrayList<>();

    //==연관관계 편의 메서드 시작==//
    public void setUpSeller(Seller seller) {
        this.seller = seller;
        seller.getItems().add(this);
    }
    //==연관관계 편의 메서드 끝==//

    //==변경 메서드 시작==//
    public void setUpName(String name) {
        this.name = name;
    }

    public void setUpPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price can't be under ZERO");
        }
        this.price = price;
    }

    public void setUpQty(int qty) {
        if (qty < 0) {
            throw new NotEnoughStockException("quantity can't be below ZERO");
        }
        this.qty = qty;
    }

    public void addQty(int qty) {
        this.qty += qty;
        this.modifiedDate = LocalDateTime.now();
    }

    public void subtractQty(int qty) {
        int tempQty = this.qty - qty;
        if (tempQty < 0) {
            throw new NotEnoughStockException("can't subtract Quantity below ZERO");
        }
        this.qty = tempQty;
        this.modifiedDate = LocalDateTime.now();
    }
    
    public void setUpStatus(ItemStatus status) {
        this.status = status;
    }
    //==변경 메서드 끝==//

    @Builder
    public Item(String name, BigDecimal price, int qty, ItemStatus status, String remark,
                String img1, String img2, LocalDateTime createdDate, LocalDateTime modifiedDate,
                Seller seller, List<OrderItem> orderItems) {
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.status = status;
        this.remark = remark;
        this.img1 = img1;
        this.img2 = img2;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.seller = seller;
        this.orderItems = orderItems;
    }
}
