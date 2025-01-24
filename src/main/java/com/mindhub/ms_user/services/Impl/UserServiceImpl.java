package com.mindhub.ms_user.services.Impl;

import com.mindhub.ms_user.dtos.RolesDTO;
import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.exceptions.NotFoundException;
import com.mindhub.ms_user.exceptions.NotValidArgumentException;
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
    public UserDTO getUser(Long id) throws NotFoundException {
        try {
            return userMapper.userToDTO(findById(id));
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
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
    public UserEntity createUser(UserDTO newUser) throws NotValidArgumentException {
        try {
            validateAlreadyExistsEntries(newUser.getUsername(), newUser.getEmail());
            return save(userMapper.userToEntity(newUser));
        } catch (NotValidArgumentException e) {
            throw new NotValidArgumentException(e.getMessage());
        }
    }

    @Override
    public UserEntity updateUser(Long id, UserDTO updatedUser) throws NotFoundException, NotValidArgumentException {
        try {
            UserEntity userToUpdate = findById(id);
            validateAlreadyExistsEntries(updatedUser.getUsername(), updatedUser.getEmail());
            return save(userMapper.updateUserToEntity(userToUpdate, updatedUser));
        } catch (NotFoundException | NotValidArgumentException e) {
            throw e;
        }
    }

    @Override
    public void deleteUser(Long id) throws NotFoundException {
        try {
            existsById(id);
            deleteById(id);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public UserEntity findById(Long id) throws NotFoundException {
        return (userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not Found.")));
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

    @Override
    public void existsById(Long id) throws NotFoundException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found.");
        }
    }

    public void validateAlreadyExistsUsername(String username) throws NotValidArgumentException {
        if (userRepository.existsByUsername(username)){
            throw new NotValidArgumentException("Username '" + username + "' is already taken");
        }
    }

    public void validateAlreadyExistsEmail(String email) throws NotValidArgumentException {
        if (userRepository.existsByEmail(email)){
            throw new NotValidArgumentException("User email '" + email + "' is already taken");
        }
    }

    public void validateAlreadyExistsEntries(String username, String email) throws NotValidArgumentException {
        validateAlreadyExistsUsername(username);
        validateAlreadyExistsEmail(email);
    }

}
