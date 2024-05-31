package com.psh94.sonnim_server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/")
    public String home(){

        return "Hi Hi , World";
    }
}
