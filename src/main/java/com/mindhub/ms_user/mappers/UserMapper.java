package com.mindhub.ms_user.mappers;

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

}
