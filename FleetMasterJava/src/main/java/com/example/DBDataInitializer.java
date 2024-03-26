package com.example;

import com.example.model.user.Role;
import com.example.model.user.User;
import com.example.service.interfaces.RentCarService;
import com.example.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBDataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final RentCarService rentCarService;

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

        rentCarService.rentCar(1,2,103);
        rentCarService.rentCar(1,2,104);
        rentCarService.rentCar(2,2,3);
        rentCarService.rentCar(2,2,106);
    }
}
