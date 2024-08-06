package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Test
    void save() {
    }

    @Test
    void findAll() {
    }


    @Test
    @DisplayName("페이지네이션 테스트")
    public void findByPagination() throws Exception {
        //given
        int offset = 3;
        int limit = 2;

        //when
//        List<Item> items = itemJpaRepository.findByPagination(offset, limit);
//        Long count = itemRepository.count();

        //then
//        Assertions.assertThat(items.size()).isEqualTo(limit);
//        Assertions.assertThat(count).isEqualTo(14L);
    }



    @Test
    void find() {
    }
}