package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//本类仅为postman测试服务器https端口是否能正常调用，与项目无关
@RequestMapping("/test")
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public Object hello() {
        return "hello, test!";
    }
}
