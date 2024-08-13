package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.dto.LogInForm;
import com.side.portfolio.demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.side.portfolio.demo.SessionConst.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String logInForm(Model model) {
        log.info("login Page");
        model.addAttribute("logInForm", new LogInForm());
        return "login";
    }

    @PostMapping("/login")
    public String logIn(LogInForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {
        log.info("submit logInForm!!");
        log.info("types : " + form.getTypes());

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "login";
        }

        if (form.getTypes().equals("team")) {

            Team team = loginService.teamLogin(form.getName(), form.getPw());
            HttpSession session = request.getSession();

            //마스터로 로그인 시, 세션에 types = master
            if (team.getId().equals(1L)) {
                session.setAttribute(LOGIN_TYPES, "master");
            } else {
                session.setAttribute(LOGIN_TYPES, "team");
            }

            session.setAttribute(LOGIN_ID, team.getId());
            session.setAttribute(LOGIN_NAME, team.getName());

            log.info("teamTypes : " + session.getAttribute(LOGIN_TYPES));
            log.info("teamId : " + team.getId());
            log.info("teamName : " + team.getName());

            return "redirect:" + redirectURL;

        } else if (form.getTypes().equals("seller")) {

            Seller seller = loginService.sellerLogin(form.getName(), form.getPw());
            HttpSession session = request.getSession();

            session.setAttribute(LOGIN_TYPES, "seller");
            session.setAttribute(LOGIN_ID, seller.getId());
            session.setAttribute(LOGIN_NAME, seller.getName());

            log.info("sellerTypes : " + session.getAttribute(LOGIN_TYPES));
            log.info("sellerId : " + seller.getId());
            log.info("sellerName : " + seller.getName());

            return "redirect:" + redirectURL;

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
}
