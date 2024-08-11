package com.side.portfolio.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class CartForm {

    private Long teamId;
    private Long itemId;
    private int qty;
    private BigDecimal price;
}
