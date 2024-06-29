package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerService {

    public final SellerRepository sellerRepository;

    /**
     * 판매자 가입
     */
    @Transactional
    public Long signUp(Seller seller) {
        validateSeller(seller);
        return sellerRepository.save(seller);
    }

    /**
     * 중복 판매자 검증
     * @param seller
     */
    private void validateSeller(Seller seller) {
        Optional<Seller> byName = sellerRepository.findByName(seller.getName());
        if (byName.isPresent()) {
            throw new IllegalStateException("이미 존재하는 판매자입니다");
        }
    }

    /**
     * 전체 판매자 조회
     * @return
     */
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    /**
     * 판매자 단건 조회
     * @param sellerId
     * @return
     */
    public Seller find(Long sellerId) {
        return sellerRepository.find(sellerId);
    }
}
