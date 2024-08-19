package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.dto.LogInForm;
import com.side.portfolio.demo.service.LogInService;
import com.side.portfolio.demo.status.TeamStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.side.portfolio.demo.SessionConst.*;
import static com.side.portfolio.demo.status.TeamStatus.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LogInController {

    private final LogInService loginService;

    @GetMapping("/login")
    public String logInForm(Model model, @RequestParam(defaultValue = "/") String redirectURI,
                            HttpServletRequest request) {

        log.info("login Page");
        log.info("redirectURI={}", redirectURI);

        //세션에 redirectURI 를 담아 POST 까지 전달
        request.getSession().setAttribute("redirectURI", redirectURI);
        model.addAttribute("logInForm", new LogInForm());

        return "login";

    }

    @PostMapping("/login")
    public String logIn(LogInForm form, BindingResult bindingResult,
                        HttpServletRequest request) {

        String redirectURI = (String) request.getSession().getAttribute("redirectURI");
        log.info("submit logInForm!!");
        log.info("types : " + form.getTypes());
        log.info("redirectURI : " + redirectURI);

        if (form.getTypes() == null) {
            bindingResult.reject("Type is required",
                    "팀 또는 판매자를 선택해야합니다!");
            return "login";
        }

        if (form.getTypes().equals("team")) {

            Team team = loginService.teamLogin(form.getName(), form.getPw());
            if (team == null) {
                bindingResult.reject("ID or PW is Not Matched",
                        "아이디 또는 비밀번호가 맞지 않습니다!");
                return "login";
            }

            HttpSession session = request.getSession();

            //마스터로 로그인 시, status = MASTER
            if (team.getStatus() == MASTER) {
                session.setAttribute(LOGIN_TYPES, "master");
            } else {
                session.setAttribute(LOGIN_TYPES, "team");
            }

            session.setAttribute(LOGIN_ID, team.getId());
            session.setAttribute(LOGIN_NAME, team.getName());

            log.info("teamTypes : " + session.getAttribute(LOGIN_TYPES));
            log.info("teamId : " + team.getId());
            log.info("teamName : " + team.getName());

        } else if (form.getTypes().equals("seller")) {

            Seller seller = loginService.sellerLogin(form.getName(), form.getPw());
            if (seller == null) {
                bindingResult.reject("ID or PW is Not Matched",
                        "아이디 또는 비밀번호가 맞지 않습니다!");
                return "login";
            }

            HttpSession session = request.getSession();

            session.setAttribute(LOGIN_TYPES, "seller");
            session.setAttribute(LOGIN_ID, seller.getId());
            session.setAttribute(LOGIN_NAME, seller.getName());

            log.info("sellerTypes : " + session.getAttribute(LOGIN_TYPES));
            log.info("sellerId : " + seller.getId());
            log.info("sellerName : " + seller.getName());

        }

        return "redirect:" + redirectURI;

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
