package com.side.portfolio.demo.domain;

import com.side.portfolio.demo.exception.NotEnoughStockException;
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

    //관리자만 수정할 수 있는 정보
    @Column(name = "team_tickets")
    private int tickets;

    @Column(name = "team_phnumber")
    private String phNumber;

    private String email;

    //관리자만 수정할 수 있는 정보
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

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Partner> partners = new ArrayList<>();

    public void setUpName(String name) {
        this.name = name;
    }

    public void setUpPw(String pw) {
        this.pw = pw;
    }

    public void setUpStatus(TeamStatus status) {
        this.status = status;
    }

    public void setUpPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public void setUpEmail(String email) {
        this.email = email;
    }

    public void setUpAddress(Address address) {
        this.address = address;
    }

    public void setUpModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setUpTickets(int tickets) {
        if (tickets < 0) {
            throw new NotEnoughStockException("quantity can't be below ZERO");
        }
        this.tickets = tickets;
    }

    public void addTickets(int tickets) {
        this.tickets += tickets;
        this.modifiedDate = LocalDateTime.now();
    }

    public void subtractTickets(int tickets) {
        int tempTickets = this.tickets - tickets;
        if (tempTickets < 0) {
            throw new NotEnoughStockException("can't subtract Quantity below ZERO");
        }
        this.tickets = tempTickets;
        this.modifiedDate = LocalDateTime.now();
    }

    public void setUpRemark(String remark) {
        this.remark = remark;
    }

    public void setUpPartner(Partner partner) {
        partners.add(partner);
    }



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
