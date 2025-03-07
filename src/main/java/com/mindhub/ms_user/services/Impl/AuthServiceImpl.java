package com.mindhub.ms_user.services.Impl;

import com.mindhub.ms_user.config.JwtUtils;
import com.mindhub.ms_user.config.RabbitMQConfig;
import com.mindhub.ms_user.dtos.NewUserDTO;
import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.dtos.ValidUserDTO;
import com.mindhub.ms_user.exceptions.NotValidArgumentException;
import com.mindhub.ms_user.mappers.UserMapper;
import com.mindhub.ms_user.models.UserEntity;
import com.mindhub.ms_user.repositories.UserRepository;
import com.mindhub.ms_user.services.AuthService;
import com.mindhub.ms_user.services.UserService;
import com.mindhub.ms_user.utils.ServiceValidations;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.Token;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceValidations serviceValidations;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerUser(NewUserDTO newUser) throws NotValidArgumentException {
        try {
            serviceValidations.validateAlreadyExistsEntries(newUser.username(), newUser.email());
            UserEntity user = userMapper.registerUserToEntity(newUser);
            UserDTO userDTO = userMapper.userToDTO(user);

            String temporaryToken = jwtUtil.generateTemporaryToken(newUser.username(), newUser.email(), newUser.password());

            amqpTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.USER_CREATED_ROUTING_KEY,
                    userDTO
            );

            Map<String, String> validationData = new HashMap<>();
            validationData.put("temporaryToken", temporaryToken);
            validationData.put("email", newUser.email());

            amqpTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.USER_VALIDATED_ROUTING_KEY,
                    validationData
            );

        } catch (NotValidArgumentException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new NotValidArgumentException(e.getMessage());
        }
    }

    public void validateUser(String token) {
        String username = jwtUtil.extractUsername(token);
        String email = jwtUtil.extractEmail(token);
        String password = jwtUtil.extractPassword(token);

        ValidUserDTO user = new ValidUserDTO(username, email, password);
        userService.save(userMapper.validUser(user));
    }


    @Override
    public String loginUser(NewUserDTO loginRequest) throws NotValidArgumentException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );
        UserEntity user = userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new NotValidArgumentException("Invalid Credentials."));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtil.generateToken(authentication.getName(), user.getId(), user.getRoles().toString());
    }
}
