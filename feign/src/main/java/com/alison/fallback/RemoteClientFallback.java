package com.alison.fallback;

import com.alison.feign.TestFeign;
import com.alison.vo.User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RemoteClientFallback implements TestFeign {
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
