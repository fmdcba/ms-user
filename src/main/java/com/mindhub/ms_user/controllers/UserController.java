package com.mindhub.ms_user.controllers;

import com.mindhub.ms_user.dtos.RolesDTO;
import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.mappers.UserMapper;
import com.mindhub.ms_user.models.UserEntity;
import com.mindhub.ms_user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) throws Exception {
        //validateId from the request
        UserDTO user = userMapper.userToDTO(userRepository.findById(id).orElseThrow(() -> new Exception("Not Found.")));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = userMapper.userListToDTO(userRepository.findAll());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        List<RolesDTO> roles = userMapper.usersRolesListToDTO(userRepository.findAll());
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserDTO newUser) {
        UserEntity newUserEntity = userRepository.save(userMapper.userToEntity(newUser));
        return new ResponseEntity<>(newUserEntity, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUser) throws Exception {
        //validate ID
        //validate entries for put
        UserEntity userToUpdate = userRepository.findById(id).orElseThrow(() -> new Exception("Not Found."));
        UserEntity updatedUserToEntity = userMapper.updateUserToEntity(userToUpdate, updatedUser);
        return new ResponseEntity<>(updatedUserToEntity, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
    }
}
