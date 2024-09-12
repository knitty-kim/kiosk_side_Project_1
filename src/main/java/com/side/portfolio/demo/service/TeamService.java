package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Partner;
import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.dto.condition.TeamDto;
import com.side.portfolio.demo.dto.condition.TeamSearchCond;
import com.side.portfolio.demo.repository.PartnerJpaRepository;
import com.side.portfolio.demo.repository.SellerJpaRepository;
import com.side.portfolio.demo.repository.TeamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamJpaRepository teamJpaRepository;
    private final SellerJpaRepository sellerJpaRepository;
    private final PartnerJpaRepository partnerJpaRepository;

    //팀 가입
    @Transactional
    public void save(Team team) {
        validateTeam(team);
        teamJpaRepository.save(team);
    }

    //제휴 맺기
    @Transactional
    public Partner createPartner(Long teamId, Long sellerId) {

        Team team = teamJpaRepository.findById(teamId).get();
        Seller seller = sellerJpaRepository.findById(sellerId).get();

        //파트너 생성
        Partner partner = Partner.createPartner(team, seller);

        teamJpaRepository.save(team);

        return partner;
    }

    //상품이 장바구니에 있는지 확인
    public Boolean isPartner(Long teamId, Long sellerId) {
        List<Partner> partners = partnerJpaRepository.findByTeam_IdAndSeller_Id(teamId, sellerId);
        if (partners.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    //중복 팀 검증
    private void validateTeam(Team team) {
        Optional<Team> byName = teamJpaRepository.findByName(team.getName());
        if (byName.isPresent()) {
            throw new IllegalStateException("이미 존재하는 팀입니다");
        }
    }

    //전체 팀 조회
    public List<Team> findAll() {
        return teamJpaRepository.findAll();
    }

    //전체 팀 검색 조회
    public Page<TeamDto> findTeamByCond(TeamSearchCond cond, Pageable pageable) {
        Page<TeamDto> teams = teamJpaRepository.searchTeam(cond, pageable);
        return teams;
    }

    //팀 전체 페이징 조회
    public Page<Team> findAll(Pageable pageable) {
        Page<Team> result = teamJpaRepository.findAll(pageable);
        return result;
    }

    //팀 ID 조회
    public Team findById(Long teamId) {
        return teamJpaRepository.findById(teamId).get();
    }

    //팀 Name 조회
    public Optional<Team> findByName(String name) {
        return teamJpaRepository.findByName(name);
    }

}
