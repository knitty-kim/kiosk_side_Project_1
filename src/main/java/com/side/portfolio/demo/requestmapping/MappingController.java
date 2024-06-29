package com.side.portfolio.demo.requestmapping;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

//@Controller는 반환된 String으로 뷰를 찾지만,
//@RestController는 반환된 값을 HTTP 메시지 바디에 박는다
@RestController
public class MappingController {

    //@RequestMapping의 HTTP 메서드 속성을 지정하지 않으면,
    //HTTP 메서드와 무관하게 호출된다
    @RequestMapping("/hello-basic")
    public String helloBasic() {
        return "OK";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        return "OK";
    }

    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        return "OK";
    }

    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        return data;
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId,
                              @PathVariable Long orderId) {
        return userId + orderId;
    }

    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        return "OK";
    }

    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        return "OK";
    }

    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsume() {
        return "OK";
    }

    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces() {
        return "OK";
    }
}
