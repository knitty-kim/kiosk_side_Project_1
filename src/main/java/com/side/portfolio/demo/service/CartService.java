package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Cart;
import com.side.portfolio.demo.repository.CartJpaRepository;
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

    //팀 ID로 장바구니 페이징 조회
    public Page<Cart> findByTeamId(Long teamId, Pageable pageable) {
        Page<Cart> carts = cartJpaRepository.findByTeam_Id(teamId, pageable);
        return carts;
    }
    
    //팀 ID로 장바구니 조회
    public List<Cart> findByTeamId(Long teamId) {
        List<Cart> carts = cartJpaRepository.findByTeam_Id(teamId);
        return carts;
    }

    //상품이 이미 담겼는지 확인
    public Boolean isInCart(Long teamId, Long ItemId) {
        List<Cart> carts = cartJpaRepository.findByTeam_IdAndItem_Id(teamId, ItemId);
        if (carts.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public void deleteById(Long cartId) {
        cartJpaRepository.deleteById(cartId);
    }

}
