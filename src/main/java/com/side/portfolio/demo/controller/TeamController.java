package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.Message;
import com.side.portfolio.demo.domain.Address;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.dto.PartnerCreateForm;
import com.side.portfolio.demo.dto.TeamUpdateForm;
import com.side.portfolio.demo.dto.condition.*;
import com.side.portfolio.demo.service.LogInService;
import com.side.portfolio.demo.service.TeamService;
import com.side.portfolio.demo.status.TeamStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final LogInService logInService;

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
            result.add("이미 제휴된 판매자입니다");
        } else {
            //제휴 파트너 생성
            teamService.createPartner(form.getTeamId(), form.getSellerId());

            result.add(true);
            result.add("/seller/partner-list/" + form.getTeamId());
        }

        return result;
    }

    //제휴 팀 목록
    @GetMapping("/partner-list/{sellerId}")
    public String partnerTeamList(Model model, HttpServletRequest request,
                              @PathVariable Long sellerId, PartnerSearchCond cond,
                              @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        if (logInService.invalidAccess(request.getSession(), sellerId)) {
            model.addAttribute("data", new Message("잘못된 접근입니다!", "/"));
            return "message";
        }

        Page<PartnerTeamDto> partners = teamService.findPartnerBySeller_Id(sellerId, cond, pageable);
        model.addAttribute("partners", partners);

        model.addAttribute("prev", partners.getPageable().previousOrFirst().getPageNumber());
        model.addAttribute("next", partners.getPageable().next().getPageNumber());

        model.addAttribute("hasPrev", partners.hasPrevious());
        model.addAttribute("hasNext", partners.hasNext());

        int groupSize = 3; //화면에 보여질 페이지 개수
        int curPageGrp = (int) Math.floor((double) partners.getNumber() / groupSize); //현재 페이지가 속한 그룹 번호
        model.addAttribute("startPage", Math.max(0, ((curPageGrp) * groupSize)));

        int endPage = Math.min(partners.getTotalPages() - 1, ((curPageGrp + 1) * groupSize) - 1);
        if (endPage == -1) {
            endPage = 0;
        }

        model.addAttribute("endPage", endPage);
        model.addAttribute("curPage", partners.getNumber());
        model.addAttribute("cond", cond);

        return "basic/partnerTeams";
    }

    //팀 수정 폼
    @GetMapping("/update/{teamId}")
    public String updateTeamForm(@PathVariable Long teamId, Model model) {

        Team team = teamService.findById(teamId);
        TeamUpdateForm form = new TeamUpdateForm();
        form.setId(teamId);
        form.setName(team.getName());
//        form.setPw(seller.getPw());
        form.setStatus(team.getStatus());
        form.setPhNumber(team.getPhNumber());
        form.setEmail(team.getEmail());

        Address address = team.getAddress();
        form.setCity(address.getCity());
        form.setStreet(address.getStreet());
        form.setZipcode(address.getZipcode());

        form.setCreatedDate(team.getCreatedDate());
        form.setModifiedDate(team.getModifiedDate());

        //관리자만 수정할 수 있는 정보 ; tickets, remark
        form.setTickets(team.getTickets());
        form.setRemark(team.getRemark());

        model.addAttribute("teamStatuses", TeamStatus.values());
        model.addAttribute("teamUpdateForm", form);
        model.addAttribute("teamId", teamId);

        return "basic/updateTeam";
    }

    //팀 수정
    @PostMapping("/update/{teamId}")
    public String updateTeam(Model model, @PathVariable Long teamId,
                               @ModelAttribute TeamUpdateForm form,
                               BindingResult bindingResult) {

        if (formHasError(form, bindingResult)) {
            model.addAttribute("teamStatuses", TeamStatus.values());
            return "basic/updateTeam";
        }

        Team team = teamService.findById(teamId);
        team.setUpName(form.getName());
        team.setUpStatus(form.getStatus());
        team.setUpPhNumber(form.getPhNumber());
        team.setUpEmail(form.getEmail());

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        team.setUpAddress(address);
        team.setUpModifiedDate(LocalDateTime.now());

        //관리자만 수정할 수 있는 정보 ; tickets, remark
        team.setUpTickets(form.getTickets());
        team.setUpRemark(form.getRemark());

        teamService.save(team);

        return "redirect:/team/update/" + teamId;

    }

    //팀 비밀번호 변경
    @ResponseBody
    @PostMapping("/updatePw")
    public String updatePw(Long id, String pw, HttpServletRequest request) {

        Team team = teamService.findById(id);

        team.setUpPw(pw);
        team.setUpModifiedDate(LocalDateTime.now());

        teamService.save(team);

        //세션 종료
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "ok";

    }

    //팀 Name 중복 검사
    @ResponseBody
    @GetMapping("/validate")
    public List<Object> validateName(Long id, String name) {
        log.info("validate Team");

        Map<Boolean, String> result = teamService.validateName(id, name);
        List<Object> response = new ArrayList<>();

        if (result.containsKey(false)) {
            response.add(false);
            response.add(result.get(false));
        } else {
            response.add(true);
            response.add(result.get(true));
        }

        return response;

    }

    private Boolean formHasError(TeamUpdateForm form, BindingResult bindingResult) {

        if (form.getName() == null) {
            bindingResult.reject("Name is required",
                    "아이디는 필수입니다!");
        }

        if (form.getPhNumber() == null) {
            bindingResult.reject("PhNumber is required",
                    "연락처는 필수입니다!");
        }

        if (form.getEmail() == null) {
            bindingResult.reject("Email is required",
                    "이메일은 필수입니다!");
        }

        if (form.getCity() == null) {
            bindingResult.reject("City is required",
                    "City는 필수입니다!");
        }

        if (form.getStreet() == null) {
            bindingResult.reject("Street is required",
                    "Street은 필수입니다!");
        }

        if (form.getZipcode() == null) {
            bindingResult.reject("Zipcode is required",
                    "Zipcode는 필수입니다!");
        }

        if (bindingResult.hasErrors()) {
            return true;
        }

        return false;
    }
}
