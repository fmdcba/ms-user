package com.mindhub.ms_user.dtos;

import com.mindhub.ms_user.models.RoleType;
import com.mindhub.ms_user.models.UserEntity;

public class UserDTO {

    private Long id;

    private String username, email;

    private RoleType roles;

    public UserDTO(UserEntity user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        roles = user.getRoles();
    }

    public UserDTO() {}

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public RoleType getRoles() {
        return roles;
    }
}
