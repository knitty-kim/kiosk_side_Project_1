package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    @DisplayName("회원가입")
    public void signUp() throws Exception {

        //Given
        Member member = new Member();
        member.setName("Robert");

        //When
        memberService.signUp(member);
        List<Member> members = memberService.findByName(member.getName());

        //Then
        assertFalse(members.isEmpty());
        assertEquals(member, members.get(0));

    }
    
    @Test
    @DisplayName("연락처 중복 회원가입 예외")
    public void validateMember() throws Exception {
        //given
        Member member1 = new Member();
        member1.setPhNumber("010-7777-7777");
        Member member2 = new Member();
        member2.setPhNumber("010-7777-7777");

        //when
        memberService.signUp(member1);

        //then 1번
//        try {
//            memberService.signUp(member2);  //예외 발생!
//        } catch (IllegalStateException e) {
//            assertEquals("이미 존재하는 회원입니다", e.getMessage());
//        }

        //then 2번
        assertThrows(IllegalStateException.class, () -> memberService.signUp(member2));
    }
}