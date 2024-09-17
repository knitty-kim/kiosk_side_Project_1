package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.Message;
import com.side.portfolio.demo.domain.Address;
import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.dto.SellerUpdateForm;
import com.side.portfolio.demo.dto.condition.PartnerSellerDto;
import com.side.portfolio.demo.dto.condition.PartnerSearchCond;
import com.side.portfolio.demo.dto.condition.SellerDto;
import com.side.portfolio.demo.dto.condition.SellerSearchCond;
import com.side.portfolio.demo.service.LogInService;
import com.side.portfolio.demo.service.SellerService;
import com.side.portfolio.demo.status.SellerStatus;
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
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;
    private final LogInService logInService;

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
    public String partnerSellerList(Model model, HttpServletRequest request,
                              @PathVariable Long teamId, PartnerSearchCond cond,
                              @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        if (logInService.invalidAccess(request.getSession(), teamId)) {
            model.addAttribute("data", new Message("잘못된 접근입니다!", "/"));
            return "message";
        }

        Page<PartnerSellerDto> partners = sellerService.findPartnerByTeam_Id(teamId, cond, pageable);
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

        return "basic/partnerSellers";
    }

    //판매자 상세
    @GetMapping("/seller-list/{sellerId}")
    public String sellerDetail(@PathVariable Long sellerId, Model model) {

        return "main";
    }

    //판매자 수정 폼
    @GetMapping("/update/{sellerId}")
    public String updateSellerForm(@PathVariable Long sellerId, Model model) {

        Seller seller = sellerService.findById(sellerId);
        SellerUpdateForm form = new SellerUpdateForm();
        form.setId(sellerId);
        form.setName(seller.getName());
//        form.setPw(seller.getPw());
        form.setStatus(seller.getStatus());
        form.setPhNumber(seller.getPhNumber());
        form.setEmail(seller.getEmail());

        Address address = seller.getAddress();
        form.setCity(address.getCity());
        form.setStreet(address.getStreet());
        form.setZipcode(address.getZipcode());

        form.setCreatedDate(seller.getCreatedDate());
        form.setModifiedDate(seller.getModifiedDate());

        model.addAttribute("sellerStatuses", SellerStatus.values());
        model.addAttribute("sellerUpdateForm", form);
        model.addAttribute("sellerId", sellerId);

        return "basic/updateSeller";
    }

    //판매자 수정
    @PostMapping("/update/{sellerId}")
    public String updateSeller(Model model, @PathVariable Long sellerId,
                               @ModelAttribute SellerUpdateForm form,
                               BindingResult bindingResult) {

        if (formHasError(form, bindingResult)) {
            model.addAttribute("sellerStatuses", SellerStatus.values());
            return "basic/updateSeller";
        }

        Seller seller = sellerService.findById(sellerId);
        seller.setUpName(form.getName());
//        seller.setUpPw(form.getPw());
        seller.setUpStatus(form.getStatus());
        seller.setUpPhNumber(form.getPhNumber());
        seller.setUpEmail(form.getEmail());

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        seller.setUpAddress(address);
        seller.setUpModifiedDate(LocalDateTime.now());

        sellerService.save(seller);

        return "redirect:/seller/update/" + sellerId;

    }

    //판매자 비밀번호 변경
    @ResponseBody
    @PostMapping("/updatePw")
    public String updateSellerPw(Long id, String pw, HttpServletRequest request) {

        Seller seller = sellerService.findById(id);

        seller.setUpPw(pw);
        seller.setUpModifiedDate(LocalDateTime.now());

        sellerService.save(seller);

        //세션 종료
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "ok";

    }

    //판매자 Name 중복 검사
    @ResponseBody
    @GetMapping("/validate")
    public List<Object> validateName(Long id, String name) {
        log.info("validate Seller");

        Map<Boolean, String> result = sellerService.validateName(id, name);
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

    private Boolean formHasError(SellerUpdateForm form, BindingResult bindingResult) {

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
