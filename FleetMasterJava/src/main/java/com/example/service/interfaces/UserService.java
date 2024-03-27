package com.example.service.interfaces;

import com.example.model.car.Car;
import com.example.model.user.User;
import com.example.model.user.dto.UserCredentials;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getUsers();

    User getUserById(int id);

    User save(User user);

    User updateUserCredentials(int userId, UserCredentials userCredentials);
    List<Car> getUserCars(int userId);
    User depositMoney(int userId, double amount);
}
