package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Cart;
import com.side.portfolio.demo.repository.CartJpaRepository;
import com.side.portfolio.demo.repository.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartJpaRepository cartJpaRepository;
    private final ItemJpaRepository itemJpaRepository;

    @Transactional
    public void createCart(Cart cart) {
        cartJpaRepository.save(cart);
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

    //상품이 장바구니에 있는지 확인
    public Boolean isInCart(Long teamId, Long itemId) {
        List<Cart> carts = cartJpaRepository.findByTeam_IdAndItem_Id(teamId, itemId);
        if (carts.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    //상품 하나 수량 변경
    @Transactional
    public void updateCartQty(Long cartId, Integer qty) {
        Cart cart = cartJpaRepository.findById(cartId).get();
        cart.setUpQty(qty);
        cart.setUpPrice(cart.getItem()
                .getPrice().multiply(new BigDecimal(qty)).setScale(2, RoundingMode.CEILING));
        cart.setUpModifiedDate(LocalDateTime.now());
        cartJpaRepository.save(cart);
    }

    //장바구니 제거
    @Transactional
    public void deleteById(Long cartId) {
        cartJpaRepository.deleteById(cartId);
    }

    //팀 ID의 모든 장바구니 제거
    @Transactional
    public void deleteAllByTeamId(Long teamId) {
        cartJpaRepository.deleteAllByTeam_Id(teamId);
    }

    //DB 내 장바구니 전체 조회
    public List<Cart> findAll() {
        return cartJpaRepository.findAll();
    }

}
