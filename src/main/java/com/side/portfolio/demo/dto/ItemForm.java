package com.side.portfolio.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemForm {

    private Long id;
    private String name;
    private int price;
    private int qty;
    private Long sellerId;

}
