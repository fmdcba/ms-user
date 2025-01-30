package com.mindhub.ms_user.services.Impl;

import com.mindhub.ms_user.config.JwtUtils;
import com.mindhub.ms_user.dtos.NewUserDTO;
import com.mindhub.ms_user.exceptions.NotFoundException;
import com.mindhub.ms_user.exceptions.NotValidArgumentException;
import com.mindhub.ms_user.mappers.UserMapper;
import com.mindhub.ms_user.models.UserEntity;
import com.mindhub.ms_user.services.AuthService;
import com.mindhub.ms_user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtil;

    @Override
    public void registerUser(NewUserDTO newUser) throws NotValidArgumentException {
        UserEntity user = userMapper.registerUserToEntity(newUser);

        userService.save(user);
    }


    public String loginUser(NewUserDTO loginRequest) throws NotFoundException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtil.generateToken(authentication.getName());
    }
}
