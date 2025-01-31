package com.Authentication.smart_auth.Controllers;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping("/user/hello")
    public String getUserGreetings(@AuthenticationPrincipal UserDetails user) {
        return user.getUsername()+" says hello to you!! You are talking to "+user.getAuthorities()+" with password "+user.getPassword();
    }

//    @GetMapping("/user/update")
//    public void updateRole(@AuthenticationPrincipal UserDetails user) {
//        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
//        user= User.withUsername(user.getUsername())
//                .password("{noop}newpass")
//                .roles("USER")
//                .build();
//        if(manager.userExists(user.getUsername()))
//            manager.updateUser(user);
//    }
}
