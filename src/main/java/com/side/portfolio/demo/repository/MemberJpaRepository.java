package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    List<Member> findByName(String name);
    List<Member> findByPhNumber(String phNumber);
}
