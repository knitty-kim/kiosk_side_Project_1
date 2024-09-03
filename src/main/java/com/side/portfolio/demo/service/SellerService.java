package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.dto.condition.PartnerDto;
import com.side.portfolio.demo.dto.condition.PartnerSearchCond;
import com.side.portfolio.demo.dto.condition.SellerDto;
import com.side.portfolio.demo.dto.condition.SellerSearchCond;
import com.side.portfolio.demo.repository.PartnerJpaRepository;
import com.side.portfolio.demo.repository.SellerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerService {

    private final SellerJpaRepository sellerJpaRepository;
    private final PartnerJpaRepository partnerJpaRepository;

    //판매자 가입
    @Transactional
    public void signUp(Seller seller) {
        sellerJpaRepository.save(seller);
    }

    //판매자 중복 가입 검증
    private void validateSeller(Seller seller) {
        Optional<Seller> sellers = sellerJpaRepository.findByName(seller.getName());
        if (sellers.isPresent()) {
            throw new IllegalStateException("이미 존재하는 판매자입니다");
        }
    }

    //제휴 판매자 검색 조회
    public Page<PartnerDto> findPartnerByTeam_Id(Long teamId, PartnerSearchCond cond, Pageable pageable) {
        Page<PartnerDto> partners = sellerJpaRepository.searchPartnerByTeamId(teamId, cond, pageable);
        return partners;
    }

    //전체 판매자 조회
    public List<Seller> findAll() {
        return sellerJpaRepository.findAll();
    }

    //전체 판매자 검색 조회
    public Page<SellerDto> findSellerByCond(SellerSearchCond cond, Pageable pageable) {
        Page<SellerDto> sellers = sellerJpaRepository.searchSeller(cond, pageable);
        return sellers;
    }

    public Seller findById(Long sellerId) {
        return sellerJpaRepository.findById(sellerId).get();
    }

    //판매자 페이지네이션
    public Page<Seller> findByPagination(Pageable pageable) {
        Page<Seller> result = sellerJpaRepository.findAll(pageable);
        return result;
    }
    



}
