package com.Authentication.smart_auth.Repositories;

import com.Authentication.smart_auth.Models.Role;
import com.Authentication.smart_auth.Models.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query(value = "select * from roles where role_type = :type", nativeQuery = true)
    public Role fetchRoleByName(RoleType type);
    @Query(value = "select * from roles", nativeQuery = true)
    public List<Role> fetchAllRoles();

}
