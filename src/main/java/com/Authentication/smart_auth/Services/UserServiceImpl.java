package com.Authentication.smart_auth.Services;

import com.Authentication.smart_auth.DTO.UserResponseDto;
import com.Authentication.smart_auth.Models.Book;
import com.Authentication.smart_auth.Models.User;
import com.Authentication.smart_auth.Repositories.BookRepository;
import com.Authentication.smart_auth.Repositories.UserRepository;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    UserRepository user_repo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwt_service;
    @Autowired
    BookRepository book_repo;

    @Override
    public User register(String username, String password, String full_name) {
        User user=new User();
        user.setUsername(username);
        user.setFull_name(full_name);
        user.setPassword(encoder.encode(password));
        user=user_repo.save(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>)user_repo.fetchAllUsers();
    }

    @Override
    public String verify(String username, String password) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );
        if(authentication.isAuthenticated())
            return jwt_service.generateToken(username);
        else
            return "Authentication failure";
    }

    @Override
    public UserResponseDto getUser(String username) {
        User user=user_repo.fetchByUsername(username);
        UserResponseDto user_response=new UserResponseDto();
        user_response.setFull_name(user.getFull_name());
        user_response.setBooks(user.getBooks());
        return user_response;
    }

    @Override
    public List<Book> buyBooks(String username, String title) {
       List<Book> book_list=book_repo.fetchBooks(title);
       User user=user_repo.fetchByUsername(username);
       Book book=book_list.getFirst();
       user.getBooks().add(book);
       user_repo.save(user);
       book.getUsers().add(user);
       book_repo.save(book);
       return user.getBooks();
    }

    @Override
    public List<Book> viewBooks(String username) {
        User user=user_repo.fetchByUsername(username);
        return user.getBooks();
    }


}
