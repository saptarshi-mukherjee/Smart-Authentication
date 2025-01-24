package com.Authentication.smart_auth.Services;

import com.Authentication.smart_auth.Models.Book;

import java.util.List;

public interface BookService {
    public List<Book> getAllBooks();
    public Book addBook(String title, String author, String category, Double price);
}
