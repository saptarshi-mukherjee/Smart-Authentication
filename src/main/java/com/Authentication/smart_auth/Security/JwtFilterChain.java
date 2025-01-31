package com.Authentication.smart_auth.Security;


import com.Authentication.smart_auth.Models.RefreshToken;
import com.Authentication.smart_auth.Services.JwtService;
import com.Authentication.smart_auth.Services.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilterChain extends OncePerRequestFilter {
    @Autowired
    JwtService jwt_service;
    @Autowired
    UserDetailsService user_details_service;
    @Autowired
    RefreshTokenService token_service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header=request.getHeader("Authorization");
        if(header==null || !header.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token=header.substring(7);
        String username=jwt_service.extractUsername(token);
        UserDetails user_details=user_details_service.loadUserByUsername(username);
        if(user_details==null) {
            filterChain.doFilter(request, response);
            //System.out.println();
        }
        else if(jwt_service.isValidToken(token)) {
            System.out.println("user found");
            setSecurityContext(token, request);
        }
        else {
            System.out.println("user found");
            RefreshToken refresh_token=token_service.getRefreshToken(username);
            if(refresh_token!=null && jwt_service.isValidToken(refresh_token.getRefresh_token())) {
                String access_token= jwt_service.generateAccessToken(username,jwt_service.extractRole(token));
                String r_token= jwt_service.generateRefreshToken(username,jwt_service.extractRole(token));
                //user_details=user_details_service.loadUserByUsername(jwt_service.extractUsername(access_token));
                refresh_token.setRefresh_token(r_token);
                token_service.saveRefreshToken(refresh_token);
                setSecurityContext(access_token,request);
            }
            else
                filterChain.doFilter(request,response);
        }
        filterChain.doFilter(request,response);
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<String> roles) {
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for(String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
        }
        return authorities;
    }

    public void setSecurityContext(String access_token, HttpServletRequest request) {
        List<String> roles=jwt_service.extractRole(access_token);
        UserDetails user_details=user_details_service.loadUserByUsername(jwt_service.extractUsername(access_token));
        List<SimpleGrantedAuthority> authorities=getAuthorities(roles);
        UsernamePasswordAuthenticationToken auth_token=
                new UsernamePasswordAuthenticationToken(user_details,null,authorities);
        auth_token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth_token);
    }
}
