package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.*;
import com.side.portfolio.demo.dto.SignUpForm;
import com.side.portfolio.demo.service.SellerService;
import com.side.portfolio.demo.service.TeamService;
import com.side.portfolio.demo.status.SellerStatus;
import com.side.portfolio.demo.status.TeamStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final TeamService teamService;
    private final SellerService sellerService;
//    private final LoginService loginService;

    @GetMapping("/")
    public String main(HttpServletRequest request) {
        log.info("Main Controller");

//        HttpSession session = request.getSession(false);
//
//        Long id = (Long) session.getAttribute(LOGIN_ID);
//        String types = (String) session.getAttribute(LOGIN_TYPES);
//        String name = (String) session.getAttribute(LOGIN_NAME);
//
//        if (session == null || id == null || types == null || name == null) {
//            return "main";
//        }

        return "main";
    }

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        log.info("signUp Page");
        model.addAttribute("signUpForm", new SignUpForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid SignUpForm form) {

        if (form.getTypes().equals("team")) {

            Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

            Team team = Team.builder()
                    .name(form.getName())
                    .pw(form.getPw())
                    .phNumber(form.getPhNumber())
                    .email(form.getEmail())
                    .status(TeamStatus.DORMANT)
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .address(address)
                    .build();

            teamService.signUp(team);

            return "redirect:/";

        } else if (form.getTypes().equals("seller")) {

            Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

            Seller seller = Seller.builder()
                    .name(form.getName())
                    .pw(form.getPw())
                    .phNumber(form.getPhNumber())
                    .email(form.getEmail())
                    .status(SellerStatus.DORMANT)
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .address(address)
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

