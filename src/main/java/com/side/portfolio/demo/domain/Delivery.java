package com.side.portfolio.demo.domain;

import com.side.portfolio.demo.status.DeliveryStatus;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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

    public void setUpAddress(Address address) {
        this.address = address;
    }

    public void setUpOrder(Order order) {
        this.order = order;
    }

    public void setUpStatus(DeliveryStatus status) {
        this.status = status;
    }
}
