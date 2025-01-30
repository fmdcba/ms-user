package com.mindhub.ms_user.dtos;

import com.mindhub.ms_user.models.RoleType;

public record NewUserDTO(Long id, String username, String email, String password, RoleType roles) {
}
