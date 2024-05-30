package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.repository.SellerRepository;
import com.side.portfolio.demo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final TeamRepository teamRepository;
    private final SellerRepository sellerRepository;

    public Team teamLogin(String name, String pw) {
        Optional<Team> byName = teamRepository.findByName(name);

        if (byName.isPresent()) {
            Team team = byName.get();

            if (team.getPw().equals(pw)) {
                return team;
            } else {
                throw new IllegalStateException("비밀번호가 올바르지 않습니다");
            }

        } else {
            throw new IllegalStateException("아이디가 없거나 올바르지 않습니다");
        }

    }

    public Seller sellerLogin(String name, String pw) {
        Optional<Seller> byName = sellerRepository.findByName(name);

        if (byName.isPresent()) {
            Seller seller = byName.get();

            if (seller.getPw().equals(pw)) {
                return seller;
            } else {
                throw new IllegalStateException("비밀번호가 올바르지 않습니다");
            }

        } else {
            throw new IllegalStateException("아이디가 없거나 올바르지 않습니다");
        }

    }

    public boolean validateUser(String name) {

        Optional<Team> byNameTeam = teamRepository.findByName(name);
        Optional<Seller> byNameSeller = sellerRepository.findByName(name);

        if (byNameTeam.isPresent() || byNameSeller.isPresent()) {
            return true;
        } else {
            return false;
        }

    }

}
