package com.mrd.pt.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author marundong
 */
@RequestMapping("system")
@RestController
public class SystemController {

    @GetMapping("hello")
    public String testHello(){
        return "hello";
    }
}
