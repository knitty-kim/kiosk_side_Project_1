package com.side.portfolio.demo.repository.custom;

import com.side.portfolio.demo.dto.condition.OrderedItemDto;

import java.util.List;

public interface OrderCustomRepository {

    List<OrderedItemDto> searchOrderItemBySellerId(Long sellerId);
}
