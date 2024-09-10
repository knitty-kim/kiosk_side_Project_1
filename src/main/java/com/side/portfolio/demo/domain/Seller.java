package com.side.portfolio.demo.domain;

import com.side.portfolio.demo.status.SellerStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long id;

    @Column(name = "seller_pw")
    private String pw;

    @Column(name = "seller_name")
    private String name;

    @Column(name = "seller_phnumber")
    private String phNumber;

    private String email;
    private String remark;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //OPEN, CLOSED, DORMANT
    @Enumerated(EnumType.STRING)
    private SellerStatus status;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "seller")
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    private List<Partner> partners = new ArrayList<>();

    public void setUpName(String name) {
        this.name = name;
    }

    public void setUpPw(String pw) {
        this.pw = pw;
    }

    public void setUpStatus(SellerStatus status) {
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

    public void setUpPartner(Partner partner) {
        partners.add(partner);
    }

    @Builder
    public Seller(String pw, String name, String phNumber,
                  String email, String remark, LocalDateTime createdDate,
                  LocalDateTime modifiedDate, SellerStatus status, Address address) {
        this.pw = pw;
        this.name = name;
        this.phNumber = phNumber;
        this.email = email;
        this.remark = remark;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.status = status;
        this.address = address;
    }
}
