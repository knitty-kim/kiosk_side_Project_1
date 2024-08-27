package com.side.portfolio.demo.domain;

import com.side.portfolio.demo.status.PartnerStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.side.portfolio.demo.status.PartnerStatus.*;

@Entity
@Getter
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @Enumerated(EnumType.STRING)
    private PartnerStatus status;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //==연관관계 편의 메서드 시작==//
    public static Partner createPartner(Team team, Seller seller) {
        Partner partner = new Partner();
        partner.setUpTeam(team);
        partner.setUpSeller(seller);
        partner.setUpCreatedDate(LocalDateTime.now());
        partner.setUpPartnerStatus(CONNECTED);

        team.setUpPartner(partner);
        seller.setUpPartner(partner);

        return partner;
    }
    //==연관관계 편의 메서드 끝==//

    //==변경 메서드 시작==//
    public void setUpTeam(Team team) {
        this.team = team;
    }
    public void setUpSeller(Seller seller) {
        this.seller = seller;
    }
    public void setUpPartnerStatus(PartnerStatus partnerStatus){
        this.status = partnerStatus;
    }
    public void setUpCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    public void setUpModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    //==변경 메서드 끝==//
}
