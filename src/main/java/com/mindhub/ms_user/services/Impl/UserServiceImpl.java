package com.mindhub.ms_user.services.Impl;

import com.mindhub.ms_user.dtos.RolesDTO;
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
    public UserDTO getUser(Long id) throws Exception {
        return userMapper.userToDTO(findById(id));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return  userMapper.userListToDTO(userRepository.findAll());
    }

    @Override
    public List<RolesDTO> getAllRoles() {
        return userMapper.usersRolesListToDTO(findAll());
    }

    @Override
    public UserEntity createUser(UserDTO newUser) {
        return save(userMapper.userToEntity(newUser));
    }

    @Override
    public UserEntity updateUser(Long id, UserDTO updatedUser) throws Exception {
        UserEntity userToUpdate = findById(id);
        return save(userMapper.updateUserToEntity(userToUpdate, updatedUser));
    }

    @Override
    public void deleteUser(Long id) {
        deleteById(id);
    }

    @Override
    public UserEntity findById(Long id) throws Exception {
        return (userRepository.findById(id).orElseThrow(() -> new Exception("Not Found.")));
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }
}
