package com.example.controller;

import com.example.model.dto.UserDto;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    List<UserDto> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/{id}")
    UserDto getUser(@PathVariable int id) {
        return service.getUserById(id);
    }
}
