package com.example;

import com.example.model.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBDataInitializer implements CommandLineRunner {
    private final UserRepository repository;
    @Override
    public void run(String... args) throws Exception {
        User u1 = new User();
        u1.setUsername("Jhon");
        u1.setPassword("123");
        u1.setEmail("jhon@gmail.com");

        User u2 = new User();
        u2.setUsername("Doe");
        u2.setPassword("456");
        u2.setEmail("doe@gmail.com");

        repository.save(u1);
        repository.save(u2);

    }
}
