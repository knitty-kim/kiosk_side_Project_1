package com.side.portfolio.demo.dto.condition;

import com.querydsl.core.annotations.QueryProjection;
import com.side.portfolio.demo.status.ItemStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer qty;
    private Long sellerId;
    private String sellerName;
    private ItemStatus status;

    @QueryProjection
    public ItemDto(Long id, String name, BigDecimal price,
                   Integer qty, Long sellerId, String sellerName,
                   ItemStatus status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.status = status;
    }
}
