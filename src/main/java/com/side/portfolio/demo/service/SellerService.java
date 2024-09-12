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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerService {

    private final SellerJpaRepository sellerJpaRepository;
    private final PartnerJpaRepository partnerJpaRepository;

    //판매자 가입
    @Transactional
    public void save(Seller seller) {
        sellerJpaRepository.save(seller);
    }

    //판매자 Name 중복 검사
    public Map<Boolean, String> validateDuplicity(Long id, String newName) {
        Seller currSeller = sellerJpaRepository.findById(id).get();

        Map<Boolean, String> result = new HashMap<>();
        if (sellerJpaRepository.existsByName(newName)) {
            if (currSeller.getName().equals(newName)) {
                result.put(true, "사용 가능한 아이디입니다");
            } else {
                result.put(false, "이미 존재하는 아이디입니다");
            }
        } else {
            result.put(true, "사용 가능한 아이디입니다");
        }

        return result;

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
