package com.mindhub.ms_user.services;

import com.mindhub.ms_user.dtos.RolesDTO;
import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.models.UserEntity;

import java.util.List;

public interface UserService extends GenericService<UserEntity> {

    UserDTO getUser(Long id) throws Exception;

    List<UserDTO> getAllUsers();

    List<RolesDTO> getAllRoles();

    UserEntity createUser(UserDTO newUser);

    UserEntity updateUser(Long id, UserDTO updatedUser) throws Exception;

    void deleteUser(Long id);
}
