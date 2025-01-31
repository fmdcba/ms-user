package com.mindhub.ms_user.services.Impl;

import com.mindhub.ms_user.config.JwtUtils;
import com.mindhub.ms_user.config.RabbitMQConfig;
import com.mindhub.ms_user.dtos.NewUserDTO;
import com.mindhub.ms_user.dtos.RolesDTO;
import com.mindhub.ms_user.dtos.UserDTO;
import com.mindhub.ms_user.exceptions.NotAuthorizedException;
import com.mindhub.ms_user.exceptions.NotFoundException;
import com.mindhub.ms_user.exceptions.NotValidArgumentException;
import com.mindhub.ms_user.mappers.UserMapper;
import com.mindhub.ms_user.models.UserEntity;
import com.mindhub.ms_user.repositories.UserRepository;
import com.mindhub.ms_user.services.UserService;
import com.mindhub.ms_user.utils.ServiceValidations;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ServiceValidations serviceValidations;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDTO getUser(Long id) throws NotFoundException, NotAuthorizedException {
        try {
            validateAuthorization(id);
            return userMapper.userToDTO(findById(id));
        } catch (NotFoundException | NotAuthorizedException e) {
            log.warn(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<UserDTO> getAllUsers() throws NotFoundException, NotAuthorizedException {
        try {
             validateIsAdmin();
             List<UserDTO> usersList = userMapper.userListToDTO(userRepository.findAll());
             validateUsersListIsEmpty(usersList);
             return usersList;
        } catch (NotFoundException | NotAuthorizedException e) {
            log.warn(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<RolesDTO> getAllRoles() throws NotFoundException, NotAuthorizedException {
        try {
            validateIsAdmin();
            List<RolesDTO> rolesList = userMapper.usersRolesListToDTO(findAll());
            validateRolesListIsEmpty(rolesList);
            return rolesList;
        } catch (NotFoundException | NotAuthorizedException e) {
            log.warn(e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public UserDTO createUser(NewUserDTO newUser) throws NotValidArgumentException, NotAuthorizedException {
        try {
            validateIsAdmin();
            serviceValidations.validateAlreadyExistsEntries(newUser.username(), newUser.email());
            UserEntity savedUser = save(userMapper.userToEntity(newUser));
            UserDTO userDTO = userMapper.userToDTO(savedUser);

            amqpTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.USER_CREATED_ROUTING_KEY,
                    userDTO
            );

            return userDTO;
        } catch (NotValidArgumentException | NotAuthorizedException e) {
            log.warn(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, NewUserDTO updatedUser) throws NotFoundException, NotValidArgumentException, NotAuthorizedException {
        try {
            validateAuthorization(id);
            UserEntity userToUpdate = findById(id);
            serviceValidations.validateAlreadyExistsEntries(updatedUser.username(), updatedUser.email());
            UserEntity savedUser = save(userMapper.updateUserToEntity(userToUpdate, updatedUser));
            return userMapper.userToDTO(savedUser);
        } catch (NotFoundException | NotValidArgumentException | NotAuthorizedException e) {
            log.warn(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) throws NotFoundException, NotAuthorizedException {
        try {
            validateAuthorization(id);
            existsById(id);
            deleteById(id);
        } catch (NotFoundException | NotAuthorizedException e) {
            log.warn(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    public UserEntity findById(Long id) throws NotFoundException {
        return (userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not Found.")));
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public void existsById(Long id) throws NotFoundException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found.");
        }
    }

    private void validateUsersListIsEmpty(List<UserDTO> allUsers) throws NotFoundException {
        if (allUsers.toArray().length == 0) {
            throw new NotFoundException("No users found to show.");
        }
    }

    private void validateRolesListIsEmpty(List<RolesDTO> allRoles) throws NotFoundException {
        if (allRoles.toArray().length == 0) {
            throw new NotFoundException("No roles found to show.");
        }
    }

    private void validateAuthorization(Long pathId) throws NotAuthorizedException {
        String token = jwtUtils.getJwtToken();
        Long userId = jwtUtils.extractId(token);

        if (!userId.equals(pathId)) {
            validateIsAdmin();
        }
    }

    private void validateIsAdmin() throws NotAuthorizedException {
        String token = jwtUtils.getJwtToken();
        String userRole = jwtUtils.extractRole(token);

        if (!userRole.equals("ADMIN")) {
            throw new NotAuthorizedException("You are not authorized to access this resource.");
        }
    }
}
