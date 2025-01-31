package com.mindhub.ms_user.utils;

import com.mindhub.ms_user.dtos.NewUserDTO;
import com.mindhub.ms_user.exceptions.NotValidArgumentException;
import com.mindhub.ms_user.models.RoleType;
import org.springframework.stereotype.Component;

@Component
public class ControllerValidations {

    public void isValidUsername(String username) throws NotValidArgumentException {
        if (username == null || username.isBlank()) {
            throw new NotValidArgumentException("Username cannot be null or empty.");
        }
    }

    public void isValidEmail(String email) throws NotValidArgumentException {
        if (email == null || email.isBlank()) {
            throw new NotValidArgumentException("Email cannot be null or empty.");
        }
    }

    public void isValidPassword(String password) throws NotValidArgumentException {
        if (password == null || password.isBlank()) {
            throw new NotValidArgumentException("Password cannot be null or empty");
        }
    }


    public void isValidRoles(RoleType status) throws NotValidArgumentException {
        if (status.equals(RoleType.USER) || status.equals(RoleType.ADMIN)) {
        } else {
            throw new NotValidArgumentException("Roles has to be USER or ADMIN.");
        }
    }

    public void validateEntries(NewUserDTO user) throws NotValidArgumentException {
        isValidUsername(user.username());
        isValidEmail(user.email());
        isValidPassword(user.password());
        if (user.roles() != null) {
            isValidRoles(user.roles());
        }
    }
}
