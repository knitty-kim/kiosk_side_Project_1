package com.side.portfolio.demo.repository.custom;

import com.side.portfolio.demo.dto.condition.OrderedItemDto;
import com.side.portfolio.demo.dto.condition.OrderedItemSearchCond;

import java.util.List;

public interface OrderCustomRepository {

    List<OrderedItemDto> searchOrderItemBySellerId(Long sellerId);

    List<OrderedItemDto> searchOrderItemBySellerId_Cond(Long sellerId, OrderedItemSearchCond cond);
}
