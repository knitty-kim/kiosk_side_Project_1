package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerJpaRepository extends JpaRepository<Partner, Long> {

    List<Partner> findByTeam_IdAndSeller_Id(Long teamId, Long sellerId);
}
