package com.side.portfolio.demo.repository.custom;

import com.side.portfolio.demo.dto.condition.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamCustomRepository {

    Page<TeamDto> searchTeam(TeamSearchCond cond, Pageable pageable);

    Page<PartnerTeamDto> searchPartnerBySellerId(Long sellerId, PartnerSearchCond cond, Pageable pageable);

}
