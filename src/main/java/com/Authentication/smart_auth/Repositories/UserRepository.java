package com.Authentication.smart_auth.Repositories;

import com.Authentication.smart_auth.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select * from users", nativeQuery = true)
    public List<User> fetchAllUsers();

    @Query(value = "select * from users\n" +
            "where username=:username", nativeQuery = true)
    public User fetchByUsername(String username);
}
