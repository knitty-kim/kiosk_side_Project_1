package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Cart;
import com.side.portfolio.demo.dto.CartForm;
import com.side.portfolio.demo.service.CartService;
import com.side.portfolio.demo.service.ItemService;
import com.side.portfolio.demo.service.LoginService;
import com.side.portfolio.demo.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final LoginService loginService;
    private final TeamService teamService;
    private final ItemService itemService;

    //장바구니 목록
    @GetMapping("/cart-list")
    public String cartList(Model model, HttpServletRequest request,
                           @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("Cart Controller");

        //TODO
        // - 세션 확인을 반복하지 않도록 리팩토링 필요!!
        HttpSession session = request.getSession(false);
//        if (loginService.validateSession(session) == false) {
//            log.info("inValid Session!!");
//            return "main";
//        }

        String types = (String) session.getAttribute("types");
        Long teamId = (Long) session.getAttribute("id");

        if (types.equals("team") || types.equals("master")) {

            //TODO - 페이징 보류
//            Page<Cart> carts = cartService.findByTeamId(teamId, pageable);
//            model.addAttribute("carts", carts);
//
//            model.addAttribute("prev", carts.getPageable().previousOrFirst().getPageNumber());
//            model.addAttribute("next", carts.getPageable().next().getPageNumber());
//
//            model.addAttribute("hasPrev", carts.hasPrevious());
//            model.addAttribute("hasNext", carts.hasNext());
//
//            int groupSize = 3; //화면에 보여질 페이지 개수
//            int curPageGrp = (int) Math.floor((double) carts.getNumber() / groupSize); //현재 페이지가 속한 그룹 번호
//            model.addAttribute("startPage", Math.max(0, ((curPageGrp) * groupSize)));
//            model.addAttribute("endPage", Math.min(carts.getTotalPages() - 1, ((curPageGrp + 1) * groupSize) - 1));
//
//            model.addAttribute("curPage", carts.getNumber());

            List<Cart> carts = cartService.findByTeamId(teamId);
            model.addAttribute("carts", carts);

            BigDecimal totalPrice = carts
                    .stream()
                    .map(Cart::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Integer totalQty = carts
                    .stream()
                    .map(Cart::getQty)
                    .reduce(0, Integer::sum);


            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalQty", totalQty);

        }
        return "pay/cart";
    }

    //장바구니에 상품 담기
    @ResponseBody
    @PostMapping("/carts/add")
    public List<Object> createCart(@RequestBody CartForm form) {
        log.info("createCart");

        List<Object> result = new ArrayList<>();

        if (cartService.isInCart(form.getTeamId(), form.getItemId())) {
            result.add(false);
            result.add("already in cart!");
        } else {

            //"정적 팩토리 메서드"가 아닌 "빌더" 사용
            //1. 이미 존재하는 Team, Item 을 사용해 Cart 를 생성하므로, 연관관계 편의 메서드가 필요없음
            //2. 가독성이 좋음
            //3. 맥락에 독립적인 테스트에 용이
            
            Cart cart = Cart.builder()
                    .team(teamService.findById(form.getTeamId()))
                    .item(itemService.findById(form.getItemId()))
                    .price(form.getPrice().multiply(new BigDecimal(form.getQty())).setScale(2, RoundingMode.CEILING))
                    .qty(form.getQty())
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .build();

            cartService.createCart(cart);

            result.add(true);
            result.add("cart-list");
        }

        return result;
    }

    //장바구니 상품 비우기
    @ResponseBody
    @PostMapping("/carts/delete/{cartId}")
    public String deleteCart(@PathVariable Long cartId) {
        cartService.deleteById(cartId);
        return "OK";
    }

    //장바구니 모든 상품 비우기
    @ResponseBody
    @PostMapping("/carts/deleteAll/{teamId}")
    public String deleteCarts(@PathVariable Long teamId) {
        cartService.deleteAllByTeamId(teamId);
        return "OK";
    }
}
