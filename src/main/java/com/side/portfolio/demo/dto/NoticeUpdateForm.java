package com.side.portfolio.demo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter @Setter
public class NoticeUpdateForm {

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotNull
    private String content;

    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private String img6;

    private MultipartFile img_1;
    private MultipartFile img_2;
    private MultipartFile img_3;
    private MultipartFile img_4;
    private MultipartFile img_5;
    private MultipartFile img_6;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
