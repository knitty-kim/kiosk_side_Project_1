package com.side.portfolio.demo.dto.condition;

import com.side.portfolio.demo.status.ItemStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ItemSearchCond {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer qty;
    private String sellerName;
    private ItemStatus status;

}
