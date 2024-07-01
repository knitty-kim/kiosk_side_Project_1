package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Item;
import com.side.portfolio.demo.domain.ItemStatus;
import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void save(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findItem(Long id) {
        return itemRepository.find(id);
    }

    @Transactional
    public void updateItem(Long id, String name, int price, int qty, ItemStatus status, Seller seller) {
        Item item = itemRepository.find(id);
        item.updateName(name);
        item.updatePrice(price);
        item.updateQty(qty);
        item.updateStatus(status);
        item.updateSeller(seller);
    }
}
