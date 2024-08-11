package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.repository.TeamJpaRepository;
import com.side.portfolio.demo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamJpaRepository teamJpaRepository;

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

    //팀 전체 조회
    public List<Team> findAll() {
        return teamJpaRepository.findAll();
    }

    //팀 전체 페이징 조회
    public Page<Team> findAll(Pageable pageable) {
        Page<Team> result = teamJpaRepository.findAll(pageable);
        return result;
    }

    /**
     * 팀 단건 조회
     * @param teamId
     * @return
     */
    public Team findById(Long teamId) {
        return teamJpaRepository.findById(teamId).get();
    }


}
