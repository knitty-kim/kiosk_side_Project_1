package com.side.portfolio.demo.dto.condition;

import com.querydsl.core.annotations.QueryProjection;
import com.side.portfolio.demo.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class OrderedItemDto {

    private Long orderId;
    private Long itemId;
    private String itemName;
    private int orderedCount;
    private Long teamId;
    private String teamName;
    private OrderStatus orderStatus;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @QueryProjection
    public OrderedItemDto(Long orderId, Long itemId, String itemName, int orderedCount,
                          Long teamId, String teamName, OrderStatus orderStatus,
                          LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.orderedCount = orderedCount;
        this.teamId = teamId;
        this.teamName = teamName;
        this.orderStatus = orderStatus;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
