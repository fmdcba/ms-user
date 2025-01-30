package com.mindhub.ms_user.services;

import com.mindhub.ms_user.dtos.NewUserDTO;
import com.mindhub.ms_user.dtos.RolesDTO;
import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.exceptions.NotFoundException;
import com.mindhub.ms_user.exceptions.NotValidArgumentException;
import com.mindhub.ms_user.models.UserEntity;

import java.util.List;

public interface UserService extends GenericService<UserEntity> {

    UserDTO getUser(Long id) throws NotFoundException;

    List<UserDTO> getAllUsers();

    List<RolesDTO> getAllRoles();

    UserEntity createUser(NewUserDTO newUser) throws NotValidArgumentException;

    UserEntity updateUser(Long id, NewUserDTO updatedUser) throws NotFoundException, NotValidArgumentException;

    void deleteUser(Long id) throws NotFoundException;
}
