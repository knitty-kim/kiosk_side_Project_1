package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.repository.custom.SellerCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SellerJpaRepository extends JpaRepository<Seller, Long>, SellerCustomRepository {

//    List<Seller> findByName(String name);
    Optional<Seller> findByName(String name);

}
