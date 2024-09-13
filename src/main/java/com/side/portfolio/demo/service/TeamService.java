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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    //팀 Name 중복 검사
    public Map<Boolean, String> validateName(Long id, String newName) {

        Map<Boolean, String> result = new HashMap<>();

        //newName으로 조회가 된다면
        if (teamJpaRepository.existsByName(newName)) {

            Team currTeam = teamJpaRepository.findById(id).orElse(null);

            // 회원가입인 경우 (id로 조회되지 않으면) || 팀 정보 수정 + newName이 기존 Name과 다르다면,
            if (currTeam == null || !currTeam.getName().equals(newName)) {
                result.put(false, "이미 존재하는 아이디입니다");
            } else {        //newName이 기존 Name과 같다면
                result.put(true, "사용 가능한 아이디입니다");
            }

        } else {
            result.put(true, "사용 가능한 아이디입니다");
        }

        return result;

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
