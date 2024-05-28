package com.side.portfolio.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    //PROCESSED, DELIVERING, DELIVERED, CANCELED
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

}
