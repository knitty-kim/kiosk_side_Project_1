package com.side.portfolio.demo.repository.custom;

import com.side.portfolio.demo.dto.condition.PartnerDto;
import com.side.portfolio.demo.dto.condition.SellerDto;
import com.side.portfolio.demo.dto.condition.SellerSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SellerCustomRepository {

    List<PartnerDto> searchPartnerByTeamId(Long teamId);
    Page<SellerDto> searchPage(SellerSearchCond cond, Pageable pageable);

}