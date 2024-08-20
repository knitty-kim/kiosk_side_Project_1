package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Notice;
import com.side.portfolio.demo.dto.NoticeCreateForm;
import com.side.portfolio.demo.dto.NoticeUpdateForm;
import com.side.portfolio.demo.service.NoticeService;
import com.side.portfolio.demo.service.FileService;
import com.side.portfolio.demo.domain.FileNameTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final FileService fileService;

    //공지 리스트
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

    //공지 상세
    @GetMapping("/notice-list/{noticeId}")
    public String noticeDetail(@PathVariable Long noticeId, Model model) {

        Notice notice = noticeService.findById(noticeId);
        model.addAttribute(notice);

        return "basic/notice";
    }

    //공지 등록 폼
    @GetMapping("/notice/add")
    public String createNoticeForm(Model model) {

        model.addAttribute("noticeCreateForm", new NoticeCreateForm());

        return "basic/addNotice";
    }

    //공지 등록
    @PostMapping("/notice/add")
    public String createNotice(Model model, @Validated NoticeCreateForm form,
                               BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "basic/addNotice";
        }

        Notice notice = Notice.builder()
                .title(form.getTitle())
                .content(form.getContent())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
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
                    Method setter = notice.getClass().getMethod("set" + fieldName, String.class);
                    setter.invoke(notice, uuidFileName);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        noticeService.createNotice(notice);
        return "redirect:/notice-list/" + notice.getId();

    }

    //공지 수정 폼
    @GetMapping("/notice/update/{noticeId}")
    public String updateNoticeForm(@PathVariable Long noticeId, Model model) {

        Notice notice = noticeService.findById(noticeId);
        NoticeUpdateForm form = new NoticeUpdateForm();

        form.setContent(notice.getContent());
        form.setTitle(notice.getTitle());
        form.setCreatedDate(notice.getCreatedDate());
        form.setModifiedDate(notice.getModifiedDate());

        try {
            // Java Reflection API
            // 순회할 필드 이름 목록
            String[] fieldNames = {"Img1", "Img2", "Img3", "Img4", "Img5", "Img6"};

            for (String fieldName : fieldNames) {
                // notice.getImgX() 메서드 동적 호출
                Method getter = notice.getClass().getMethod("get" + fieldName);
                String uuid = (String) getter.invoke(notice);

                if (uuid != null) {
                    // 업로드 파일 이름 찾기
                    String uploadFileName = fileService.findTableByUuid(uuid).getUploadFileName();

                    // NoticeUpdateForm 에 업로드 파일명 세팅
                    Method setter = form.getClass().getMethod("set" + fieldName, String.class);
                    setter.invoke(form, uploadFileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("noticeUpdateForm", form);

        return "basic/updateNotice";
    }

    //공지 수정
    @PostMapping("/notice/update/{noticeId}")
    public String updateNotice(Model model, @PathVariable Long noticeId,
                               String deleteImages,
                               @Validated NoticeUpdateForm form,
                               BindingResult bindingResult) {

        log.info("form={}", form.toString());
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "basic/updateNotice";
        }

        Notice notice = noticeService.findById(noticeId);

        if (deleteImages != null && !deleteImages.isEmpty()) {
            String[] deleteImageIds = deleteImages.split(",");

            for (String imgId : deleteImageIds) {
                try {
                    Method getter = notice.getClass().getMethod("get" + imgId);
                    String uuid = (String) getter.invoke(notice);

                    Method setter = notice.getClass().getMethod("set" + imgId, String.class);
                    setter.invoke(notice, (String) null);

                    noticeService.createNotice(notice);
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

                    Method setter = notice.getClass().getMethod("set" + modifiedName, String.class);
                    setter.invoke(notice, uuidFileName);

                    noticeService.createNotice(notice);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/notice-list/" + noticeId;

    }

    //공지 삭제
    @GetMapping("/notice/delete/{noticeId}")
    public String deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteById(noticeId);
        return "redirect:/notice-list";
    }

    //공지사항 상세 클릭 시, 이미지 파일 불러오기
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        String fullDir = fileService.getFullDir(filename);
        return new UrlResource("file:" + fullDir);
    }
}
