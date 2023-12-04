package com.side.portfolio.demo.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    public Item save(Item item) {
        item.setItemRid(++sequence);
        store.put(item.getItemRid(), item);
        return item;
    }

    public Item findById(Long itemRId) {
        return store.get(itemRId);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long itemRId, Item updateItem) {
        Item foundItem = findById(itemRId);
        foundItem.setItemName(updateItem.getItemName());
        foundItem.setPrice(updateItem.getPrice());
        foundItem.setQuantity(updateItem.getQuantity());
        foundItem.setStatus(updateItem.getStatus());
    }

    public void clear(){
        store.clear();
    }
}
