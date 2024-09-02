package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Item;
import com.side.portfolio.demo.repository.custom.ItemCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemJpaRepository extends JpaRepository<Item, Long>, ItemCustomRepository {

    List<Item> findBySeller_Id(Long sellerId);
}
