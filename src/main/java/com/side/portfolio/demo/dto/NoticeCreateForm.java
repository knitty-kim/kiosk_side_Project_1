package com.side.portfolio.demo.dto;

import com.side.portfolio.demo.status.ItemStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class NoticeCreateForm {

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotBlank(message = "본문은 필수입니다")
    private String content;

    private MultipartFile img1;
    private MultipartFile img2;
    private MultipartFile img3;
    private MultipartFile img4;
    private MultipartFile img5;
    private MultipartFile img6;

}
