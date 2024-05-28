package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setName("memberA");

        //when
        Long savedId = memberRepository.save(member);
        Member foundMember = memberRepository.find(savedId);

        //then
        assertThat(foundMember.getId()).isEqualTo(member.getId());
        assertThat(foundMember.getName()).isEqualTo(member.getName());
        assertThat(foundMember).isEqualTo(member);
    }

}