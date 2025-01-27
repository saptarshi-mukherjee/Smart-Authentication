package com.Authentication.smart_auth.Services;


import com.Authentication.smart_auth.Models.Book;
import com.Authentication.smart_auth.Models.User;
import com.Authentication.smart_auth.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository book_repo;


    @Override
    public List<Book> getAllBooks() {
        List<Book> books=book_repo.fetchAllBooks();
        return books;
    }

    @Override
    public Book addBook(String title, String author, String category, Double price) {
        Book book=new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setPrice(price);
        book=book_repo.save(book);
        return book;
    }

    @Override
    public List<Book> getBook(String title) {
        return book_repo.fetchBooks(title);
    }

    @Override
    public List<User> getUsers(String title) {
        Book book=book_repo.fetchBooks(title).getFirst();
        return book.getUsers();
    }
}
