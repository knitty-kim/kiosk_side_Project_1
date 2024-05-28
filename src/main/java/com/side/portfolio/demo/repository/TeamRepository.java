package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamRepository {

    private final EntityManager em;

    //저장
    public Long save(Team team) {
        em.persist(team);
        return team.getId();
    }

    //단건 조회
    public Team find(Long id) {
        return em.find(Team.class, id);
    }

    //전체 조회
    public List<Team> findAll() {
        return em.createQuery("select t from Team t", Team.class)
                .getResultList();
    }

    //조건 조회
    //임시로 List 리턴
    public List<Team> findByPhNumber(String phNumber) {
        return em.createQuery("select t from Team t where t.phNumber = :phNumber", Team.class)
                .setParameter("phNumber", phNumber)
                .getResultList();
    }
}
