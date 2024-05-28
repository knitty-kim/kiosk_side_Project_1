package com.side.portfolio.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_pw")
    private String pw;

    @Column(name = "team_name")
    private String name;

    @Column(name = "team_tickets")
    private int tickets;

    @Column(name = "team_phnumber")
    private String phNumber;

    private String email;
    private String remark;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //ACTIVE, DORMANT
    @Enumerated(EnumType.STRING)
    private TeamStatus status;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Order> kiosks = new ArrayList<>();

}
