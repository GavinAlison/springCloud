package com.alison.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoutingController {

    @GetMapping(value = "/available")
    public String available() {
        return "Spring in Action";
    }

    @GetMapping(value = "/checked-out")
    public String checkedOut() {
        return "Spring Boot in Action";
    }

}
