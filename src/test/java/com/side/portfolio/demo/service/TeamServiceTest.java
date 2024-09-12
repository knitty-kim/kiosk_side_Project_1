package com.side.portfolio.demo.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.portfolio.demo.domain.*;
import com.side.portfolio.demo.repository.SellerJpaRepository;
import com.side.portfolio.demo.repository.TeamJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@Transactional
class TeamServiceTest {

    @Autowired EntityManager em;
    @Autowired TeamService teamService;
    @Autowired SellerService sellerService;
    @Autowired TeamJpaRepository teamJpaRepository;
    @Autowired SellerJpaRepository sellerJpaRepository;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void initData() {
        //팀 추가
        queryFactory = new JPAQueryFactory(em);
        Team testA = Team.builder()
                .name("testA")
                .pw("1234").build();
        Team testB = Team.builder().name("testB")
                .pw("5678")
                .build();
        teamService.save(testA);
        teamService.save(testB);

        //판매자 추가
        Seller panda = Seller.builder()
                .name("panda")
                .pw("1234")
                .build();
        Seller ari = Seller.builder()
                .name("ari")
                .pw("1234")
                .build();
        sellerService.save(panda);
        sellerService.save(ari);
    }


    @Test
    @DisplayName("초기 데이터 확인1")
    public void initTest() {
        QTeam t = new QTeam("t");
        Team foundTeam = queryFactory
                .select(t)
                .from(t)
                .where(t.name.eq("testA"))
                .fetchOne();
        assertThat(foundTeam.getName()).isEqualTo("testA");
    }

    @Test
    @DisplayName("초기 데이터 확인2")
    public void initTest2() {
        QSeller qSeller = QSeller.seller;
        Seller foundSeller = queryFactory.select(qSeller)
                .from(qSeller)
                .where(qSeller.name.eq("panda"))
                .fetchOne();
        assertThat(foundSeller.getName()).isEqualTo("panda");
    }

    @Test
    @DisplayName("파트너 추가 테스트")
    void addPartnerSeller() {
        Team team = teamJpaRepository.findByName("testA").get();
        Seller seller = sellerJpaRepository.findByName("panda").get();
        Partner partner = teamService.createPartner(team.getId(), seller.getId());

        assertThat(team.equals(partner.getTeam()));
        assertThat(seller.equals(partner.getSeller()));
    }

    //@Test
    //@DisplayName("팀 가입")
    //public void signUp() throws Exception {
        //given
        //Team team = Team.builder().name("Arizona").build();

        //when
        //teamService.signUp(team);

        //then
        //assertEquals(team, teamRepository.find(savedId));
    //}

    //@Test
    //@DisplayName("연락처 중복 팀 예외")
    //public void validateTeam() throws Exception {
        //given
        //Team team1 = Team.builder().phNumber("520-621-2211").build();
        //Team team2 = Team.builder().phNumber("520-621-2211").build();

        //when
        //eamService.signUp(team1);

        //then
        //assertThrows(IllegalStateException.class, () -> teamService.signUp(team2));
    //}

}