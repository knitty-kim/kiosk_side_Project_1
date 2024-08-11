package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Item;
import com.side.portfolio.demo.domain.ItemStatus;
import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.repository.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemJpaRepository itemJpaRepository;

    @Transactional
    public void createItem(Item item) {
        itemJpaRepository.save(item);
    }

    public List<Item> findAll() {
        return itemJpaRepository.findAll();
    }

    public Item findById(Long itemId) {
        return itemJpaRepository.findById(itemId).get();
    }

    /**
     * 아이템 페이지네이션
     * @param pageable
     * @return
     */
    public Page<Item> findByPagination(Pageable pageable){
        Page<Item> result = itemJpaRepository.findAll(pageable);
        return result;
    }

    @Transactional
    public void updateItem(Long id, String name, BigDecimal price, int qty, ItemStatus status, Seller seller) {
        Item item = itemJpaRepository.findById(id).get();
        item.setUpName(name);
        item.setUpPrice(price);
        item.setUpQty(qty);
        item.setUpStatus(status);
        item.setUpSeller(seller);
    }

}
