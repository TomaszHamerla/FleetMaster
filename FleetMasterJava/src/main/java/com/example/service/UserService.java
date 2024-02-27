package com.example.service;

import com.example.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getUsers();

    User getUserById(int id);

    User save(User user);
}
