package com.side.portfolio.demo.item;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
        assertThat(foundItem).isEqualTo(savedItem);


    }

    @Test
    public void findById() {
        //given
        Item itemA = new Item("Fritos", 1, 10, "OK");
        Item itemB = new Item("Lays", 2, 20, "OK");
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        //when
        Item foundItemA = itemRepository.findById(itemA.getItemRid());

        //then
        assertThat(foundItemA.getItemRid()).isEqualTo(itemA.getItemRid());
        assertThat(foundItemA.getItemRid()).isNotEqualTo(itemB.getItemRid());

    }

    @Test
    public void findAll() {
        //given
        Item itemA = new Item("Fritos", 1, 10, "OK");
        Item itemB = new Item("Lays", 2, 20, "OK");
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        //when
        List<Item> itemList = itemRepository.findAll();

        //then
        assertThat(itemList.size()).isEqualTo(2);
        assertThat(itemList).contains(itemA, itemB);
    }

    @Test
    public void update() {
        //given
        Item itemA = new Item("Fritos", 1, 10, "OK");
        Item itemB = new Item("ChipsAhoy", 3, 30, "OK");
        itemRepository.save(itemA);

        //when
        itemRepository.update(itemA.getItemRid(), itemB);

        //then
        assertThat(itemA.getItemName()).isEqualTo(itemB.getItemName());
        assertThat(itemA.getPrice()).isEqualTo(itemB.getPrice());
        assertThat(itemA.getQuantity()).isEqualTo(itemB.getQuantity());
        assertThat(itemA.getStatus()).isEqualTo(itemB.getStatus());
    }

    @Test
    public void clear() {
        //given
        Item itemA = new Item("Fritos", 1, 10, "OK");
        Item itemB = new Item("Lays", 2, 20, "OK");
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        //when
        itemRepository.clear();

        //then
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList.size()).isEqualTo(0);
        assertThat(itemList).isEmpty();

    }
}