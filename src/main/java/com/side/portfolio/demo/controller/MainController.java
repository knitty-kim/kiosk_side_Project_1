package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Address;
import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.domain.Team;
import com.side.portfolio.demo.dto.SignUpForm;
import com.side.portfolio.demo.service.FileService;
import com.side.portfolio.demo.service.SellerService;
import com.side.portfolio.demo.service.TeamService;
import com.side.portfolio.demo.status.SellerStatus;
import com.side.portfolio.demo.status.TeamStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final TeamService teamService;
    private final SellerService sellerService;
    private final FileService fileService;
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
    public String signUp(@Validated SignUpForm form, BindingResult bindingResult) {

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

