package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.domain.SellerStatus;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.domain.TeamStatus;
import com.side.portfolio.demo.dto.LogInForm;
import com.side.portfolio.demo.dto.SignUpForm;
import com.side.portfolio.demo.service.LoginService;
import com.side.portfolio.demo.service.SellerService;
import com.side.portfolio.demo.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final TeamService teamService;
    private final SellerService sellerService;
    private final LoginService loginService;

    @GetMapping("/")
    public String main(HttpServletRequest request, Model model) {
        log.info("Main Controller");

        HttpSession session = request.getSession(false);
        if (loginService.validateSession(session) == false) {
            return "main";
        }

        String types = (String) session.getAttribute("types");
        Long id = (Long) session.getAttribute("id");
        String name = (String) session.getAttribute("name");

        model.addAttribute("types", types);
        model.addAttribute("id", id);
        model.addAttribute("name", name);

        return "main";
    }

    @GetMapping("/login")
    public String logInForm(Model model) {
        log.info("login Page");
        model.addAttribute("logInForm", new LogInForm());
        return "login";
    }

    @PostMapping("/login")
    public String logIn(LogInForm form, HttpServletRequest request) {
        log.info("submit logInForm!!");
        log.info("types : " + form.getTypes());

        if (form.getTypes().equals("team")) {
            Team team = loginService.teamLogin(form.getName(), form.getPw());
            HttpSession session = request.getSession();

            //마스터로 로그인 시, 세션에 types = master
            if (team.getId().equals(1L)) {
                session.setAttribute("types", "master");
            } else {
                session.setAttribute("types", "team");
            }

            log.info("teamTypes : " + session.getAttribute("types"));
            log.info("teamId : " + team.getId());
            log.info("teamName : " + team.getName());

            session.setAttribute("id", team.getId());
            session.setAttribute("name", team.getName());

        } else if (form.getTypes().equals("seller")) {
            Seller seller = loginService.sellerLogin(form.getName(), form.getPw());
            HttpSession session = request.getSession();
            log.info("sellerId : " + seller.getId());
            log.info("sellerName : " + seller.getName());
            session.setAttribute("types", "seller");
            session.setAttribute("id", seller.getId());
            session.setAttribute("name", seller.getName());

        }

        return "main";
    }

    @PostMapping("/logout")
    public String logOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        log.info("signUp Page");
        model.addAttribute("signUpForm", new SignUpForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid SignUpForm form) {
        //@Valid ; 자바 표준 검증 어노테이션
        //@Validated ; 스프링 검증 어노테이션
        if (form.getTypes().equals("team")) {

            Team team = Team.builder()
                    .name(form.getName())
                    .pw(form.getPw())
                    .phNumber(form.getPhNumber())
                    .email(form.getEmail())
                    .status(TeamStatus.DORMANT)
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
            //TODO 임시 address -> remark
                    .remark(form.getAddress())
                    .build();

            teamService.signUp(team);

            return "redirect:/";

        } else if (form.getTypes().equals("seller")) {

            Seller seller = Seller.builder()
                    .name(form.getName())
                    .pw(form.getPw())
                    .phNumber(form.getPhNumber())
                    .email(form.getEmail())
                    .status(SellerStatus.DORMANT)
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
            //TODO 임시 address -> remark
                    .remark(form.getAddress())
                    .build();

            sellerService.signUp(seller);

            return "redirect:/";

        }

        return "redirect:/";
    }

    @GetMapping("/about")
    public String about() {
        log.info("about page");
        return "about";
    }


}

