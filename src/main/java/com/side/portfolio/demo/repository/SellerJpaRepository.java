package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.repository.custom.SellerCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerJpaRepository extends JpaRepository<Seller, Long>, SellerCustomRepository {

    Optional<Seller> findByName(String name);
    boolean existsByName(String name);
}
