package com.side.portfolio.demo.response;

import com.side.portfolio.demo.request.RequestParamController;
import com.side.portfolio.demo.request.RequestParamController.HelloData;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ResponseBodyController {

    //V1 : response를 직접 받아서, Writer 사용
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("OK");
    }


    //V2 : ResponseEntity 사용!
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


    //V3 : @ResponseBody 사용!
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "OK";
    }


    //JSON V1 : ResponseEntity에 객체를 JSON으로 담아 보내기!
    //ResponseEntity는 HttpStatus 설정 가능
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    
    //JSON V2 : ResponseBody에 객체를 JSON으로 담아 보내기!
    //HttpStatus는 @ResponseStatus으로 설정 가능
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return helloData;
    }
}
