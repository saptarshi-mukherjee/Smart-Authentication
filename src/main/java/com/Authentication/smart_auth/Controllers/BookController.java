package com.Authentication.smart_auth.Controllers;


import com.Authentication.smart_auth.DTO.BookRequestDto;
import com.Authentication.smart_auth.Models.Book;
import com.Authentication.smart_auth.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService book_service;

    @GetMapping("/get/all")
    public List<Book> allBooks() {
        return book_service.getAllBooks();
    }

    @PostMapping("/post")
    public Book addNewBooks(@RequestBody BookRequestDto book_req) {
        return book_service.addBook(book_req.getTitle(),book_req.getAuthor(),book_req.getCategory(),book_req.getPrice());
    }
}
