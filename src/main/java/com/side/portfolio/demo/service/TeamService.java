package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.repository.TeamJpaRepository;
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

    private final TeamJpaRepository teamJpaRepository;

    //팀 가입
    @Transactional
    public void signUp(Team team) {
        validateTeam(team);
        teamJpaRepository.save(team);
    }

    //중복 팀 검증
    private void validateTeam(Team team) {
        Optional<Team> byName = teamJpaRepository.findByName(team.getName());
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

    //팀 ID 조회
    public Team findById(Long teamId) {
        return teamJpaRepository.findById(teamId).get();
    }

    //팀 Name 조회
    public Optional<Team> findByName(String name) {
        return teamJpaRepository.findByName(name);
    }

}
