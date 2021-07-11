package com.github.users.services;

import com.github.users.entities.User;
import com.github.users.entities.enums.Role;

import java.util.List;

public interface IUserService {

    List<User> findAll();

    User findById(String id);

    User findByEmail(String email);

    User create(User user);

    void updateInfo(User user);

    void updateEmailById(String id, String email);

    void updatePasswordById(String id, String password);

    void addRoleById(String id, Role role);

    void removeRoleById(String id, Role role);

    void deleteById(String id);

}
