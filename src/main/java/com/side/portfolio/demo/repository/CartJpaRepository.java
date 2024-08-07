package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {

    Page<Cart> findByTeam_Id(Long teamId, Pageable pageable);
    List<Cart> findByTeam_Id(Long teamId);
    List<Cart> findByTeam_IdAndItem_Id(Long teamId, Long itemId);
}
