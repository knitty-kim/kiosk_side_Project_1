package com.side.portfolio.demo.repository.custom;

import com.side.portfolio.demo.dto.condition.ItemDto;
import com.side.portfolio.demo.dto.condition.ItemSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemCustomRepository {

    Page<ItemDto> searchItem(ItemSearchCond cond, Pageable pageable);
}
