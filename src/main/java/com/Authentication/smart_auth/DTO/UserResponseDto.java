package com.Authentication.smart_auth.DTO;

import com.Authentication.smart_auth.Models.Book;

import java.util.List;

public class UserResponseDto {
    String full_name;
    List<Book> books;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
