package com.alison.controller;

import com.alison.feign.TestFeign;
import com.alison.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@RestController
public class TestController {
    @Autowired
    TestFeign testFeign;// 注入 Feign接口

    @RequestMapping(value = "/test2")
    public String test() {
        return testFeign.testByFeign2();
    }

    @Value("${appName:feign}")
    private String appName;

    @Value("${spring.application.name}")
    private String name;

    @Value("${server.port}")
    private String port;

    // 测试带普通参数
    @RequestMapping("/testByParam")
    public String testByParam(String from) {
        return appName + "上线测试:" + name + ":" + port + "来自:" + from;
    }

    // 测试带多个普通参数Map
    @RequestMapping("/testByMap")
    public String testByParam(@RequestParam Map<String, Object> map) {
        return appName + "上线测试:" + name + ":" + port + "用户:" + map.get("name");
    }

    // 测试参数是对象的情况
    @RequestMapping("/testByRequestBody")
    public String testByRequestBody(@RequestBody User user) {
        return appName + "上线测试:" + name + ":" + port + "用户:" + user.getName();
    }
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("goods")
    public String getGoodsByUser() {
        String response = (String) restTemplate.exchange("http://localhost:9001/user",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                }).getBody();
        log.info("goods----info");
        log.debug("goods----debug");
        log.error("goods----error");
        return  response;
    }

}
