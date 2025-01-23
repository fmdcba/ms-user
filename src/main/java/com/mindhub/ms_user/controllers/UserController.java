package com.mindhub.ms_user.controllers;

import com.mindhub.ms_user.dtos.RolesDTO;
import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.exceptions.NotFoundException;
import com.mindhub.ms_user.exceptions.NotValidArgument;
import com.mindhub.ms_user.models.RoleType;
import com.mindhub.ms_user.models.UserEntity;
import com.mindhub.ms_user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RestControllerAdvice
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) throws NotFoundException, NotValidArgument {
        isValidId(id);
        UserDTO user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        List<RolesDTO> roles = userService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserDTO newUser) throws NotValidArgument {
        validateEntries(newUser);
        UserEntity newUserEntity = userService.createUser(newUser);
        return new ResponseEntity<>(newUserEntity, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUser) throws NotFoundException, NotValidArgument {
        validateEntries(updatedUser);
        UserEntity updatedUserToEntity = userService.updateUser(id, updatedUser);
        return new ResponseEntity<>(updatedUserToEntity, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws NotValidArgument {
        isValidId(id);
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
    }

    public void isValidId(Long id) throws NotValidArgument {
        if (id == null || id <= 0) {
            throw new NotValidArgument("Invalid ID");
        }
    }

    public void isValidUsername(String username) throws NotValidArgument {
        if (username == null || username.isBlank()) {
            throw new NotValidArgument("Username cannot be empty.");
        }
    }

    public void isValidEmail(String email) throws NotValidArgument {
        if (email == null || email.isBlank()) {
            throw new NotValidArgument("Email cannot be empty.");
        }
    }

    public void isValidRoles(RoleType status) throws NotValidArgument {
        if (status.equals(RoleType.USER) || status.equals(RoleType.ADMIN)) {
        } else {
            throw new NotValidArgument("Roles has to be USER or ADMIN.");
        }
    }

    public void validateEntries(UserDTO user) throws NotValidArgument {
        isValidUsername(user.getUsername());
        isValidEmail(user.getEmail());
        isValidRoles(user.getRoles());
    }
}
