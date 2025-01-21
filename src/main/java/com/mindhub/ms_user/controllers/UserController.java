package com.mindhub.ms_user.controllers;

import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.mappers.UserMapper;
import com.mindhub.ms_user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) throws Exception {
        //validateId from the request
        UserDTO user = userMapper.userToDTO(userRepository.findById(id).orElseThrow(() -> new Exception("Not Found.")));
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() throws Exception {
        //List<UserDTO> users = userMapper
        List<UserDTO> users = userMapper.userListToDTO(userRepository.findAll());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
