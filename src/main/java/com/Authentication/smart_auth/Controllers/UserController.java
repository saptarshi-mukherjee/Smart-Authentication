package com.Authentication.smart_auth.Controllers;


import com.Authentication.smart_auth.DTO.LoginRequestDto;
import com.Authentication.smart_auth.DTO.PurchaseResponseDto;
import com.Authentication.smart_auth.DTO.UserRequestDto;
import com.Authentication.smart_auth.DTO.UserResponseDto;
import com.Authentication.smart_auth.Models.Book;
import com.Authentication.smart_auth.Models.User;
import com.Authentication.smart_auth.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService user_service;

    private String getUserDetails() {
        UserDetails pricipal= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return pricipal.getUsername();
    }

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

    @GetMapping("/get/one")
    public UserResponseDto userInfo() {
        String username=getUserDetails();
        return user_service.getUser(username);
    }

    @GetMapping("/buy")
    public List<PurchaseResponseDto> buy(@RequestParam("book") String book_name) {
        String username=getUserDetails();
        List<Book> book_list= user_service.buyBooks(username,book_name);
        List<PurchaseResponseDto> purchased_books=new ArrayList<>();
        for(Book book : book_list) {
            PurchaseResponseDto bought=new PurchaseResponseDto();
            bought.setTitle(book.getTitle());
            bought.setAuthor(book.getAuthor());
            bought.setCategory(book.getCategory());
            purchased_books.add(bought);
        }
        return purchased_books;
    }

    @GetMapping("/get")
    public List<PurchaseResponseDto> viewBooks() {
        String username=getUserDetails();
        List<Book> book_list= user_service.viewBooks(username);
        List<PurchaseResponseDto> purchased_books=new ArrayList<>();
        for(Book book : book_list) {
            PurchaseResponseDto bought=new PurchaseResponseDto();
            bought.setTitle(book.getTitle());
            bought.setAuthor(book.getAuthor());
            bought.setCategory(book.getCategory());
            purchased_books.add(bought);
        }
        return purchased_books;
    }
}
