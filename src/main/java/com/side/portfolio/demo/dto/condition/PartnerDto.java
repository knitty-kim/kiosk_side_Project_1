package com.side.portfolio.demo.dto.condition;

import com.querydsl.core.annotations.QueryProjection;
import com.side.portfolio.demo.status.PartnerStatus;
import com.side.portfolio.demo.status.SellerStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PartnerDto {

    private Long sellerId;
    private String sellerName;
    private Long teamId;
    private String sellerPhNumber;
    private String sellerEmail;
    private SellerStatus sellerStatus;
    private PartnerStatus partnerStatus;
    private LocalDateTime sellerCreatedDate;
    private LocalDateTime partnerCreatedDate;

    @QueryProjection
    public PartnerDto(Long sellerId, String sellerName, Long teamId,
                      String sellerPhNumber, String sellerEmail,
                      SellerStatus sellerStatus, PartnerStatus partnerStatus,
                      LocalDateTime sellerCreatedDate, LocalDateTime partnerCreatedDate) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.teamId = teamId;
        this.sellerPhNumber = sellerPhNumber;
        this.sellerEmail = sellerEmail;
        this.sellerStatus = sellerStatus;
        this.partnerStatus = partnerStatus;
        this.sellerCreatedDate = sellerCreatedDate;
        this.partnerCreatedDate = partnerCreatedDate;
    }
}
