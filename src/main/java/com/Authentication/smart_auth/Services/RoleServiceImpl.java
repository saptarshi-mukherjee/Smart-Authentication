package com.Authentication.smart_auth.Services;

import com.Authentication.smart_auth.Models.Role;
import com.Authentication.smart_auth.Models.RoleType;
import com.Authentication.smart_auth.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository role_repo;

    @Override
    public Role addRole(String role_name) {
        RoleType role_type=RoleType.valueOf(role_name);
        Role role=new Role();
        role.setRole_type(role_type);
        role=role_repo.save(role);
        return role;
    }

    @Override
    public List<Role> viewRoles() {
        return role_repo.fetchAllRoles();
    }
}
