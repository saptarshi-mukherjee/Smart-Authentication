package com.Authentication.smart_auth.Services;

import com.Authentication.smart_auth.Models.CustomUserDetails;
import com.Authentication.smart_auth.Models.User;
import com.Authentication.smart_auth.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository user_repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=user_repo.fetchByUsername(username);
        if(user==null)
            throw new UsernameNotFoundException("User not present");
        return new CustomUserDetails(user);
    }
}
