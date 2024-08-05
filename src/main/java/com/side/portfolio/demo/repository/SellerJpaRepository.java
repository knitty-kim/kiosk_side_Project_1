package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerJpaRepository extends JpaRepository<Seller, Long> {

}
