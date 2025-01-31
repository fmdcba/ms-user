package com.mindhub.ms_user.controllers;

import com.mindhub.ms_user.dtos.NewUserDTO;
import com.mindhub.ms_user.dtos.RolesDTO;
import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.exceptions.NotAuthorizedException;
import com.mindhub.ms_user.exceptions.NotFoundException;
import com.mindhub.ms_user.exceptions.NotValidArgumentException;
import com.mindhub.ms_user.models.UserEntity;
import com.mindhub.ms_user.services.UserService;
import com.mindhub.ms_user.utils.ControllerValidations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RestControllerAdvice
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ControllerValidations controllerValidations;

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", description = "Return user if ID is valid and exists in DB")
        @ApiResponse(responseCode = "200", description = "Return user, and http code status OK")
        @ApiResponse(responseCode = "400", description = "Error msg Bad request: Invalid ID")
        @ApiResponse(responseCode = "404", description = "Error msg: Not found")
    public ResponseEntity<?> getUser(@PathVariable long id) throws NotFoundException, NotValidArgumentException, NotAuthorizedException {
        isValidId(id);
        UserDTO user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Return all users in DB")
        @ApiResponse(responseCode = "200", description = "Return list of users, and http code status OK")
        @ApiResponse(responseCode = "404", description = "Error msg: No users found to show")
    public ResponseEntity<?> getAllUsers() throws NotFoundException, NotAuthorizedException {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/roles")
    @Operation(summary = "Get all roles", description = "Return all roles of the user with its ID")
        @ApiResponse(responseCode = "200", description = "Return list of roles with user ID, and http code status OK")
        @ApiResponse(responseCode = "404", description = "Error msg: No roles found to show")
    public ResponseEntity<?> getRoles() throws NotFoundException, NotAuthorizedException {
        List<RolesDTO> roles = userService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create user", description = "Return user if ID is valid and exists in DB")
        @ApiResponse(responseCode = "200", description = "Return created user, and http code status OK")
        @ApiResponse(responseCode = "400", description = "Error msg Bad request: Indicating the field that cause the error")
    public ResponseEntity<?> createUser(@RequestBody NewUserDTO newUser) throws NotValidArgumentException, NotAuthorizedException {
        controllerValidations.validateEntries(newUser);
        UserDTO user = userService.createUser(newUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Return updated user if ID is valid and exists in DB")
        @ApiResponse(responseCode = "200", description = "Return updated user, and http code status OK")
        @ApiResponse(responseCode = "400", description = "Error msg Bad request: Invalid ID")
        @ApiResponse(responseCode = "404", description = "Error msg: Not found")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody NewUserDTO updatedUser) throws NotFoundException, NotValidArgumentException, NotAuthorizedException {
        controllerValidations.validateEntries(updatedUser);
        UserDTO user = userService.updateUser(id, updatedUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Return msg of operation completed successfully")
        @ApiResponse(responseCode = "200", description = "Return msg: User deleted successfully, and http code status OK")
        @ApiResponse(responseCode = "400", description = "Error msg Bad request: Invalid ID")
        @ApiResponse(responseCode = "404", description = "Error msg: Not found")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws NotValidArgumentException, NotFoundException, NotAuthorizedException {
        isValidId(id);
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
    }

    public void isValidId(Long id) throws NotValidArgumentException {
        if (id == null || id <= 0) {
            throw new NotValidArgumentException("Invalid ID");
        }
    }
}
