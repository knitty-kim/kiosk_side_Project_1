package com.side.portfolio.demo.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class Member {

    private Long memberRId;
    //private Long associationRId;
    //private String memberId;
    //private String memberPw;
    private String memberName;
    private int memberAge;

    public Member(String memberName, int memberAge) {
        this.memberName = memberName;
        this.memberAge = memberAge;
    }

    //private String nationalNumber;
    //private String phoneNumber;
    //private String email;
    //private String grade;
    //private String status;
    //private String address;
    
    //private LocalDateTime createdDate;
    //private LocalDateTime modifiedDate;

}
