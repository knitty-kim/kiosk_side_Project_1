package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Item;
import com.side.portfolio.demo.status.ItemStatus;
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

    public Page<Item> findAll(Pageable pageable){
        Page<Item> result = itemJpaRepository.findAll(pageable);
        return result;
    }

    public Item findById(Long itemId) {
        return itemJpaRepository.findById(itemId).get();
    }


    //상품 수정
    @Transactional
    public void updateItem(Long id, String name, BigDecimal price, int qty, ItemStatus status, Seller seller) {
        //빌더 패턴을 사용중이나,
        //수정 시에는 기존 객체의 필드를 SET 해야하므로 setUp.. 메서드 사용
        Item item = itemJpaRepository.findById(id).get();
        item.setUpName(name);
        item.setUpPrice(price);
        item.setUpQty(qty);
        item.setUpStatus(status);
        item.setUpSeller(seller);
    }

}
