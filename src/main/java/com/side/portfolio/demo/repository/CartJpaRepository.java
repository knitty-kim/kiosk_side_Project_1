package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {

    //findBy + 일에 해당하는 엔티티 + _ + 식별자
    List<Cart> findByTeam_Id(Long teamId);
}
