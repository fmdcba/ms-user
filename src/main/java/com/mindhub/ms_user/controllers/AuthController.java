package com.mindhub.ms_user.controllers;

import com.mindhub.ms_user.config.JwtUtils;
import com.mindhub.ms_user.dtos.NewUserDTO;
import com.mindhub.ms_user.exceptions.NotFoundException;
import com.mindhub.ms_user.exceptions.NotValidArgumentException;
import com.mindhub.ms_user.services.AuthService;
import com.mindhub.ms_user.utils.ControllerValidations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ControllerValidations controllerValidations;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "Sign up", description = "Receives credential for a user in the body and return a confirmation message")
    @ApiResponse(responseCode = "201", description = "confirmation msg on body: User registered successfully.")
    @ApiResponse(responseCode = "400", description = "Point a required missing part of the data. E.g: User title must not be null or empty")
    public ResponseEntity<?> registerUser(@RequestBody NewUserDTO newUserDTO) throws NotValidArgumentException {
        controllerValidations.validateEntries(newUserDTO);
        authService.registerUser(newUserDTO);
        return new ResponseEntity<>("User registered successfully.", HttpStatus.CREATED);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> saveUser(@RequestParam String token) throws NotValidArgumentException {
        authService.validateUser(token);
        return new ResponseEntity<>("User validated successfully.", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Log in", description = "Login a user and return a JTW in plain text")
    @ApiResponse(responseCode = "200", description = "Confirmation msg on body: JWT")
    @ApiResponse(responseCode = "401", description = "Invalid Credentials")
    public ResponseEntity<String> authenticateUser(@RequestBody NewUserDTO loginRequest) throws NotValidArgumentException {
        controllerValidations.isValidEmail(loginRequest.email());
        controllerValidations.isValidPassword(loginRequest.password());
        String jwt = authService.loginUser(loginRequest);

        return ResponseEntity.ok(jwt);
    }
}