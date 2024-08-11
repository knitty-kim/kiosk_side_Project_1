package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {

}
