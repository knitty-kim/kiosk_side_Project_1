package com.side.portfolio.demo.dto;

import com.side.portfolio.demo.status.TeamStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class TeamUpdateForm {

    //@NotBlank(message = "ID는 필수입니다")
    private Long id;

    //@NotBlank(message = "가입 유형은 필수입니다")
//    private String types;

    //@NotBlank(message = "이름은 필수입니다")
    private String name;

    //@NotBlank(message = "비밀번호는 필수입니다")
//    private String pw;

    //@NotBlank(message = "상태 선택은 필수입니다")
    private TeamStatus status;

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

    private int tickets;
    private String remark;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
