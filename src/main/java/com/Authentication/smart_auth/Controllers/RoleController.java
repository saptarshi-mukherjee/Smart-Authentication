package com.Authentication.smart_auth.Controllers;


import com.Authentication.smart_auth.Models.Role;
import com.Authentication.smart_auth.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    RoleService role_service;

    @PostMapping("/add")
    public Role addNewRole(@RequestParam("role") String role_name) {
        return role_service.addRole(role_name);
    }

    @GetMapping("/get/all")
    public List<Role> viewAllRoles() {
        return role_service.viewRoles();
    }
}
