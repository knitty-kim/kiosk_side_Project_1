package com.side.portfolio.demo.dto.condition;

import com.side.portfolio.demo.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class OrderedItemSearchCond {

    private Long orderId;
    private Long itemId;
    private String itemName;
    private String teamName;
    private OrderStatus orderStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

}
