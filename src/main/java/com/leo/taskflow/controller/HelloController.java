package com.leo.taskflow.controller;

import com.leo.taskflow.dto.HelloResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public HelloResponse hello() {
        return new HelloResponse("Hello TaskFlow");
    }
}
