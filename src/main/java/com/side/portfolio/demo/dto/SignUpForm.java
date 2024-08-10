package com.side.portfolio.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class SignUpForm {

    /**
     * 커맨드 객체(Command Object)
     * "VO 또는 DTO" 와 같다
     * Getter, Setter 필수
     *
     * xxForm과 같은 작명법은 화면단에서 서버로 넘어온
     * Form 객체임을 명확히 할 수 있다!
     */
    @NotBlank(message = "가입 유형은 필수입니다")
    private String types;

    @NotBlank(message = "아이디는 필수입니다")
    private String name;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    private String pw;
    
    @NotBlank(message = "연락처는 필수입니다")
    private String phNumber;

    @NotBlank(message = "메일은 필수입니다")
    private String email;

    @NotBlank(message = "City는 필수입니다")
    private String city;

    @NotBlank(message = "Street은 필수입니다")
    private String street;

    @NotBlank(message = "Zipcode는 필수입니다")
    private String zipcode;
}
