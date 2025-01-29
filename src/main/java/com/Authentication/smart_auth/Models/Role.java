package com.Authentication.smart_auth.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long role_id;
    RoleType role_type;

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public RoleType getRole_type() {
        return role_type;
    }

    public void setRole_type(RoleType role_type) {
        this.role_type = role_type;
    }
}
