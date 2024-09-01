package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.dto.PartnerCreateForm;
import com.side.portfolio.demo.dto.condition.TeamDto;
import com.side.portfolio.demo.dto.condition.TeamSearchCond;
import com.side.portfolio.demo.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

//    //전체 팀 목록
//    @GetMapping("/team-list")
//    public String teamList(Model model,
//                           @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
//
//        Page<Team> teams = teamService.findAll(pageable);
//        model.addAttribute("teams", teams);
//
//        model.addAttribute("prev", teams.getPageable().previousOrFirst().getPageNumber());
//        model.addAttribute("next", teams.getPageable().next().getPageNumber());
//
//        model.addAttribute("hasPrev", teams.hasPrevious());
//        model.addAttribute("hasNext", teams.hasNext());
//
//        int groupSize = 3; //화면에 보여질 페이지 개수
//        int curPageGrp = (int) Math.floor((double) teams.getNumber() / groupSize); //현재 페이지가 속한 그룹 번호
//        model.addAttribute("startPage", Math.max(0, ((curPageGrp) * groupSize)));
//        model.addAttribute("endPage", Math.min(teams.getTotalPages() - 1, ((curPageGrp + 1) * groupSize) - 1));
//
//        model.addAttribute("curPage", teams.getNumber());
//
//        return "basic/teams";
//    }

    //전체 팀 검색 조회 목록
    @GetMapping("/team-list")
    public String teamList(Model model, TeamSearchCond cond,
                           @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<TeamDto> teams = teamService.findTeamByCond(cond, pageable);
        model.addAttribute("teams", teams);

        model.addAttribute("prev", teams.getPageable().previousOrFirst().getPageNumber());
        model.addAttribute("next", teams.getPageable().next().getPageNumber());

        model.addAttribute("hasPrev", teams.hasPrevious());
        model.addAttribute("hasNext", teams.hasNext());

        int groupSize = 3; //화면에 보여질 페이지 개수
        int curPageGrp = (int) Math.floor((double) teams.getNumber() / groupSize); //현재 페이지가 속한 그룹 번호
        model.addAttribute("startPage", Math.max(0, ((curPageGrp) * groupSize)));
        model.addAttribute("endPage", Math.min(teams.getTotalPages() - 1, ((curPageGrp + 1) * groupSize) - 1));

        model.addAttribute("curPage", teams.getNumber());

        model.addAttribute("cond", cond);

        return "basic/teams";
    }

    //제휴 파트너 생성
    @ResponseBody
    @PostMapping("/partners/add")
    public List<Object> createPartner(@RequestBody PartnerCreateForm form) {
        log.info("createPartner");

        List<Object> result = new ArrayList<>();

        if (teamService.isPartner(form.getTeamId(), form.getSellerId())) {
            result.add(false);
            result.add("the seller is already connected!");
        } else {
            //제휴 파트너 생성
            teamService.createPartner(form.getTeamId(), form.getSellerId());

            result.add(true);
            result.add("/seller/partner-list/" + form.getTeamId());
        }

        return result;
    }
}
