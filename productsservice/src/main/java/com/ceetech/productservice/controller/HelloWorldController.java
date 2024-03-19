package com.ceetech.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("api")
@RestController
@Slf4j
public class HelloWorldController {

    @GetMapping("hello")
    public String getMethodName(@RequestParam(required = false) String params) {
        log.info("Received params {}", params);
        return "Hello world";
    }
}
