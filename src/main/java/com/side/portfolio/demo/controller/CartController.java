package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Cart;
import com.side.portfolio.demo.dto.CartForm;
import com.side.portfolio.demo.service.CartService;
import com.side.portfolio.demo.service.ItemService;
import com.side.portfolio.demo.service.LoginService;
import com.side.portfolio.demo.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final LoginService loginService;
    private final TeamService teamService;
    private final ItemService itemService;

    @GetMapping("/cart-list")
    public String cartList(Model model, HttpServletRequest request,
                           @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("Cart Controller");

        HttpSession session = request.getSession(false);
        if (loginService.validateSession(session) == false) {
            return "main";
        }

        String types = (String) session.getAttribute("types");
        Long teamId = (Long) session.getAttribute("id");

        if (types.equals("team")) {
            Page<Cart> carts = cartService.findByTeamId(teamId, pageable);
            System.out.println(carts);
            model.addAttribute("carts", carts);

            model.addAttribute("prev", carts.getPageable().previousOrFirst().getPageNumber());
            model.addAttribute("next", carts.getPageable().next().getPageNumber());

            model.addAttribute("hasPrev", carts.hasPrevious());
            model.addAttribute("hasNext", carts.hasNext());

            int groupSize = 3; //화면에 보여질 페이지 개수
            int curPageGrp = (int) Math.floor((double) carts.getNumber() / groupSize); //현재 페이지가 속한 그룹 번호
            model.addAttribute("startPage", Math.max(0, ((curPageGrp) * groupSize)));
            model.addAttribute("endPage", Math.min(carts.getTotalPages() - 1, ((curPageGrp + 1) * groupSize) - 1));

            model.addAttribute("curPage", carts.getNumber());
        }

        return "team/cart";
    }

    @PostMapping("/cart-list")
    public String createCart(@RequestBody CartForm form) {

        Cart cart = Cart.builder()
                .team(teamService.findById(form.getTeamId()))
                .item(itemService.findById(form.getItemId()))
                .price(form.getPrice() * form.getQty())
                .qty(form.getQty())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        cartService.save(cart);

        return "redirect:/cart-list";

    }
}
