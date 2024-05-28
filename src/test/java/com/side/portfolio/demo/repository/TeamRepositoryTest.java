package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;

    @Test
    @DisplayName("팀 저장 및 조회")
    public void saveFind() throws Exception {
        //given
        Team team = new Team();

        //when
        Long savedId = teamRepository.save(team);
        Team foundTeam = teamRepository.find(savedId);

        //then
        assertThat(foundTeam.getId()).isEqualTo(team.getId());
        assertThat(foundTeam).isEqualTo(team);
    }

    @Test
    @DisplayName("팀 전체 조회")
    public void findAll() throws Exception {
        //given
        Team team1 = new Team();
        Team team2 = new Team();
        Team team3 = new Team();
        Team team4 = new Team();

        //when
        teamRepository.save(team1);
        teamRepository.save(team2);
        List<Team> teams = teamRepository.findAll();

        //then
        assertThat(teams.size()).isEqualTo(2);
        assertThat(teams.contains(team1)).isTrue();
        assertThat(teams.contains(team2)).isTrue();
        assertThat(teams.contains(team3)).isFalse();
        assertThat(teams.contains(team4)).isFalse();

    }

    @Test
    @DisplayName("팀 연락처 조회")
    public void findByPhNumber() throws Exception {
        //given
        Team team = new Team();
        team.setPhNumber("520-621-2211");

        //when
        teamRepository.save(team);
        List<Team> foundTeamList = teamRepository.findByPhNumber("520-621-2211");

        //then
        assertThat(foundTeamList.size()).isEqualTo(1);
        assertThat(foundTeamList.contains(team)).isTrue();
        assertThat(foundTeamList.get(0)).isEqualTo(team);
    }
}