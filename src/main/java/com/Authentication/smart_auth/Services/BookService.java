package com.Authentication.smart_auth.Services;

import com.Authentication.smart_auth.Models.Book;
import com.Authentication.smart_auth.Models.User;

import java.util.List;

public interface BookService {
    public List<Book> getAllBooks();
    public Book addBook(String title, String author, String category, Double price);
    public List<Book> getBook(String title);
    public List<User> getUsers(String title);
}
