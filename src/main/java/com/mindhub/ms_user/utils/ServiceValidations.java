package com.mindhub.ms_user.utils;

import com.mindhub.ms_user.exceptions.NotValidArgumentException;
import com.mindhub.ms_user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceValidations {

    @Autowired
    UserRepository userRepository;

    public void validateAlreadyExistsUsername(String username) throws NotValidArgumentException {
        if (userRepository.existsByUsername(username)){
            throw new NotValidArgumentException("Username: '" + username + "' is already taken");
        }
    }

    public void validateAlreadyExistsEmail(String email) throws NotValidArgumentException {
        if (userRepository.existsByEmail(email)){
            throw new NotValidArgumentException("Email: '" + email + "' is already taken");
        }
    }

    public void validateAlreadyExistsEntries(String username, String email) throws NotValidArgumentException {
        validateAlreadyExistsUsername(username);
        validateAlreadyExistsEmail(email);
    }
}
