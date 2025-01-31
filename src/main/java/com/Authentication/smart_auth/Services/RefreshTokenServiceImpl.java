package com.Authentication.smart_auth.Services;

import com.Authentication.smart_auth.Models.RefreshToken;
import com.Authentication.smart_auth.Repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    TokenRepository token_repo;

    @Override
    public RefreshToken saveRefreshToken(RefreshToken token) {
        return token_repo.save(token);
    }

    @Override
    public RefreshToken getRefreshToken(String username) {
        return token_repo.fetchByUsername(username);
    }
}
