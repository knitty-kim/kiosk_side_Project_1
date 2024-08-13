package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.repository.SellerJpaRepository;
import com.side.portfolio.demo.repository.SellerRepository;
import com.side.portfolio.demo.repository.TeamJpaRepository;
import com.side.portfolio.demo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final TeamRepository teamRepository;
    private final TeamJpaRepository teamJpaRepository;
    private final SellerRepository sellerRepository;
    private final SellerJpaRepository sellerJpaRepository;

    //팀 로그인
    public Team teamLogin(String name, String pw) {
        return teamJpaRepository.findByName(name)
                .filter(t -> t.getPw().equals(pw))
                .orElseThrow(() -> new NoSuchElementException("Not Found Team"));
//                .orElse(null);

//        Optional<Team> byName = teamRepository.findByName(name);
//
//        if (byName.isPresent()) {
//            Team team = byName.get();
//
//            if (team.getPw().equals(pw)) {
//                return team;
//            } else {
//                throw new IllegalStateException("비밀번호가 올바르지 않습니다");
//            }
//
//        } else {
//            throw new IllegalStateException("아이디가 없거나 올바르지 않습니다");
//        }

    }

    //판매자 로그인
    public Seller sellerLogin(String name, String pw) {

        return sellerJpaRepository.findByName(name)
                .filter(s -> s.getPw().equals(pw))
                .orElseThrow(() -> new NoSuchElementException("Not Found Seller"));
//                .orElse(null);

//        Optional<Seller> byName = sellerRepository.findByName(name);
//
//        if (byName.isPresent()) {
//            Seller seller = byName.get();
//
//            if (seller.getPw().equals(pw)) {
//                return seller;
//            } else {
//                throw new IllegalStateException("비밀번호가 올바르지 않습니다");
//            }
//
//        } else {
//            throw new IllegalStateException("아이디가 없거나 올바르지 않습니다");
//        }

    }

//    //세션 체크
//    public boolean validateSession(HttpSession session) {
//        if (session == null) {
//            log.info("Session is null");
//            return false;
//        } else if (session.getAttribute("id") == null) {
//            log.info("Id in Session is null");
//            return false;
//        } else if (session.getAttribute("types") == null) {
//            log.info("Types in Session is null");
//            return false;
//        } else if (session.getAttribute("name") == null) {
//            log.info("Name in Session is null");
//            return false;
//        }
//        return true;
//    }


}
