package com.side.portfolio.demo.api.apiDto;

import com.side.portfolio.demo.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
public class PosItem {

    private Long id;
    private String name;
    private int qty;
    private String img1;
    private String remark;

    public PosItem(Item item) {
        id = item.getId();
        name = item.getName();
        qty = item.getQty();
        img1 = item.getImg1();
        remark = item.getRemark();
    }
}
