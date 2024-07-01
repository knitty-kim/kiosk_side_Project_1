package com.side.portfolio.demo.domain;

import com.side.portfolio.demo.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private int price;

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

    //==비즈니스 로직==//
    //도메인 주도 설계 ; 도메인이 비즈니스 로직의 주도권을 가지는 설계
    //이런 설계는 서비스의 많은 로직이 엔티티로 이동하게 되며,
    //서비스는 엔티티를 호출하는 정도의 얇은 비즈니스 로직을 가지게 됨
    //이렇게 하면 information expert pattern 지키면서 개발할 수 있다
    //*** 엔티티 한 곳에서 처리가능하면 엔티티에서 처리
    //*** 엔티티의 처리 범위를 넘어가면 서비스에서 처리

    /**
     * 상품명 변경
     */
    public void updateName(String name) {
        this.name = name;
    }

    /**
     * 가격 변경
     */
    public void updatePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("price can't be under ZERO");
        }
        this.price = price;
    }

    /**
     * 수량 변경
     */
    public void updateQty(int qty) {
        if (qty < 0) {
            throw new NotEnoughStockException("quantity can't be under ZERO");
        }
        this.qty = qty;
    }

    /**
     * 상태 변경
     * @param status
     */
    public void updateStatus(ItemStatus status) {
        this.status = status;
    }

    /**
     * 판매자 변경
     * @param seller
     */
    public void updateSeller(Seller seller) {
        this.seller = seller;
    }

    @Builder
    public Item(String name, int price, int qty, ItemStatus status, String remark,
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
