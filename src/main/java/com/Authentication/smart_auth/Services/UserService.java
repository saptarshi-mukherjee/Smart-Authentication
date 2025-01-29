package com.Authentication.smart_auth.Services;

import com.Authentication.smart_auth.DTO.UserResponseDto;
import com.Authentication.smart_auth.Models.Book;
import com.Authentication.smart_auth.Models.User;

import java.util.List;

public interface UserService {
    public User register(String username, String password, String full_name, String roleName);
    public List<User> getAllUsers();
    public String verify(String username, String password);
    public UserResponseDto getUser(String username);
    public List<Book> buyBooks(String username, String title);
    public List<Book> viewBooks(String username);
}
