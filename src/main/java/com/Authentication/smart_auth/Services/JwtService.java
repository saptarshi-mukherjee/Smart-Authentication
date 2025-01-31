package com.Authentication.smart_auth.Services;

import java.util.List;

public interface JwtService {
    public String generateAccessToken(String username, List<String> role_name);
    public String generateRefreshToken(String username, List<String> role_name);
    public String extractUsername(String token);
    public boolean isValidToken(String token);
    public List<String> extractRole(String token);
}
