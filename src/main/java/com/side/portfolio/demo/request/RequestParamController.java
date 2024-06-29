package com.side.portfolio.demo.request;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class RequestParamController {
    
    //V1 :  request에서 직접 파라미터 추출
    //      반환 타입 void + response에 write == 뷰 조회 X
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        System.out.println("username : " + username + ", age : " + age);

        response.getWriter().write("OK");
    }

    //V2 :  @RequestParam으로 파라미터 추출
    //      @ResponseBody로 직접 입력
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {
        System.out.println("username : " + memberName + ", age : " + memberAge);
        return "OK";
    }
    
    //V3 : 파라미터 이름이 같으면 생략 가능
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age) {
        System.out.println("username : " + username + ", age : " + age);
        return "OK";
    }

    //V4 : 파라미터 타입이 단순하면 @RequestParam 생략 가능
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        System.out.println("username : " + username + ", age : " + age);
        return "OK";
    }


    //Required에 대해서...1
    //request-param-required?username= -> 빈문자로 통과
    //request-param-required?age= -> 불가
    //null을 int 같은 기본형에 입력하는 것은 불가 -> Integer로 변경 또는 defaultValue 사용
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) Integer age) {
        System.out.println("username : " + username + ", age : " + age);
        return "OK";
    }

    //Required에 대해서...2
    //defaultValue는 빈 문자의 경우에도 세팅됨!
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                      @RequestParam(required = false, defaultValue = "-1") int age) {
        return "OK";
    }

    //파라미터를 paramMap으로 받기..!
    //파라미터의 Key가 1개라면 paramMap을 사용해도 되지만,
    //그렇지 않다면 MultiValueMap을 사용
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        System.out.println("username : " + paramMap.get("username") + ", age : " + paramMap.get("age"));
        return "OK";
    }

    //@ModelAttribute V1 : 파라미터를 객체로 받기
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        System.out.println(helloData.getUsername() + ", " + helloData.getAge());
        return "OK";
    }

    //@ModelAttribute V2 : @ModelAttribute 생략
    //생략 규칙 : @RequestParam ; String, int, Integer 같은 단순 타입
    //          @ModelAttribute ; 그 외 나머지
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        System.out.println(helloData.getUsername() + ", " + helloData.getAge());
        return "OK";
    }

    @Data
    //@Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 자동 적용
    public static class HelloData {
        //객체에 getUsername() , setUsername()가 있으면, 이 객체의 username를 "프로퍼티"라고 한다
        private String username;
        //바인딩 오류 : age=abc처럼 바인딩이 잘못되는 경우, BindException
        private int age;
    }
}
