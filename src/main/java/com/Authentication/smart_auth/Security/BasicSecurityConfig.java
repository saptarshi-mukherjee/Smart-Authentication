package com.Authentication.smart_auth.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class BasicSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> {
            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests
                    .requestMatchers("/about/**").permitAll()
                    .requestMatchers("/deny/**").denyAll()
                    .anyRequest()).authenticated();
        });
        //http.formLogin(Customizer.withDefaults());
        // Disables mandatory CSRF token
        http.csrf(csrf->csrf.disable());
        http.httpBasic(Customizer.withDefaults());
        return (SecurityFilterChain)http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
        UserDetails user1= User.withUsername("Bumba")
                .password("{noop}pass1")
                .roles("USER")
                .build();
        UserDetails user2=User.withUsername("samir")
                .password("{noop}pass2")
                .roles("USER")
                .build();
        UserDetails user3=User.withUsername("juin")
                .password("{noop}pass3")
                .roles("ADMIN")
                .build();
        if(!manager.userExists(user1.getUsername()))
            manager.createUser(user1);
        if(!manager.userExists(user2.getUsername()))
            manager.createUser(user2);
        if(!manager.userExists(user3.getUsername()))
            manager.createUser(user3);
        return manager;
    }
}
