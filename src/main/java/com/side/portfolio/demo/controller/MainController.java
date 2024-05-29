package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.SignUpForm;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.domain.TeamStatus;
import com.side.portfolio.demo.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@Controller
public class MainController {

    @Autowired
    TeamService teamService;


    @GetMapping("/")
    public String main() {
        log.info("Home Controller Main");
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        log.info("login Page");
        return "login";
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

            Team team = new Team();
            team.setName(form.getId());
            team.setPw(form.getPw());
            team.setPhNumber(form.getPhNumber());
            team.setEmail(form.getEmail());
            team.setStatus(TeamStatus.DORMANT);
            team.setCreatedDate(LocalDateTime.now());
            team.setModifiedDate(LocalDateTime.now());

            //임시 address -> remark
            team.setRemark(form.getAddress());

            teamService.signUp(team);

            return "redirect:/";

        } else if (form.getTypes().equals("seller")) {

            return "redirect:/";
        }

        return "redirect:/";
    }
}

