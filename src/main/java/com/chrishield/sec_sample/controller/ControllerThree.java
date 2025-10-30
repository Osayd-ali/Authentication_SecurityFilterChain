package com.chrishield.sec_sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/three")
public class ControllerThree {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Controller Three";
    }

    @GetMapping("/bighello")
    public String bigHello() {
        return "HELLO! from Controller Three";
    }

    @GetMapping("/smallhello")
    public String smallHello() {
        return "Hi!";
    }
}
