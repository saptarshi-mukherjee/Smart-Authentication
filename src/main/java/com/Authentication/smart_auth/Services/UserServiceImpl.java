package com.Authentication.smart_auth.Services;

import com.Authentication.smart_auth.DTO.UserResponseDto;
import com.Authentication.smart_auth.Models.*;
import com.Authentication.smart_auth.Repositories.BookRepository;
import com.Authentication.smart_auth.Repositories.RoleRepository;
import com.Authentication.smart_auth.Repositories.UserRepository;
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
    @Autowired
    RoleRepository role_repo;
    @Autowired
    RefreshTokenService token_service;

    @Override
    public User register(String username, String password, String full_name, String roleName) {
        User user=new User();
        user.setUsername(username);
        user.setFull_name(full_name);
        user.setPassword(encoder.encode(password));
        RoleType type=RoleType.valueOf(roleName);
        Role role=role_repo.fetchRoleByName(type);
        user.setRole(role);
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
        if(authentication.isAuthenticated()) {
            Role user_role=user_repo.fetchByUsername(username).getRole();
            String access_token =  jwt_service.generateAccessToken(username,List.of(user_role.getRole_type().toString()));
            String refresh_token= jwt_service.generateRefreshToken(username,List.of(user_role.getRole_type().toString()));
            RefreshToken prev_token=token_service.getRefreshToken(username);
            if(prev_token!=null) {
                prev_token.setRefresh_token(refresh_token);
                token_service.saveRefreshToken(prev_token);
            }
            else {
                RefreshToken new_token=new RefreshToken(username,refresh_token);
                token_service.saveRefreshToken(new_token);
            }
            return access_token;
        }        else
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
