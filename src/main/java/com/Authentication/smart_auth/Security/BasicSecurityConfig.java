package com.Authentication.smart_auth.Security;


import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class BasicSecurityConfig  {

    @Autowired
    UserDetailsService user_details_service;
    @Autowired
    JwtFilterChain filter_chain;

//    public BasicSecurityConfig(UserDetailsService user_details_service) {
//        this.user_details_service = user_details_service;
//    }

    @Bean
    public CorsConfigurationSource getCors() {
        CorsConfiguration config=new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> {
            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests
                    .requestMatchers("/about/**").permitAll()
                    .requestMatchers("/deny/**").denyAll()
                    .requestMatchers("/users/register").permitAll()
                    .requestMatchers("/users/login").permitAll()
                    .requestMatchers("/roles/**").permitAll()
                    .anyRequest()).authenticated();
        });
        //http.formLogin(Customizer.withDefaults());

        // Disables mandatory CSRF token
        http.cors(cors -> cors.configurationSource(getCors()));
        http.csrf(csrf->csrf.disable());
        http.httpBasic(Customizer.withDefaults());
        http.addFilterBefore(filter_chain, UsernamePasswordAuthenticationFilter.class);
        return (SecurityFilterChain)http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
//        UserDetails user1= User.withUsername("Bumba")
//                .password("{noop}pass1")
//                .roles("USER")
//                .build();
//        UserDetails user2=User.withUsername("samir")
//                .password("{noop}pass2")
//                .roles("USER")
//                .build();
//        UserDetails user3=User.withUsername("juin")
//                .password("{noop}pass3")
//                .roles("ADMIN")
//                .build();
//        if(!manager.userExists(user1.getUsername()))
//            manager.createUser(user1);
//        if(!manager.userExists(user2.getUsername()))
//            manager.createUser(user2);
//        if(!manager.userExists(user3.getUsername()))
//            manager.createUser(user3);
//        return manager;
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(user_details_service);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
