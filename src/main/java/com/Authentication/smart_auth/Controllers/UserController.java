package com.Authentication.smart_auth.Controllers;


import com.Authentication.smart_auth.DTO.LoginRequestDto;
import com.Authentication.smart_auth.DTO.UserRequestDto;
import com.Authentication.smart_auth.Models.User;
import com.Authentication.smart_auth.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService user_service;

    @PostMapping("/register")
    public User addUser(@RequestBody UserRequestDto user_req) {
        return user_service.register(user_req.getUsername(),user_req.getPassword(),user_req.getFull_name());
    }

    @GetMapping("/get/all")
    public List<User> allUsers() {
        return user_service.getAllUsers();
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto login_req) {
        return user_service.verify(login_req.getUsername(),login_req.getPassword());
    }
}
