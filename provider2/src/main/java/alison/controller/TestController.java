package alison.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${server.port}")
    String port;

    @Value("${spring.application.name}")
    String name;

    String appName = "provider";

    @RequestMapping("/test")
    public String test() {
        return appName+"上线测试:"+name+":"+port;
    }
}
