package com.poscoict.poscoict.pjt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class HomeController {

    @GetMapping
    public String test(){
        return "test";
    }
}
