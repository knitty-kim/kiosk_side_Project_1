package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

}
