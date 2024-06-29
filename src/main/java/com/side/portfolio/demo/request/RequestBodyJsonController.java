package com.side.portfolio.demo.request;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.nio.charset.StandardCharsets;

import static com.side.portfolio.demo.request.RequestParamController.*;

//메시지 바디로 JSON이 들어오는 경우!
//{"username":"hello", "age":20}
//요청 시, content-type이 application/json 이어야만,
//JSON을 처리하는 HTTP 메시지 컨버터가 실행된다!
@Controller
public class RequestBodyJsonController {

    //objectMapper : JSON -> 자바 객체로 변환
    private ObjectMapper objectMapper = new ObjectMapper();


    //V1 : request와 response를 받아서, InputStream과 Writer 직접 사용!
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        System.out.println(helloData.toString());
        response.getWriter().write("OK");
    }


    //V2 : @RequestBody와 @ResponseBody를 사용!
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);

        System.out.println(data.toString());
        return "OK";
    }


    //V3 : 객체로 바로 받기!
    //HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용
    //내부 동작 :
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) {
        System.out.println(data.toString());
        return "OK";
    }


    //V4 : HttpEntity로 객체를 받기!
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();
        System.out.println(data.toString());
        return "OK";
    }


    //V5 : 객체로 바로 받기! + 객체로 바로 보내기!
    //HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용
    ////내부 동작 : 요청 시, JSON 요청 -> HTTP 메시지 컨버터 -> 객체
    //             응답 시, 객체 -> HTTP 메시지 컨버터 -> JSON 응답
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        System.out.println(data.toString());
        return data;
    }
}
