package com.mindhub.ms_user.services;

import com.mindhub.ms_user.dtos.NewUserDTO;
import com.mindhub.ms_user.dtos.RolesDTO;
import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.exceptions.NotAuthorizedException;
import com.mindhub.ms_user.exceptions.NotFoundException;
import com.mindhub.ms_user.exceptions.NotValidArgumentException;
import com.mindhub.ms_user.models.UserEntity;

import java.util.List;

public interface UserService extends GenericService<UserEntity> {

    UserDTO getUser(Long id) throws NotFoundException, NotAuthorizedException;

    List<UserDTO> getAllUsers() throws NotFoundException, NotAuthorizedException;

    List<RolesDTO> getAllRoles() throws NotFoundException, NotAuthorizedException;

    UserDTO createUser(NewUserDTO newUser) throws NotValidArgumentException, NotAuthorizedException;

    UserDTO updateUser(Long id, NewUserDTO updatedUser) throws NotFoundException, NotValidArgumentException, NotAuthorizedException;

    void deleteUser(Long id) throws NotFoundException, NotAuthorizedException;
}
