package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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

    //name 조건 조회
    public Optional<Team> findByName(String name) {
        List<Team> team = em.createQuery("select t from Team t where t.name = :name", Team.class)
                .setParameter("name", name)
                .getResultList();
        return team.isEmpty() ? Optional.empty() : Optional.of(team.get(0));
    }

    //phNumber 조건 조회
    public Optional<Team> findByPhNumber(String phNumber) {
        List<Team> team = em.createQuery("select t from Team t where t.phNumber = :phNumber", Team.class)
                .setParameter("phNumber", phNumber)
                .getResultList();
        return team.isEmpty() ? Optional.empty() : Optional.of(team.get(0));
    }
}
