package com.alison.controller;

import com.alison.vo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TestController {

    @Value("${server.port}")
    String port;

    @Value("${spring.application.name}")
    String name;

    @Value("${appName:provider}")
    String appName;

    @RequestMapping("/test2")
    public String test() {
        return appName + "上线测试:" + name + ":" + port;
    }

    @RequestMapping("/test")
    public String test22() {
        int a=1/0;// 除数不能为0
        return appName+"上线测试:"+name+":"+port;
    }
    @GetMapping("user")
    public String getUser() {
        return "admin";
    }

}
