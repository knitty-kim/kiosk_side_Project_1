package com.side.portfolio.demo.repository.custom;

import com.side.portfolio.demo.dto.condition.PartnerSellerDto;
import com.side.portfolio.demo.dto.condition.PartnerSearchCond;
import com.side.portfolio.demo.dto.condition.SellerDto;
import com.side.portfolio.demo.dto.condition.SellerSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SellerCustomRepository {

//    List<PartnerDto> searchPartnerByTeamId(Long teamId);
    Page<SellerDto> searchSeller(SellerSearchCond cond, Pageable pageable);

    Page<PartnerSellerDto> searchPartnerByTeamId(Long teamId, PartnerSearchCond cond, Pageable pageable);

}
