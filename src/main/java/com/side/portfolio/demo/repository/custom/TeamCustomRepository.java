package com.side.portfolio.demo.repository.custom;

import com.side.portfolio.demo.dto.condition.TeamDto;
import com.side.portfolio.demo.dto.condition.TeamSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamCustomRepository {

    Page<TeamDto> searchTeam(TeamSearchCond cond, Pageable pageable);
}
