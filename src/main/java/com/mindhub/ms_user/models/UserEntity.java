package com.mindhub.ms_user.models;

import jakarta.persistence.*;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username, email;

    @Column(nullable = false)
    private RoleType roles;

    public UserEntity(String username, String email, RoleType roles) {
        this.username = username;
        this.email = email;
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

    public RoleType getRoles() {
        return roles;
    }

    public void setRoles(RoleType roles) {
        this.roles = roles;
    }
}
