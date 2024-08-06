package com.side.portfolio.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartForm {

    private Long teamId;
    private Long itemId;
    private int qty;
    private int price;
}
