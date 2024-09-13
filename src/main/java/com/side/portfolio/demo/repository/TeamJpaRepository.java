package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.repository.custom.TeamCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamJpaRepository extends JpaRepository<Team, Long>, TeamCustomRepository {

    Optional<Team> findByName(String name);
    boolean existsByName(String name);
}
