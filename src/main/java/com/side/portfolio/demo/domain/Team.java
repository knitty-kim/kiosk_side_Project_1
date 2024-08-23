package com.side.portfolio.demo.domain;

import com.side.portfolio.demo.status.TeamStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    //ACTIVE, DORMANT, MASTER
    @Enumerated(EnumType.STRING)
    private TeamStatus status;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Kiosk> kiosks = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Cart> carts = new ArrayList<>();

    @Builder
    public Team(String pw, String name, int tickets, String phNumber,
                String email, String remark, LocalDateTime createdDate,
                LocalDateTime modifiedDate, TeamStatus status, Address address) {
        this.pw = pw;
        this.name = name;
        this.tickets = tickets;
        this.phNumber = phNumber;
        this.email = email;
        this.remark = remark;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.status = status;
        this.address = address;
    }
}
