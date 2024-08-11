//package com.side.portfolio.demo.repository;
//
//import com.side.portfolio.demo.domain.Member;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//@Repository
//public class MemberRepository {
//
//    //em에 final 붙이고 @RequiredArgsConstructor 로 생성자 대체 가능
//    @PersistenceContext//스프링부트가 @Autowired 로 대체 가능하게 해줌
//    private EntityManager em;
//
//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }
//
//    public Long save(Member member) {
//        em.persist(member);
//        return member.getId();
//    }
//
//    //단건 조회 ; em.find(type, PK)
//    public Member find(Long id) {
//        return em.find(Member.class, id);
//    }
//
//    //전체 조회
//    //JPQL ; 객체에 대한 쿼리
//    public List<Member> findAll(){
//        return em.createQuery("select m from Member m", Member.class)
//                .getResultList();
//    }
//
//    //JPQL ; 쿼리에 파라미터 바인딩
//    public List<Member> findByPhNumber(String phNumber) {
//        return em.createQuery("select m from Member m where m.phNumber = :phNumber", Member.class)
//                .setParameter("phNumber", phNumber)
//                .getResultList();
//    }
//}
