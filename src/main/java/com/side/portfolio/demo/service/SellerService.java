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
    public Map<Boolean, String> validateName(Long id, String newName) {

        Map<Boolean, String> result = new HashMap<>();

        //newName으로 조회가 된다면
        if (sellerJpaRepository.existsByName(newName)) {

            Seller currSeller = sellerJpaRepository.findById(id).orElse(null);

            // 회원가입인 경우 (id로 조회되지 않으면) || 판매자 정보 수정 + newName이 기존 Name과 다르다면,
            if (currSeller == null || !currSeller.getName().equals(newName)) {
                result.put(false, "이미 존재하는 아이디입니다");
            } else {        //newName이 기존 Name과 같다면
                result.put(true, "사용 가능한 아이디입니다");
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
