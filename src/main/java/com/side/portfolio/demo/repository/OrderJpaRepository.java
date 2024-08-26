package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Order;
import com.side.portfolio.demo.repository.custom.OrderCustomRepository;
import com.side.portfolio.demo.status.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJpaRepository extends JpaRepository<Order, Long>, OrderCustomRepository {

    Page<Order> findByTeam_Id(Long teamId, Pageable pageable);
    List<Order> findByStatus(OrderStatus status);
}
