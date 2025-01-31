package com.mindhub.ms_user.dtos;

import com.mindhub.ms_user.models.RoleType;

public record ValidUserDTO(String username, String email, String password) {
}
