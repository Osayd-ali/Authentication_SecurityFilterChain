package com.chrishield.sec_sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/two")
public class ControllerTwo {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Controller Two";
    }

    @GetMapping("/bighello")
    public String bigHello() {
        return "HELLO! from Controller Two";
    }

    @GetMapping("/smallhello")
    public String smallHello() {
        return "Hi!";
    }
}
