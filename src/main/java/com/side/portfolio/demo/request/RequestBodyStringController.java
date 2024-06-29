package com.side.portfolio.demo.request;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Controller
public class RequestBodyStringController {

    //HTTP message Body에 데이터가 담겨서 오는 경우,
    //@RequestParam이나 @ModelAttribute로 데이터를 읽을 수 없다!!

    //V1 : Request를 받아서 InputStream으로 읽기
    //      Response를 받아서 Writer로 쓰기
    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println(messageBody);
        response.getWriter().write("OK");
    }

    //V2 : InputStream으로 바로 읽기
    //      Writer로 바로 쓰기
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println(messageBody);
        responseWriter.write("OK");
    }

    //V3 : HttpEntity 사용!
    //HttpEntity ; HTTP 헤더, HTTP 바디 조회 및 입력
    // - 메시지 헤더-바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
    // - 메시지 헤더-바디 정보를 직접 입력(뷰 조회X)
    //
    //HttpMessageConverter ; 메시지 헤더-바디를 읽어서 문자나 객체로 변환
    // - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
    //
    //HttpEntity를 상속받은 다음 객체들도 같은 기능을 제공
    //RequestEntity, ResponseEntity
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        System.out.println(messageBody);
        return new HttpEntity<>("OK");
    }

    //V4 : RequestBody와 ResponseBody 사용!
    //@RequestBody
    //- 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
    //- HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
    //
    //@ResponseBody
    //- 메시지 바디 정보를 직접 입력(view 조회X)
    //- HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
    //
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        System.out.println(messageBody);
        return "OK";
    }

}
