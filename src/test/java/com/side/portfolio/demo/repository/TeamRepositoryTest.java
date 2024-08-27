package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TeamRepositoryTest {

    @Autowired
    TeamJpaRepository teamJpaRepository;

    @Test
    @DisplayName("팀 저장 및 조회")
    public void saveFind() throws Exception {
        //given
        Team team = Team.builder().build();

        //when
//        Long savedId = teamRepository.save(team);
//        Team foundTeam = teamRepository.find(savedId);

        //then
//        assertThat(foundTeam.getId()).isEqualTo(team.getId());
//        assertThat(foundTeam).isEqualTo(team);
    }

    @Test
    @DisplayName("팀 전체 조회")
    public void findAll() throws Exception {
        //given
        Team team1 = Team.builder().build();
        Team team2 = Team.builder().build();
        Team team3 = Team.builder().build();
        Team team4 = Team.builder().build();

        //when
//        teamRepository.save(team1);
//        teamRepository.save(team2);
//        List<Team> teams = teamRepository.findAll();

        //then
//        assertThat(teams.size()).isEqualTo(2);
//        assertThat(teams.contains(team1)).isTrue();
//        assertThat(teams.contains(team2)).isTrue();
//        assertThat(teams.contains(team3)).isFalse();
//        assertThat(teams.contains(team4)).isFalse();

    }

    @Test
    @DisplayName("팀 연락처 조회")
    public void findByPhNumber() throws Exception {
        //given
        Team team = Team.builder()
                .phNumber("520-621-2211").build();

        //when
//        teamRepository.save(team);
//        Optional<Team> foundTeamList = teamRepository.findByPhNumber("520-621-2211");

        //then
//        foundTeamList.ifPresent(
//                t -> {
//                    assertThat(t).isEqualTo(team);
//                }
//        );

        //then
        //assertThat(foundTeamList.size()).isEqualTo(1);
        //assertThat(foundTeamList.contains(team)).isTrue();
        //assertThat(foundTeamList.get(0)).isEqualTo(team);
    }
}