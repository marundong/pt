package com.mrd.pt.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("authorized")
    public String test(){
        return "test";
    }
}
