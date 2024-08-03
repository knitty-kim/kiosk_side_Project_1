package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/team-list")
    public String teamList(Model model,
                           @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

//        model.addAttribute("teams", teamService.findAll());

        Page<Team> teams = teamService.findByPagination(pageable);
        model.addAttribute("teams", teams);

        model.addAttribute("prev", teams.getPageable().previousOrFirst().getPageNumber());
        model.addAttribute("next", teams.getPageable().next().getPageNumber());

        model.addAttribute("hasPrev", teams.hasPrevious());
        model.addAttribute("hasNext", teams.hasNext());

        int groupSize = 3; //화면에 보여질 페이지 개수
        int curPageGrp = (int) Math.floor((double) teams.getNumber() / groupSize); //현재 페이지가 속한 그룹 번호
        model.addAttribute("startPage", Math.max(0, ((curPageGrp) * groupSize)));
        model.addAttribute("endPage", Math.min(teams.getTotalPages() - 1, ((curPageGrp + 1) * groupSize) - 1));

        return "basic/teams";
    }
}
