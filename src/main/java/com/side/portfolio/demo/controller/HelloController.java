package com.side.portfolio.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("start")
    public String hello(Model model) {
        model.addAttribute("data", "2023-11-15");
        //컨트롤러에서 문자열을 반환하면, 뷰 리졸버가 해당 파일을 매핑하여 찾아준다
        //resources:templates + {반환된 문자열} + .html

        return "start";
        //뷰 리졸버가 아래의 경로로 파일을 찾아준다
        //-> resources:templates + start + .html
    }

    @GetMapping("mvc-start")
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "mvc-start-template";
    }


    //@ResponseBody는 뷰 리졸버를 사용하지 않는다
    //HTTP BODY에 직접 리턴값을 찍어버린다
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }



    //클래스로 담아서 ResponseBody로 반환하면
    //JSON으로 반환한다!!
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
