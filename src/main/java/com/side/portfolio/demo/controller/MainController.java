package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Address;
import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.dto.SignUpForm;
import com.side.portfolio.demo.dto.condition.OrderedItemDto;
import com.side.portfolio.demo.dto.condition.OrderedItemSearchCond;
import com.side.portfolio.demo.service.FileService;
import com.side.portfolio.demo.service.OrderService;
import com.side.portfolio.demo.service.SellerService;
import com.side.portfolio.demo.service.TeamService;
import com.side.portfolio.demo.status.ItemStatus;
import com.side.portfolio.demo.status.OrderStatus;
import com.side.portfolio.demo.status.SellerStatus;
import com.side.portfolio.demo.status.TeamStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final TeamService teamService;
    private final SellerService sellerService;
    private final FileService fileService;
    private final OrderService orderService;
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
    public String signUp(SignUpForm form, BindingResult bindingResult) {

        if (formHasError(form, bindingResult)) {
            return "signup";
        }

        if (form.getTypes().equals("team")) {

            Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

            Team team = Team.builder()
                    .name(form.getName())
                    .pw(form.getPw())
                    .phNumber(form.getPhNumber())
                    .email(form.getEmail())
                    .status(TeamStatus.DORMANT)
                    .tickets(0)
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .address(address)
                    .build();

            teamService.save(team);

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

            sellerService.save(seller);

            return "redirect:/";

        }

        return "redirect:/";
    }

    @GetMapping("/about")
    public String about() {
        log.info("about page");
        return "about";
    }

    //기존 비밀번호 확인 폼 -> 비밀번호 변경 / 회원탈퇴
    @GetMapping("/checkPwForm/{action}")
    public String checkPwForm(@RequestParam(defaultValue = "/") String redirectURI,
                           @PathVariable String action, Model model, HttpServletRequest request) {

        request.getSession().setAttribute("redirectURI", redirectURI);

        //action ; 'pw' or 'withdraw'
        model.addAttribute("action", action);

        return "basic/checkPw";

    }

    //비밀번호 변경 가능 여부 확인
    @ResponseBody
    @PostMapping("/checkPw")
    public Boolean checkPw(Long id, String type, String pw) {

        if (type.equals("team")) {
            Team team = teamService.findById(id);

            if (team.getPw().equals(pw)) {
                return true;
            } else {
                return false;
            }

        } else {
            Seller seller = sellerService.findById(id);

            if (seller.getPw().equals(pw)) {
                return true;
            } else {
                return false;
            }

        }

    }

    //비밀번호 변경 폼
    @GetMapping("/updatePwForm")
    public String updatePwForm(@RequestParam(defaultValue = "/") String redirectURI,
                              HttpServletRequest request) {
        request.getSession().setAttribute("redirectURI", redirectURI);
        return "basic/updatePw";
    }

    //회원탈퇴 폼
    @GetMapping("/withdrawForm")
    public String withdrawForm(@RequestParam(defaultValue = "/") String redirectURI,
                               HttpServletRequest request) {
        log.info("withdraw page");
        request.getSession().setAttribute("redirectURI", redirectURI);
        return "basic/withdraw";
    }

    //회원탈퇴 확정
    @ResponseBody
    @PostMapping("/withdraw")
    public String withdraw(Long id, String type, HttpServletRequest request) {

        if (type.equals("team")) {
            Team team = teamService.findById(id);

            //ORDERED, ACCEPTED
            boolean checkOrdered = team.getOrders().stream()
                    .anyMatch(order -> OrderStatus.ORDERED.equals(order.getStatus()));

            boolean checkAccepted = team.getOrders().stream()
                    .anyMatch(order -> OrderStatus.ACCEPTED.equals(order.getStatus()));

            if (checkOrdered || checkAccepted) {
                return "fail";

            } else {
                team.setUpStatus(TeamStatus.DORMANT);
                team.setUpModifiedDate(LocalDateTime.now());
                teamService.save(team);
            }

        } else {
            Seller seller = sellerService.findById(id);

            //ORDERED, ACCEPTED
            List<OrderedItemDto> orderedItems = orderService.findOrderItemBySeller_Id(id, new OrderedItemSearchCond());

            int orderedSize = (int) orderedItems.stream()
                    .filter(orderedItem -> OrderStatus.ORDERED.equals(orderedItem.getOrderStatus()))
                    .count();

            int acceptedSize = (int) orderedItems.stream()
                    .filter(orderedItem -> OrderStatus.ACCEPTED.equals(orderedItem.getOrderStatus()))
                    .count();

            if (orderedSize > 0 || acceptedSize > 0) {
                return "fail";
            }

            //판매자의 모든 상품에 대해 "CLOSED" 처리
            seller.getItems()
                    .forEach(item -> item.setUpStatus(ItemStatus.CLOSED));

            seller.setUpStatus(SellerStatus.DORMANT);
            seller.setUpModifiedDate(LocalDateTime.now());
            sellerService.save(seller);
        }

        //세션 종료
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "pass";

    }
    
    //상세 페이지 로드 시, 이미지 파일 불러오기
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        String fullDir = fileService.getFullDir(filename);
        return new UrlResource("file:" + fullDir);
    }

    //가입 정보 필수 필드 체크
    private Boolean formHasError(SignUpForm form, BindingResult bindingResult) {
        if (form.getTypes() == null) {
            bindingResult.reject("Type is required",
                    "가입 유형을 선택해야합니다!");
        }

        if (form.getName() == null) {
            bindingResult.reject("Name is required",
                    "아이디는 필수입니다!");
        }

        if (form.getPw() == null) {
            bindingResult.reject("Password is required",
                    "비밀번호는 필수입니다!");
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

