package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Member;
import com.side.portfolio.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {

    //컴파일 시, 세팅 강제하도록 final
    private final MemberRepository memberRepository;

    //@RequiredArgsConstructor 로 대체 가능
    @Autowired//<- 생략 가능
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /**
     * 회원가입
     */
    @Transactional(readOnly = false)
    public Long signUp(Member member) {
        validateMember(member);
        return memberRepository.save(member);
    }

    /**
     * 중복 회원 검증
     * 멀티쓰레드 환경에서 동시 회원가입 가능성 있음
     * 검사할 속성을 DB에서 Unique하게 제약할 것
     * @param member
     */
    private void validateMember(Member member) {
        List<Member> byPhNumber = memberRepository.findByPhNumber(member.getPhNumber());
        if (!byPhNumber.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    /**
     * 전체 회원 조회
     */
    private List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 한 회원 조회
     */
    private Member findMember(Long memberId) {
        return memberRepository.find(memberId);
    }
}
