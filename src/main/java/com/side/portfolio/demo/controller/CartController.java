package com.side.portfolio.demo.controller;

import com.side.portfolio.demo.domain.Cart;
import com.side.portfolio.demo.dto.CartForm;
import com.side.portfolio.demo.service.CartService;
import com.side.portfolio.demo.service.ItemService;
import com.side.portfolio.demo.service.LoginService;
import com.side.portfolio.demo.service.TeamService;
import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (loginService.validateSession(session) == false) {
            log.info("inValid Session!!");
            return "main";
        }

        String types = (String) session.getAttribute("types");
        Long teamId = (Long) session.getAttribute("id");

        if (types.equals("team") || types.equals("master")) {
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

            Float totalPrice = carts
                    .stream()
                    .map(Cart::getPrice)
                    .reduce((float) 0, Float::sum);

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
    @PostMapping("/cart-list/createCart")
    @ResponseBody
    public List<Object> createCart(@RequestBody CartForm form) {

        List<Object> result = new ArrayList<>();

        if (cartService.isInCart(form.getTeamId(), form.getItemId())) {
            result.add(false);
            result.add("already in cart!");

        } else {
            Cart cart = Cart.builder()
                    .team(teamService.findById(form.getTeamId()))
                    .item(itemService.findById(form.getItemId()))
                    .price(Float.valueOf(form.getPrice()) * form.getQty())
                    .qty(form.getQty())
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .build();

            cartService.save(cart);

            result.add(true);
            result.add("cart-list");
        }

        return result;
    }

    //장바구니에서 상품 제거
    @GetMapping("/cart-list/remove/{cartId}")
    public String removeCart(@PathVariable Long cartId) {

        cartService.remove(cartId);
        return "redirect:/cart-list";
    }

//    @GetMapping("/cart-list/preOrder")
//    public String preOrder(Model model, HttpServletRequest request) {
//
//        log.info("preOrder!!");
//
//        HttpSession session = request.getSession(false);
//        if (loginService.validateSession(session) == false) {
//            log.info("inValid Session!!");
//            return "main";
//        }
//
//        String types = (String) session.getAttribute("types");
//        Long teamId = (Long) session.getAttribute("id");
//
//        if (types.equals("team") || types.equals("master")) {
//            List<Cart> totalCarts = cartService.findByTeamId(teamId);
//            model.addAttribute("carts", totalCarts);
//        }
//
//        return "team/cart";
//    }
}
