package com.Authentication.smart_auth.Services;

import com.Authentication.smart_auth.Models.RefreshToken;

public interface RefreshTokenService {
    public RefreshToken saveRefreshToken(RefreshToken token);
    public RefreshToken getRefreshToken(String username);
}
