package com.Authentication.smart_auth.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Successful";
    }

    @GetMapping("/about/info")
    public String getAbout() {
        String msg="This is a demo application aimed at learning authentication and AI";
        return msg;
    }
}
