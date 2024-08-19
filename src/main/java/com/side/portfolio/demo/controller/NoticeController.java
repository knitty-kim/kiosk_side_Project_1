package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Notice;
import com.side.portfolio.demo.dto.ItemUpdateForm;
import com.side.portfolio.demo.dto.NoticeCreateForm;
import com.side.portfolio.demo.service.NoticeService;
import com.side.portfolio.demo.status.NoticeStatus;
import com.side.portfolio.demo.upload.FileUtil;
import com.side.portfolio.demo.upload.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final FileUtil fileUtil;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/notice-list")
    public String noticeList(Model model,
                             @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Notice> notices = noticeService.findAll(pageable);
        model.addAttribute("notices", notices);

        model.addAttribute("prev", notices.getPageable().previousOrFirst().getPageNumber());
        model.addAttribute("next", notices.getPageable().next().getPageNumber());

        model.addAttribute("hasPrev", notices.hasPrevious());
        model.addAttribute("hasNext", notices.hasNext());

        int groupSize = 3; //화면에 보여질 페이지 개수
        int curPageGrp = (int) Math.floor((double) notices.getNumber() / groupSize); //현재 페이지가 속한 그룹 번호
        model.addAttribute("startPage", Math.max(0, ((curPageGrp) * groupSize)));

        int endPage = Math.min(notices.getTotalPages() - 1, ((curPageGrp + 1) * groupSize) - 1);
        if (endPage == -1) {
            endPage = 0;
        }

        model.addAttribute("endPage", endPage);

        model.addAttribute("curPage", notices.getNumber());

        return "basic/notices";
    }

    @GetMapping("/notice-list/{noticeId}")
    public String noticeDetail(@PathVariable Long noticeId, Model model) {

        Notice notice = noticeService.findById(noticeId);
        model.addAttribute(notice);

        return "basic/notice";
    }

    @GetMapping("/notice/add")
    public String createNoticeForm(Model model) {

        model.addAttribute("noticeCreateForm", new NoticeCreateForm());

        return "basic/addNotice";
    }

    @PostMapping("/notice/add")
    public String createNotice(Model model, @Validated NoticeCreateForm form,
                               BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "basic/addNotice";
        }

        log.info("img1 : " + form.getImg1());

        if (!form.getImg1().isEmpty()) {

            UploadFile uploadedFile1 = fileUtil.createFile(form.getImg1());
            UploadFile uploadedFile2 = fileUtil.createFile(form.getImg2());
//            UploadFile uploadedFile3 = fileUtil.createFile(form.getImg3());
//            UploadFile uploadedFile4 = fileUtil.createFile(form.getImg4());
//            UploadFile uploadedFile5 = fileUtil.createFile(form.getImg5());
//            UploadFile uploadedFile6 = fileUtil.createFile(form.getImg6());
            String uuidFileName1 = uploadedFile1.getUuidFileName();
            String uuidFileName2 = uploadedFile2.getUuidFileName();
//            String uuidFileName3 = uploadedFile3.getUuidFileName();
//            String uuidFileName4 = uploadedFile4.getUuidFileName();
//            String uuidFileName5 = uploadedFile5.getUuidFileName();
//            String uuidFileName6 = uploadedFile6.getUuidFileName();

            Notice notice = Notice.builder()
                    .title(form.getTitle())
                    .content(form.getContent())
                    .img1(uuidFileName1)
                    .img2(uuidFileName2)
                    .status(NoticeStatus.POSTED)
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .build();

            noticeService.createNotice(notice);
            return "redirect:/notice-list/" + notice.getId();

        } else {

            Notice notice = Notice.builder()
                    .title(form.getTitle())
                    .content(form.getContent())
                    .status(NoticeStatus.POSTED)
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .build();

            noticeService.createNotice(notice);
            return "redirect:/notice-list/" + notice.getId();

        }

    }

    @GetMapping("/notice/update/{noticeId}")
    public String updateNoticeForm(@PathVariable Long itemId, Model model) {

//        Item item = itemService.findById(itemId);
//        ItemUpdateForm form = new ItemUpdateForm();
//        form.setName(item.getName());
//        form.setPrice(String.valueOf(item.getPrice()));
//        form.setQty(String.valueOf(item.getQty()));
//        form.setStatus(item.getStatus());
//        form.setSellerId(item.getSeller().getId());

//        model.addAttribute("itemStatuses", ItemStatus.values());
//        model.addAttribute("itemUpdateForm", form);
//        model.addAttribute("sellers", sellerService.findAll());

        return "basic/updateNotice";
    }

    //상품 수정
    @PostMapping("/notice/update/{noticeId}")
    public String updateNotice(Model model, @PathVariable Long itemId,
                               @Validated @ModelAttribute ItemUpdateForm form,
                               BindingResult bindingResult) {

//        if (bindingResult.hasErrors()) {
//            log.info("errors={}", bindingResult);
//            model.addAttribute("itemStatuses", ItemStatus.values());
//            model.addAttribute("sellers", sellerService.findAll());
//            return "basic/updateItem";
//        }
//
//        itemService.updateItem(itemId, form.getName(), new BigDecimal(form.getPrice()),
//                Integer.valueOf(form.getQty()), form.getStatus(), sellerService.findById(form.getSellerId()));

        return "redirect:/notice-list";
    }


    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        String fullDir = fileUtil.getFullDir(filename);
        return new UrlResource("file:" + fullDir);
    }
}
