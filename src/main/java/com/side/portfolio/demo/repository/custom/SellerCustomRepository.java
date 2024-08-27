package com.side.portfolio.demo.repository.custom;

import com.side.portfolio.demo.dto.condition.PartnerDto;

import java.util.List;

public interface SellerCustomRepository {

    List<PartnerDto> searchPartnerByTeamId(Long teamId);
}
