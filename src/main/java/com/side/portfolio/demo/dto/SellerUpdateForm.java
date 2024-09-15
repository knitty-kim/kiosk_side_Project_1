package com.side.portfolio.demo.dto;

import com.side.portfolio.demo.status.SellerStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter @Setter
public class SellerUpdateForm {

    //@NotBlank(message = "ID는 필수입니다")
    private Long id;

    //@NotBlank(message = "가입 유형은 필수입니다")
//    private String types;

    //@NotBlank(message = "이름은 필수입니다")
    private String name;

    //@NotBlank(message = "비밀번호는 필수입니다")
//    private String pw;

    //@NotBlank(message = "상태 선택은 필수입니다")
    private SellerStatus status;

    //@NotBlank(message = "연락처는 필수입니다")
    private String phNumber;

    //@NotBlank(message = "메일은 필수입니다")
    private String email;

    //@NotBlank(message = "City는 필수입니다")
    private String city;

    //@NotBlank(message = "Street은 필수입니다")
    private String street;

    //@NotBlank(message = "Zipcode는 필수입니다")
    private String zipcode;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
