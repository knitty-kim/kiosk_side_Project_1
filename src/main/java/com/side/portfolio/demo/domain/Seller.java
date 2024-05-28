package com.side.portfolio.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Seller {

    @Id @GeneratedValue
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
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    private List<Item> items = new ArrayList<>();

}
