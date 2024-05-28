package com.side.portfolio.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Kiosk {

    @Id @GeneratedValue
    @Column(name = "kiosk_id")
    private Long id;

    private String remark;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    //RUNNING, BROKEN
    @Enumerated(EnumType.STRING)
    private KioskStatus status;
}
