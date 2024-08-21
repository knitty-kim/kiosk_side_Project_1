package com.side.portfolio.demo.dto;

import com.side.portfolio.demo.status.ItemStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ItemCreateForm {

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

    private MultipartFile img1;
    private MultipartFile img2;
    private MultipartFile img3;
    private MultipartFile img4;
    private MultipartFile img5;
    private MultipartFile img6;
}
