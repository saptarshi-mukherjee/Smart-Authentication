package com.Authentication.smart_auth.Repositories;


import com.Authentication.smart_auth.Models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken,Long> {

    @Query(value = "select * from refresh_tokens where username = :username",
            nativeQuery = true)
    public RefreshToken fetchByUsername(@Param("username") String username);
}
