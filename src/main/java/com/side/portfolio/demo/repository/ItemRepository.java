package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    /**
     * 상품 저장
     * @param item
     */
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    /**
     * 상품 전체 조회
     * @return
     */
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    /**
     * 페이지네이션
     * @param limit
     * @return
     */
//    public List<Item> findByPagination(int offset, int limit) {
//        return em.createQuery("select i from Item i order by i.id asc")
//                .setFirstResult(offset)
//                .setMaxResults(limit)
//                .getResultList();
//    }

    /**
     * 상품 단건 조회
     * @param id
     * @return
     */
    public Item find(Long id) {
        return em.find(Item.class, id);
    }

    /**
     * 아이템 카운트
     * @return
     */
    public Long count() {
        return em.createQuery("select count(i) from Item i", Long.class)
                .getSingleResult();
    }
}

