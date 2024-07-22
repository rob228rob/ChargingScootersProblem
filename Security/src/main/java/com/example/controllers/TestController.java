package com.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    @GetMapping("/guest/g1")
    public String test1() {
        return "test1 - user/guest";
    }

    @GetMapping("/admin")
    public String test2() {
        return "test2 - admin";
    }
}
