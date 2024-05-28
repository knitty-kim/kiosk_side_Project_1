package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.repository.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamServiceTest {

    @Autowired TeamRepository teamRepository;
    @Autowired TeamService teamService;

    @Test
    @DisplayName("팀 가입")
    public void signUp() throws Exception {
        //given
        Team team = new Team();
        team.setName("Arizona");

        //when
        Long savedId = teamService.signUp(team);

        //then
        assertEquals(team, teamRepository.find(savedId));
    }

    @Test
    @DisplayName("연락처 중복 팀 예외")
    public void validateTeam() throws Exception {
        //given
        Team team1 = new Team();
        team1.setPhNumber("520-621-2211");
        Team team2 = new Team();
        team2.setPhNumber("520-621-2211");

        //when
        teamService.signUp(team1);

        //then
        assertThrows(IllegalStateException.class, () -> teamService.signUp(team2));
    }
}