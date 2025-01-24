package com.Authentication.smart_auth.Services;

import com.Authentication.smart_auth.Models.User;

import java.util.List;

public interface UserService {
    public User register(String username, String password, String full_name);
    public List<User> getAllUsers();
    public String verify(String username, String password);
}
