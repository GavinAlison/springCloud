package com.alison.service;

import com.alison.feign.TestFeign;
import org.springframework.stereotype.Component;
import java.util.Map;
import com.alison.vo.User;

@Component
public class TestFeignFallback  implements TestFeign {
    @Override
    public String testByFeign() {
        return "error";
    }

    @Override
    public String testByFeign2() {
        return "test2";
    }

    @Override
    public String testByParam(String from) {
        return "error";
    }

    @Override
    public String testByParam(Map<String, Object> map) {
        return "error";
    }

    @Override
    public String testByRequestBody(User user) {
        return "error";
    }

}
