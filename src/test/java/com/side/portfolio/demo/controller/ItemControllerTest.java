package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Item;
import com.side.portfolio.demo.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ItemControllerTest {

    @Autowired
    ItemService itemService;

    @Test
    @DisplayName("페이지네이션 뷰 테스트")
    public void itemList() throws Exception {
        //given
        PageRequest pageRequest = PageRequest.of(1, 3);
        Page<Item> items = itemService.findAll(pageRequest);

        //when
        int totalPages = items.getTotalPages(); //총 페이지 수
        int curPage = items.getNumber(); //현재 페이지 번호
        int pageSize = 3; //화면에 보여줄 페이지 개수
        int curPageGrp = (int) Math.floor((double) curPage / pageSize); //현재 페이지가 속한 그룹 번호
        
        //then
        System.out.println("총 페이지 수 ; " + totalPages);
        System.out.println("현재 페이지 번호 ; " + curPage);
        System.out.println("화면에 보여줄 페이지 개수 ; " + pageSize);
        System.out.println("현재 페이지가 속한 그룹 번호 ; " + curPageGrp);
        System.out.println("현재 페이지가 속한 그룹의 첫 페이지 번호 ; " + Math.max(0, ((curPageGrp) * pageSize)));
        System.out.println("현재 페이지가 속한 그룹의 끝 페이지 번호 ; " + Math.min(totalPages - 1, ((curPageGrp + 1) * pageSize) - 1));
        
    }

}