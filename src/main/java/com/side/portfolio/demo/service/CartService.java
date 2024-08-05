package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Cart;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.repository.CartJpaRepository;
import com.side.portfolio.demo.repository.TeamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartJpaRepository cartJpaRepository;

    public List<Cart> findByTeamId(Long teamId) {
        List<Cart> carts = cartJpaRepository.findByTeam_Id(teamId);
        return carts;
    }

}
