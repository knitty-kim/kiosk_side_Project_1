package com.side.portfolio.demo.dto;

import com.side.portfolio.demo.status.ItemStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter @Setter
public class ItemUpdateForm {

    //@NotBlank(message = "상품 ID는 필수입니다")
    private String id;

    @NotBlank(message = "상품명은 필수입니다")
    private String name;

    @NotNull(message = "가격은 0 이상이어야 합니다")
    @Range(min = 0, max = 9999)
    private String price;

    @NotNull(message = "수량은 0 이상이어야 합니다")
    @Range(min = 0, max = 9999)
    private String qty;

    @NotNull(message = "상태가 선택되어야 합니다")
    private ItemStatus status;

    @NotNull(message = "판매자가 선택되어야 합니다")
    private Long sellerId;

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
