package com.side.portfolio.demo.api.apiController;

import com.side.portfolio.demo.api.apiDto.PosItem;
import com.side.portfolio.demo.domain.Item;
import com.side.portfolio.demo.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PosController {

    private final ItemService itemService;

    @GetMapping("/api/items")
    public Result itemList() {
        List<Item> items = itemService.findAll();
        List<PosItem> collect = items.stream()
                .map(i -> new PosItem(i))
                .collect(Collectors.toList());

        return new Result(collect);
    }


    @Getter @Setter
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

}
