package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {

}
