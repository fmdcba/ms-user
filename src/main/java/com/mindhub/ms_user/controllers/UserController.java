package com.mindhub.ms_user.controllers;

import com.mindhub.ms_user.dtos.RolesDTO;
import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.models.UserEntity;
import com.mindhub.ms_user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) throws Exception {
        UserDTO user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = userService.getAllusers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        List<RolesDTO> roles = userService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserDTO newUser) {
        UserEntity newUserEntity = userService.createUser(newUser);
        return new ResponseEntity<>(newUserEntity, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUser) throws Exception {
        UserEntity updatedUserToEntity = userService.updateUser(id, updatedUser);
        return new ResponseEntity<>(updatedUserToEntity, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
    }
}
