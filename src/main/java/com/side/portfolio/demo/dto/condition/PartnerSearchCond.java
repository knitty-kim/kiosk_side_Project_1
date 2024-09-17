package com.side.portfolio.demo.dto.condition;

import com.side.portfolio.demo.status.SellerStatus;
import com.side.portfolio.demo.status.TeamStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PartnerSearchCond {

    private Long id;
    private String name;
    private String phNumber;
    private String email;
    private SellerStatus sellerStatus;
    private TeamStatus teamStatus;
}
