package com.side.portfolio.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LogInForm {

    private String types;

//    @NotBlank(message = "아이디는 필수입니다")
    private String name;

//    @NotBlank(message = "비밀번호는 필수입니다")
    private String pw;

}
