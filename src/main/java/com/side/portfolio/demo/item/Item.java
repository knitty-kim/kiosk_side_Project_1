package com.side.portfolio.demo.item;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Item {

    private Long itemRid;
    private String itemName;

    //private float price;
    private Integer price;

    private Integer quantity;
    private String status;
    private String remark;  //not required

    //private String itemImg1;
    //private String itemImg2;
    //private LocalDateTime createdDate;
    //private LocalDateTime modifiedDate;


    public Item(String itemName, Integer price, Integer quantity, String status) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }
}

