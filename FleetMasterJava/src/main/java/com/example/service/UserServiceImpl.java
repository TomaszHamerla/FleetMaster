package com.example.service;

import com.example.exception.TooLongValueException;
import com.example.exception.TooShortPasswordException;
import com.example.exception.UserNotFoundException;
import com.example.model.user.MyUserPrincipal;
import com.example.model.user.Role;
import com.example.model.user.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User save(User user) {
        if (isValueLongerThen35Chars(user.getUsername(), user.getEmail()))
            throw new TooLongValueException("Username or email too long for type character varying(35)");

        if (!hasPasswordMinimumLength(user.getPassword()))
            throw new TooShortPasswordException("Given password is to short ! 3 characters at least");
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        return repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(MyUserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with given name not found"));
    }

    private boolean isValueLongerThen35Chars(String username, String email) {
        return username.length() > 35 || email.length() > 35;
    }

    private boolean hasPasswordMinimumLength(String password) {
        return password.length() >= 3;
    }
}
