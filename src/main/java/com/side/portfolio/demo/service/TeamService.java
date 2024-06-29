package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    /**
     * 팀 가입
     * @param team
     * @return
     */
    @Transactional
    public Long signUp(Team team) {
        validateTeam(team);
        return teamRepository.save(team);
    }

    /**
     * 중복 팀 검증
     * @param team
     */
    private void validateTeam(Team team) {
        Optional<Team> byName = teamRepository.findByName(team.getName());
        if (byName.isPresent()) {
            throw new IllegalStateException("이미 존재하는 팀입니다");
        }
    }

    /**
     * 전체 팀 조회
     * @return
     */
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    /**
     * 팀 단건 조회
     * @param teamId
     * @return
     */
    public Team find(Long teamId) {
        return teamRepository.find(teamId);
    }
}
