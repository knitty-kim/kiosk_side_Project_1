package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Member;
import com.side.portfolio.demo.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;

    /**
     * 회원가입
     */
    @Transactional
    public void signUp(Member member) {
        memberJpaRepository.save(member);
    }

    /**
     * 중복 회원 검증
     * 멀티쓰레드 환경에서 동시 회원가입 가능성 있음
     * 검사할 속성을 DB에서 Unique하게 제약할 것
     * @param member
     */
    private void validateMember(Member member) {
        List<Member> byPhNumber = memberJpaRepository.findByPhNumber(member.getPhNumber());
        if (!byPhNumber.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    public List<Member> findAll() {
        return memberJpaRepository.findAll();
    }

    public Member findById(Long memberId) {
        return memberJpaRepository.findById(memberId).get();
    }

    public List<Member> findByName(String name) {
        return memberJpaRepository.findByName(name);
    }

    public List<Member> findByPhNumber(String phNumber) {
        return memberJpaRepository.findByPhNumber(phNumber);
    }
}
