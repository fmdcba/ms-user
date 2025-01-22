package com.mindhub.ms_user.services.Impl;

import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.mappers.UserMapper;
import com.mindhub.ms_user.models.UserEntity;
import com.mindhub.ms_user.repositories.UserRepository;
import com.mindhub.ms_user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDTO getUser() {
        return null;
    }

    @Override
    public List<UserDTO> getAllusers() {
        return List.of();
    }

    @Override
    public UserDTO createUser() {
        return null;
    }

    @Override
    public UserDTO updateUser() {
        return null;
    }

    @Override
    public void deleteUser() {

    }

    @Override
    public UserEntity findById(Long Id) {
        return null;
    }

    @Override
    public List<UserEntity> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public UserEntity save(UserEntity entity) {
        return null;
    }
}
