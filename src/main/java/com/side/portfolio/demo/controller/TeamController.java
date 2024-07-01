package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/team-list")
    public String teamList(Model model) {

        model.addAttribute("teams", teamService.findAll());
        return "basic/teams";
    }
}
