package com.side.portfolio.demo.item;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.*;

public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clear();
    }


    @Test
    public void save() {
        //given
        Item item = new Item("Fritos", 1, 10, "OK");

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item foundItem = itemRepository.findById(item.getItemRid());
        Assertions.assertThat(foundItem).isEqualTo(savedItem);


    }

    @Test
    public void findById() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void update() {
    }

    @Test
    public void clear() {
    }
}