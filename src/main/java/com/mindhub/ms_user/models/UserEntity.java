package com.mindhub.ms_user.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username, email;

    private String password;

    private RoleType roles;

    public UserEntity(String username, String email, String password, RoleType roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserEntity() {}

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleType getRoles() {
        return roles;
    }

    public void setRoles(RoleType roles) {
        this.roles = roles;
    }
}
