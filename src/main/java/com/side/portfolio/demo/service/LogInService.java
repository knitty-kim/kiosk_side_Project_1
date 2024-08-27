package com.side.portfolio.demo.service;

import com.side.portfolio.demo.Message;
import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.repository.SellerJpaRepository;
import com.side.portfolio.demo.repository.TeamJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogInService {

    private final TeamJpaRepository teamJpaRepository;
    private final SellerJpaRepository sellerJpaRepository;

    //팀 로그인
    public Team teamLogin(String name, String pw) {
        return teamJpaRepository.findByName(name)
                .filter(t -> t.getPw().equals(pw))
                .orElse(null);
//                .orElseThrow(() -> new NoSuchElementException("Not Found Team"));

    }

    //판매자 로그인
    public Seller sellerLogin(String name, String pw) {

        return sellerJpaRepository.findByName(name)
                .filter(s -> s.getPw().equals(pw))
                .orElse(null);
//                .orElseThrow(() -> new NoSuchElementException("Not Found Seller"));

    }

    public Boolean invalidAccess(HttpSession session, Long inputId) {
        Long sessionId = (Long) session.getAttribute("id");

        if (sessionId != inputId) {
            return true;
        } else {
            return false;
        }

    }

}
