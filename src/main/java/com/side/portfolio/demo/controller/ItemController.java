package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.FileNameTable;
import com.side.portfolio.demo.domain.Item;
import com.side.portfolio.demo.dto.ItemCreateForm;
import com.side.portfolio.demo.dto.ItemUpdateForm;
import com.side.portfolio.demo.dto.condition.ItemDto;
import com.side.portfolio.demo.dto.condition.ItemSearchCond;
import com.side.portfolio.demo.service.FileService;
import com.side.portfolio.demo.service.ItemService;
import com.side.portfolio.demo.service.SellerService;
import com.side.portfolio.demo.status.ItemStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final SellerService sellerService;
    private final FileService fileService;

//    //전체 상품 목록
//    @GetMapping("/item-list")
//    public String itemList(Model model,
//                           @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
//
//        Page<Item> items = itemService.findAll(pageable);
//        model.addAttribute("items", items);
//
//        model.addAttribute("prev", items.getPageable().previousOrFirst().getPageNumber());
//        model.addAttribute("next", items.getPageable().next().getPageNumber());
//
//        model.addAttribute("hasPrev", items.hasPrevious());
//        model.addAttribute("hasNext", items.hasNext());
//
//        int groupSize = 3; //화면에 보여질 페이지 개수
//        int curPageGrp = (int) Math.floor((double) items.getNumber() / groupSize); //현재 페이지가 속한 그룹 번호
//        model.addAttribute("startPage", Math.max(0, ((curPageGrp) * groupSize)));
//        model.addAttribute("endPage", Math.min(items.getTotalPages() - 1, ((curPageGrp + 1) * groupSize) - 1));
//
//        model.addAttribute("curPage", items.getNumber());
//
//        return "basic/items";
//    }

    //전체 상품 검색 조회 목록
    @GetMapping("/item-list")
    public String itemList(Model model, ItemSearchCond cond,
                           @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<ItemDto> items = itemService.findItemByCond(cond, pageable);
        model.addAttribute("items", items);

        model.addAttribute("prev", items.getPageable().previousOrFirst().getPageNumber());
        model.addAttribute("next", items.getPageable().next().getPageNumber());

        model.addAttribute("hasPrev", items.hasPrevious());
        model.addAttribute("hasNext", items.hasNext());

        int groupSize = 3; //화면에 보여질 페이지 개수
        int curPageGrp = (int) Math.floor((double) items.getNumber() / groupSize); //현재 페이지가 속한 그룹 번호
        model.addAttribute("startPage", Math.max(0, ((curPageGrp) * groupSize)));
        model.addAttribute("endPage", Math.min(items.getTotalPages() - 1, ((curPageGrp + 1) * groupSize) - 1));

        model.addAttribute("curPage", items.getNumber());

        model.addAttribute("cond", cond);

        return "basic/items";
    }

    //상품 상세
    @GetMapping("/item-list/{itemId}")
    public String itemDetail(@PathVariable Long itemId, Model model) {

        Item item = itemService.findById(itemId);
        model.addAttribute(item);

        return "basic/item";
    }
    
    //상품 등록 폼
    @GetMapping("/item/add")
    public String createItemForm(Model model) {

        model.addAttribute("itemStatuses", ItemStatus.values());
        model.addAttribute("sellers", sellerService.findAll());
        model.addAttribute("itemCreateForm", new ItemCreateForm());

        return "basic/addItem";
    }

    //상품 등록
    @PostMapping("/item/add")
    public String createItem(Model model,
                             @Validated @ModelAttribute ItemCreateForm form,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            model.addAttribute("itemStatuses", ItemStatus.values());
            model.addAttribute("sellers", sellerService.findAll());
            return "basic/addItem";
        }

        Item item = Item.builder()
                .name(form.getName())
                .price(new BigDecimal(form.getPrice()))
                .qty(Integer.valueOf(form.getQty()))
                .status(form.getStatus())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .seller(sellerService.findById(form.getSellerId()))
                .build();

        try {
            // Java Reflection API
            // 순회할 필드 이름 목록
            String[] fieldNames = {"Img1", "Img2", "Img3", "Img4", "Img5", "Img6"};

            for (String fieldName : fieldNames) {
                // form.getImgX() 메서드 동적 호출
                Method getter = form.getClass().getMethod("get" + fieldName);
                MultipartFile fieldValue = (MultipartFile) getter.invoke(form);

                // fieldValue 가 비어 있지 않은 경우에만 처리
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    FileNameTable uploadedFile = fileService.createFile(fieldValue);
                    String uuidFileName = uploadedFile.getUuidFileName();

                    // notice.setImgX() 메서드를 동적으로 호출하여 값 설정
                    Method setter = item.getClass().getMethod("setUp" + fieldName, String.class);
                    setter.invoke(item, uuidFileName);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        itemService.createItem(item);

        return "redirect:/item-list/" + item.getId();
    }

    //상품 수정 폼
    @GetMapping("/item/update/{itemId}")
    public String updateItemForm(@PathVariable Long itemId, Model model) {

        Item item = itemService.findById(itemId);
        ItemUpdateForm form = new ItemUpdateForm();
        form.setName(item.getName());
        form.setPrice(String.valueOf(item.getPrice()));
        form.setQty(String.valueOf(item.getQty()));
        form.setStatus(item.getStatus());
        form.setSellerId(item.getSeller().getId());
        form.setCreatedDate(item.getCreatedDate());
        form.setModifiedDate(item.getModifiedDate());

        try {
            // Java Reflection API
            // 순회할 필드 이름 목록
            String[] fieldNames = {"Img1", "Img2", "Img3", "Img4", "Img5", "Img6"};

            for (String fieldName : fieldNames) {
                // notice.getImgX() 메서드 동적 호출
                Method getter = item.getClass().getMethod("get" + fieldName);
                String uuid = (String) getter.invoke(item);

                if (uuid != null) {
                    // 업로드 파일 이름 찾기
                    String uploadFileName = fileService.findTableByUuid(uuid).getUploadFileName();

                    // ItemUpdateForm 에 업로드 파일명 세팅
                    Method setter = form.getClass().getMethod("set" + fieldName, String.class);
                    setter.invoke(form, uploadFileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("itemStatuses", ItemStatus.values());
        model.addAttribute("sellers", sellerService.findAll());
        model.addAttribute("itemUpdateForm", form);
        model.addAttribute("itemId", itemId);

        return "basic/updateItem";
    }

    //상품 수정
    @PostMapping("/item/update/{itemId}")
    public String updateItem(Model model, @PathVariable Long itemId,
                             String deleteImages,
                             @Validated @ModelAttribute ItemUpdateForm form,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            model.addAttribute("itemStatuses", ItemStatus.values());
            model.addAttribute("sellers", sellerService.findAll());
            return "basic/updateItem";
        }

        //TODO - 수정일 set 할 것!
        Item item = itemService.findById(itemId);
        item.setUpName(form.getName());
        item.setUpPrice(new BigDecimal(form.getPrice()));
        item.setUpQty(Integer.valueOf(form.getQty()));
        item.setUpStatus(form.getStatus());
        item.setUpSeller(sellerService.findById(form.getSellerId()));
        item.setUpModifiedDate(LocalDateTime.now());

        if (deleteImages != null && !deleteImages.isEmpty()) {
            String[] deleteImageIds = deleteImages.split(",");

            for (String imgId : deleteImageIds) {
                try {
                    Method getter = item.getClass().getMethod("get" + imgId);
                    String uuid = (String) getter.invoke(item);

                    Method setter = item.getClass().getMethod("setUp" + imgId, String.class);
                    setter.invoke(item, (String) null);

                    fileService.deleteTableByUuidFileName(uuid);
                    fileService.deleteFile(uuid);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            // Java Reflection API
            // 순회할 필드 이름 목록
            String[] fieldNames = {"Img_1", "Img_2", "Img_3", "Img_4", "Img_5", "Img_6"};

            for (String fieldName : fieldNames) {

                String modifiedName = fieldName.replace("_", "");
                Method getter = form.getClass().getMethod("get" + fieldName);
                MultipartFile fieldValue = (MultipartFile) getter.invoke(form);

                if (fieldValue != null && !fieldValue.isEmpty()) {
                    FileNameTable uploadedFile = fileService.createFile(fieldValue);
                    String uuidFileName = uploadedFile.getUuidFileName();

                    Method setter = item.getClass().getMethod("setUp" + modifiedName, String.class);
                    setter.invoke(item, uuidFileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        itemService.createItem(item);

//        itemService.updateItem(itemId, form.getName(), new BigDecimal(form.getPrice()),
//                Integer.valueOf(form.getQty()), form.getStatus(), sellerService.findById(form.getSellerId()));

        return "redirect:/item-list/" + item.getId();
    }



}
