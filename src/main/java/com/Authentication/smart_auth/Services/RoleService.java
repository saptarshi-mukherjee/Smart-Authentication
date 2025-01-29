package com.Authentication.smart_auth.Services;

import com.Authentication.smart_auth.Models.Role;

import java.util.List;

public interface RoleService {
    public Role addRole(String role_name);
    public List<Role> viewRoles();
}
