package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeJpaRepository extends JpaRepository<Notice, Long> {

}
