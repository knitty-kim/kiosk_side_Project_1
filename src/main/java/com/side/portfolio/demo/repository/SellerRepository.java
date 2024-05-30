package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SellerRepository {

    private final EntityManager em;

    public Long save(Seller seller) {
        em.persist(seller);
        return seller.getId();
    }

    public Seller find(Long id) {
        return em.find(Seller.class, id);
    }

    public List<Seller> findAll() {
        return em.createQuery("select s from Seller s", Seller.class)
                .getResultList();
    }

    public Optional<Seller> findByName(String name) {
        List<Seller> seller = em.createQuery("select t from Seller t where t.name = :name", Seller.class)
                .setParameter("name", name)
                .getResultList();
        return seller.isEmpty() ? Optional.empty() : Optional.of(seller.get(0));
    }

    public Optional<Seller> findByPhNumber(String phNumber) {
        List<Seller> seller = em.createQuery("select t from Seller t where t.phNumber = :phNumber", Seller.class)
                .setParameter("phNumber", phNumber)
                .getResultList();
        return seller.isEmpty() ? Optional.empty() : Optional.of(seller.get(0));
    }
}
