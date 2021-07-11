package com.github.users.services.impl;

import com.github.users.entities.User;
import com.github.users.entities.enums.Role;
import com.github.users.exceptions.BadRequestException;
import com.github.users.exceptions.EntityConflictException;
import com.github.users.exceptions.EntityNotFoundException;
import com.github.users.repositories.IUserRepository;
import com.github.users.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User create(User user) {
        if (userRepository.existsById(user.getId()) || userRepository.existsByEmail(user.getEmail())){
            throw new EntityConflictException(String.format("Conflict saving entity: %s", user.toString()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void updateInfo(User user) {
        User userToUpdate = userRepository.findById(user.getId()).orElseThrow(EntityNotFoundException::new);
        if(!Objects.equals(userToUpdate.getEmail(), user.getEmail())){
            throw new BadRequestException(
                    String.format("Updating email by this method is not allowed. User: %s", user.toString())
            );
        }
        if (!Objects.equals(passwordEncoder.encode(userToUpdate.getPassword()),user.getPassword())){
            throw new BadRequestException(
                    String.format("Updating password by this method is not allowed. User: %s", user.toString())
            );
        }
        userRepository.save(user);
    }

    @Override
    public void updateEmailById(String id, String email) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setEmail(email);
        userRepository.save(user);
    }

    @Override
    public void updatePasswordById(String id, String password) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public void addRoleById(String id, Role role) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.addRole(role);
        userRepository.save(user);
    }

    @Override
    public void removeRoleById(String id, Role role) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.removeRole(role);
        userRepository.save(user);
    }

    @Override
    public void deleteById(String id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);
    }

}
