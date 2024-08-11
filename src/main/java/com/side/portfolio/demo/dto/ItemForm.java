package com.side.portfolio.demo.dto;

import com.side.portfolio.demo.domain.ItemStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ItemForm {

    private Long id;
    private String name;
    private BigDecimal price;
    private int qty;
    private ItemStatus status;
    private Long sellerId;

}
