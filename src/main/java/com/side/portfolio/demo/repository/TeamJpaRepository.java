package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName(String name);
}
