package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {

    List<Item> findBySeller_Id(Long sellerId);
}
