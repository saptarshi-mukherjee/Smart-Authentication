package com.Authentication.smart_auth.Services;

import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;

public interface JwtService {
    public String generateToken(String username);
    public String extractUsername(String token);
    public boolean isValidToken(String token, UserDetails user_details);
}
