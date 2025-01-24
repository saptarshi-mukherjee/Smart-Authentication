package com.Authentication.smart_auth.Services;

import com.Authentication.smart_auth.Models.User;
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
            return "Login successful. Token generated";
        else
            return "Authentication failure";
    }


}
