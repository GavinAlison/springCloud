package com.alison.feign;

import com.alison.fallback.RemoteClientFallback;
import com.alison.vo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "springcloud-provider", fallback = RemoteClientFallback.class)
public interface TestFeign {
    @RequestMapping(value = "/test")
    String testByFeign();
    @RequestMapping(value = "/test2")
    String testByFeign2();

    // 测试带普通参数
    @RequestMapping("/testByParam")
    public String testByParam(String from);

    // 测试带多个普通参数Map
    @RequestMapping("/testByMap")
    public String testByParam(@RequestParam Map<String, Object> map);

    // 测试参数是对象的情况
    @RequestMapping("/testByRequestBody")
    public String testByRequestBody(@RequestBody User user);
}
