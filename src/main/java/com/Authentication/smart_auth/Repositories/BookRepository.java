package com.Authentication.smart_auth.Repositories;

import com.Authentication.smart_auth.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    @Query(value = "select * from books", nativeQuery = true)
    public List<Book> fetchAllBooks();

    @Query(value = "select *\n" +
            "from books\n" +
            "where title like concat(:title, '%')\n" +
            "or\n" +
            "title like concat('%',:title)\n" +
            "    or\n" +
            "    title like concat('%', :title, '%')\n" +
            "    or\n" +
            "    title = :title", nativeQuery = true)
    public List<Book> fetchBooks(String title);
}
