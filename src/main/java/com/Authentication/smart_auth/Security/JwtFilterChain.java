package com.Authentication.smart_auth.Security;


import com.Authentication.smart_auth.Services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilterChain extends OncePerRequestFilter {
    @Autowired
    JwtService jwt_service;
    @Autowired
    UserDetailsService user_details_service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header=request.getHeader("Authorization");
        if(header==null || !header.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token=header.substring(7);
        String username=jwt_service.extractUsername(token);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(username!=null && authentication==null) {
            UserDetails user_details=user_details_service.loadUserByUsername(username);
            if(jwt_service.isValidToken(token,user_details)) {
                UsernamePasswordAuthenticationToken auth_token=new UsernamePasswordAuthenticationToken(
                        user_details, null, user_details.getAuthorities()
                );
                auth_token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth_token);
            }
        }
        filterChain.doFilter(request,response);
    }
}
