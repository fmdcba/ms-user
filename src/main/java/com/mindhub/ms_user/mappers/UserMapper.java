package com.mindhub.ms_user.mappers;

import com.mindhub.ms_user.dtos.RolesDTO;
import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.models.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserDTO userToDTO (UserEntity user) {
        return new UserDTO(user);
    }

    public List<UserDTO> userListToDTO(List<UserEntity> users) {
        return users.stream().map(user -> new UserDTO(user)).toList();
    }

    public UserEntity userToEntity (UserDTO user){
        return new UserEntity(user.getUsername(), user.getEmail(), user.getRoles());
    }

    public UserEntity updateUserToEntity (UserEntity userToUpdate, UserDTO updatedUser) {
        userToUpdate.setUsername(updatedUser.getUsername());
        userToUpdate.setEmail(updatedUser.getEmail());
        userToUpdate.setRoles(updatedUser.getRoles());

        return userToUpdate;
    }

    public List<RolesDTO> usersRolesListToDTO(List<UserEntity> users) {
        return users.stream().map(user -> new RolesDTO(user.getId(), user.getRoles())).toList();
    }
}
