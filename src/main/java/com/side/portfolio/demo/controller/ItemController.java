package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Item;
import com.side.portfolio.demo.domain.ItemStatus;
import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.dto.ItemForm;
import com.side.portfolio.demo.service.ItemService;
import com.side.portfolio.demo.service.MemberService;
import com.side.portfolio.demo.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    public final ItemService itemService;
    public final SellerService sellerService;

    @GetMapping("/item-list")
    public String itemList(Model model) {

        model.addAttribute("items", itemService.findAll());
        return "basic/items";
    }

    /**
     * 상품 등록 폼
     * @param model
     * @return
     */
    @GetMapping("/item-list/add")
    public String createItemForm(Model model) {

        model.addAttribute("itemStatuses", ItemStatus.values());
        model.addAttribute("sellers", sellerService.findAll());
        model.addAttribute("itemForm", new ItemForm());
        return "basic/addItem";
    }

    /**
     * 상품 등록
     * @param form
     * @return
     */
    @PostMapping("/item-list/add")
    public String createItem(ItemForm form) {
        Item item = Item.builder()
                .name(form.getName())
                .price(form.getPrice())
                .qty(form.getQty())
                .status(form.getStatus())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .seller(sellerService.find(form.getSellerId()))
                .build();

        itemService.save(item);

        return "redirect:/item-list";
    }

    /**
     * 상품 수정 폼
     */
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model) {
        Item item = itemService.findItem(itemId);
        ItemForm form = new ItemForm();
        form.setId(itemId);
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setQty(item.getQty());
        form.setStatus(item.getStatus());
        form.setSellerId(item.getSeller().getId());

        model.addAttribute("itemStatuses", ItemStatus.values());
        model.addAttribute("itemForm", form);
        model.addAttribute("sellers", sellerService.findAll());

        return "basic/updateItem";
    }

    /**
     * 상품 수정
     * @param itemId
     * @param itemForm
     * @return
     */
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute ItemForm itemForm) {
        itemService.updateItem(itemId, itemForm.getName(), itemForm.getPrice(),
                itemForm.getQty(), itemForm.getStatus(), sellerService.find(itemForm.getSellerId()));

        return "redirect:/item-list";
    }
}
