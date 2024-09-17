package com.side.portfolio.demo.dto.condition;

import com.querydsl.core.annotations.QueryProjection;
import com.side.portfolio.demo.status.PartnerStatus;
import com.side.portfolio.demo.status.SellerStatus;
import com.side.portfolio.demo.status.TeamStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PartnerTeamDto {

    private Long teamId;
    private String teamName;
    private Long sellerId;
    private String teamPhNumber;
    private String teamEmail;
    private TeamStatus teamStatus;
    private PartnerStatus partnerStatus;
    private LocalDateTime teamCreatedDate;
    private LocalDateTime partnerCreatedDate;

    @QueryProjection
    public PartnerTeamDto(Long teamId, String teamName, Long sellerId,
                          String teamPhNumber, String teamEmail,
                          TeamStatus teamStatus, PartnerStatus partnerStatus,
                          LocalDateTime teamCreatedDate, LocalDateTime partnerCreatedDate) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.sellerId = sellerId;
        this.teamPhNumber = teamPhNumber;
        this.teamEmail = teamEmail;
        this.teamStatus = teamStatus;
        this.partnerStatus = partnerStatus;
        this.teamCreatedDate = teamCreatedDate;
        this.partnerCreatedDate = partnerCreatedDate;
    }
}
