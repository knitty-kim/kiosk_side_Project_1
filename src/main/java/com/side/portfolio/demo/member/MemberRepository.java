package com.side.portfolio.demo.member;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음
 * 추후 ConcurrentHashMap, AtomicLong 사용 고려
 */
@NoArgsConstructor
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }
    
    //멤버를 저장소에 저장하기
    public Member save(Member member) {
        member.setMemberRId(++sequence);
        store.put(member.getMemberRId(), member);
        return member;
    }

    //아이디로 멤버 찾기
    public Member findById(Long id) {
        return store.get(id);
    }

    //모든 멤버 찾기
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    
    //저장소 비우기
    public void clearStore() {
        store.clear();
    }
}
