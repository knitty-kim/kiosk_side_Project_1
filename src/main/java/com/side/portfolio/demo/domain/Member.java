package com.side.portfolio.demo.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_pw")
    private String pw;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_tickets")
    private int tickets;

    @Column(name = "member_phnumber")
    private String phNumber;

    private String email;
    private String remark;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //ACTIVE, DORMANT
    //@ColumnDefault("DORMANT")   //테이블 생성 시, 기본값 설정 //에러 발생
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

}

