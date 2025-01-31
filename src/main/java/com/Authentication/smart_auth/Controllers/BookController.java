package com.Authentication.smart_auth.Controllers;


import com.Authentication.smart_auth.DTO.BookRequestDto;
import com.Authentication.smart_auth.DTO.OwnerResponseDto;
import com.Authentication.smart_auth.Models.Book;
import com.Authentication.smart_auth.Models.User;
import com.Authentication.smart_auth.Services.BookService;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {

    @Autowired
    BookService book_service;

    @GetMapping("/get/all")
    public List<Book> allBooks() {
        return book_service.getAllBooks();
    }

    @PostMapping("/post")
    @Secured("ROLE_ADMIN")
    public Book addNewBooks(@RequestBody BookRequestDto book_req) {
        return book_service.addBook(book_req.getTitle(),book_req.getAuthor(),book_req.getCategory(),book_req.getPrice());
    }

    @GetMapping("/get/search")
    public List<Book> search(@RequestParam("search") String title) {
        return book_service.getBook(title);
    }

    @GetMapping("/get/owners")
    public List<OwnerResponseDto> getBookOwners(@RequestParam("book") String book_name) {
        List<User> user_list= book_service.getUsers(book_name);
        List<OwnerResponseDto> owners=new ArrayList<>();
        for(User user : user_list) {
            OwnerResponseDto owner=new OwnerResponseDto();
            owner.setId(user.getId());
            owner.setUsername(user.getUsername());
            owner.setFull_name(user.getFull_name());
            owners.add(owner);
        }
        return owners;
    }
}
