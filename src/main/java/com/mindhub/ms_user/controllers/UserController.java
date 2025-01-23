package com.mindhub.ms_user.controllers;

import com.mindhub.ms_user.dtos.RolesDTO;
import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.exceptions.NotFoundException;
import com.mindhub.ms_user.exceptions.NotValidArgumentException;
import com.mindhub.ms_user.models.RoleType;
import com.mindhub.ms_user.models.UserEntity;
import com.mindhub.ms_user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get user by id", description = "Return user if ID is valid and exists in DB")
        @ApiResponse(responseCode = "200", description = "Return user, and http code status OK")
        @ApiResponse(responseCode = "400", description = "Error msg Bad request: Invalid ID")
        @ApiResponse(responseCode = "404", description = "Error msg: Not found")
    public ResponseEntity<?> getUser(@PathVariable long id) throws NotFoundException, NotValidArgumentException {
        isValidId(id);
        UserDTO user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Return all users in DB")
        @ApiResponse(responseCode = "200", description = "Return list of users, and http code status OK")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/roles")
    @Operation(summary = "Get all roles", description = "Return all roles of the user with its ID")
        @ApiResponse(responseCode = "200", description = "Return list of roles with user ID, and http code status OK")
    public ResponseEntity<?> getRoles() {
        List<RolesDTO> roles = userService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("/users")
    @Operation(summary = "Create user", description = "Return user if ID is valid and exists in DB")
        @ApiResponse(responseCode = "200", description = "Return created user, and http code status OK")
        @ApiResponse(responseCode = "400", description = "Error msg Bad request: Indicating the field that cause the error")
    public ResponseEntity<?> createUser(@RequestBody UserDTO newUser) throws NotValidArgumentException {
        validateEntries(newUser);
        UserEntity newUserEntity = userService.createUser(newUser);
        return new ResponseEntity<>(newUserEntity, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Update user", description = "Return user if ID is valid and exists in DB")
        @ApiResponse(responseCode = "200", description = "Return updated user, and http code status OK")
        @ApiResponse(responseCode = "400", description = "Error msg Bad request: Invalid ID")
        @ApiResponse(responseCode = "404", description = "Error msg: Not found")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUser) throws NotFoundException, NotValidArgumentException {
        validateEntries(updatedUser);
        UserEntity updatedUserToEntity = userService.updateUser(id, updatedUser);
        return new ResponseEntity<>(updatedUserToEntity, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Update user", description = "Return user if ID is valid and exists in DB")
        @ApiResponse(responseCode = "200", description = "Return msg: User deleted successfully, and http code status OK")
        @ApiResponse(responseCode = "400", description = "Error msg Bad request: Indicating the field that cause the error")
        @ApiResponse(responseCode = "404", description = "Error msg: Not found")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws NotValidArgumentException, NotFoundException {
        isValidId(id);
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
    }

    public void isValidId(Long id) throws NotValidArgumentException {
        if (id == null || id <= 0) {
            throw new NotValidArgumentException("Invalid ID");
        }
    }

    public void isValidUsername(String username) throws NotValidArgumentException {
        if (username == null || username.isBlank()) {
            throw new NotValidArgumentException("Username cannot be empty.");
        }
    }

    public void isValidEmail(String email) throws NotValidArgumentException {
        if (email == null || email.isBlank()) {
            throw new NotValidArgumentException("Email cannot be empty.");
        }
    }

    public void isValidRoles(RoleType status) throws NotValidArgumentException {
        if (status.equals(RoleType.USER) || status.equals(RoleType.ADMIN)) {
        } else {
            throw new NotValidArgumentException("Roles has to be USER or ADMIN.");
        }
    }

    public void validateEntries(UserDTO user) throws NotValidArgumentException {
        isValidUsername(user.getUsername());
        isValidEmail(user.getEmail());
        isValidRoles(user.getRoles());
    }
}
