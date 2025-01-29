package com.Authentication.smart_auth.Services;

import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.List;

public interface JwtService {
    public String generateToken(String username, List<String> role_name);
    public String extractUsername(String token);
    public boolean isValidToken(String token, UserDetails user_details);
    public List<String> extractRole(String token);
}
