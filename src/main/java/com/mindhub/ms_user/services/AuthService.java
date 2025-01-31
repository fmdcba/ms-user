package com.mindhub.ms_user.services;

import com.mindhub.ms_user.dtos.NewUserDTO;
import com.mindhub.ms_user.exceptions.NotFoundException;
import com.mindhub.ms_user.exceptions.NotValidArgumentException;

public interface AuthService {

    void registerUser(NewUserDTO newUser) throws NotValidArgumentException;

    void validateUser(String token);

    String loginUser(NewUserDTO loginRequest) throws NotValidArgumentException;
}
