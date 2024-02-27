package com.example;

import com.example.model.user.Role;
import com.example.model.user.User;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBDataInitializer implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User();
        u1.setUsername("Jhon");
        u1.setPassword("123");
        u1.setEmail("jhon@gmail.com");
        u1.setRole(Role.ADMIN);

        User u2 = new User();
        u2.setUsername("Doe");
        u2.setPassword("456");
        u2.setEmail("doe@gmail.com");
        u2.setRole(Role.USER);

        userService.save(u1);
        userService.save(u2);

    }
}
