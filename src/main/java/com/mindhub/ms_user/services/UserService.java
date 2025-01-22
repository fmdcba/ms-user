package com.mindhub.ms_user.services;

import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.models.UserEntity;

import java.util.List;

public interface UserService extends GenericService<UserEntity> {

    UserDTO getUser();

    List<UserDTO> getAllusers();

    UserDTO createUser();

    UserDTO updateUser();

    void deleteUser();
}
