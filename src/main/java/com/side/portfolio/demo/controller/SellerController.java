package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.Message;
import com.side.portfolio.demo.dto.condition.PartnerDto;
import com.side.portfolio.demo.dto.condition.PartnerSearchCond;
import com.side.portfolio.demo.dto.condition.SellerDto;
import com.side.portfolio.demo.dto.condition.SellerSearchCond;
import com.side.portfolio.demo.service.LogInService;
import com.side.portfolio.demo.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;
    private final LogInService logInService;

    //전체 판매자 목록
//    @GetMapping("/seller-list")
//    public String sellerList(Model model,
//                             @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
//
//        Page<Seller> sellers = sellerService.findByPagination(pageable);
//        model.addAttribute("sellers", sellers);
//
//        model.addAttribute("prev", sellers.getPageable().previousOrFirst().getPageNumber());
//        model.addAttribute("next", sellers.getPageable().next().getPageNumber());
//
//        model.addAttribute("hasPrev", sellers.hasPrevious());
//        model.addAttribute("hasNext", sellers.hasNext());
//
//        int groupSize = 3; //화면에 보여질 페이지 개수
//        int curPageGrp = (int) Math.floor((double) sellers.getNumber() / groupSize); //현재 페이지가 속한 그룹 번호
//        model.addAttribute("startPage", Math.max(0, ((curPageGrp) * groupSize)));
//        model.addAttribute("endPage", Math.min(sellers.getTotalPages() - 1, ((curPageGrp + 1) * groupSize) - 1));
//
//        model.addAttribute("curPage", sellers.getNumber());
//
//        return "basic/sellers";
//    }

    //전체 판매자 검색 조회 목록
    @GetMapping("/seller-list")
    public String sellerList(Model model, SellerSearchCond cond,
                              @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<SellerDto> sellers = sellerService.findSellerByCond(cond, pageable);
        model.addAttribute("sellers", sellers);

        model.addAttribute("prev", sellers.getPageable().previousOrFirst().getPageNumber());
        model.addAttribute("next", sellers.getPageable().next().getPageNumber());

        model.addAttribute("hasPrev", sellers.hasPrevious());
        model.addAttribute("hasNext", sellers.hasNext());

        int groupSize = 3; //화면에 보여질 페이지 개수
        int curPageGrp = (int) Math.floor((double) sellers.getNumber() / groupSize); //현재 페이지가 속한 그룹 번호
        model.addAttribute("startPage", Math.max(0, ((curPageGrp) * groupSize)));
        model.addAttribute("endPage", Math.min(sellers.getTotalPages() - 1, ((curPageGrp + 1) * groupSize) - 1));



        model.addAttribute("curPage", sellers.getNumber());

        model.addAttribute("cond", cond);

        return "basic/sellers";

    }


    //제휴 판매자 목록
    @GetMapping("/partner-list/{teamId}")
    public String partnerList(Model model, HttpServletRequest request,
                              @PathVariable Long teamId, PartnerSearchCond cond,
                              @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        if (logInService.invalidAccess(request.getSession(), teamId)) {
            model.addAttribute("data", new Message("잘못된 접근입니다!", "/"));
            return "message";
        }

        Page<PartnerDto> partners = sellerService.findPartnerByTeam_Id(teamId, cond, pageable);
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

        return "basic/partners";
    }

    //판매자 상세
    @GetMapping("/seller-list/{sellerId}")
    public String sellerDetail(@PathVariable Long sellerId, Model model) {


        return "main";
    }

}
