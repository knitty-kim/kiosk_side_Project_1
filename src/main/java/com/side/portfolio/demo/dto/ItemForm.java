package com.side.portfolio.demo.dto;

import com.side.portfolio.demo.domain.ItemStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemForm {

    private Long id;
    private String name;
    private float price;
    private int qty;
    private ItemStatus status;
    private Long sellerId;

}
