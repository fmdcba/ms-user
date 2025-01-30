package com.mindhub.ms_user.mappers;

import com.mindhub.ms_user.dtos.NewUserDTO;
import com.mindhub.ms_user.dtos.RolesDTO;
import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.models.RoleType;
import com.mindhub.ms_user.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDTO userToDTO (UserEntity user) {
        return new UserDTO(user);
    }

    public List<UserDTO> userListToDTO(List<UserEntity> users) {
        return users.stream().map(user -> new UserDTO(user)).toList();
    }

    public UserEntity userToEntity (NewUserDTO user){
        return new UserEntity(user.username(), user.email(), passwordEncoder.encode(user.password()), user.roles());
    }

    public UserEntity updateUserToEntity (UserEntity userToUpdate, NewUserDTO updatedUser) {
        userToUpdate.setUsername(updatedUser.username());
        userToUpdate.setEmail(updatedUser.email());
        userToUpdate.setPassword(passwordEncoder.encode(updatedUser.password()));
        userToUpdate.setRoles(updatedUser.roles());

        return userToUpdate;
    }

    public List<RolesDTO> usersRolesListToDTO(List<UserEntity> users) {
        return users.stream().map(user -> new RolesDTO(user.getId(), user.getRoles())).toList();
    }

    public UserEntity registerUserToEntity (NewUserDTO user) {
        UserEntity userEntity = new UserEntity(user.username(), user.email(), passwordEncoder.encode(user.password()), RoleType.USER);

        return userEntity;
    }
}
