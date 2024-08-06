package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Cart;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.repository.CartJpaRepository;
import com.side.portfolio.demo.repository.TeamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartJpaRepository cartJpaRepository;

    @Transactional
    public void save(Cart cart) {
        cartJpaRepository.save(cart);
    }

    public List<Cart> findAll() {
        return cartJpaRepository.findAll();
    }

    //팀 ID로 조회한 장바구니 정보
    public Page<Cart> findByTeamId(Long teamId, Pageable pageable) {
        Page<Cart> carts = cartJpaRepository.findByTeam_Id(teamId, pageable);
        return carts;
    }

}
