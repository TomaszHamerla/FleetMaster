package com.example.service;

import com.example.model.user.User;
import com.example.model.user.dto.UserCredentials;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getUsers();

    User getUserById(int id);

    User save(User user);

    User updateUserCredentials(int userId, UserCredentials userCredentials);
}
