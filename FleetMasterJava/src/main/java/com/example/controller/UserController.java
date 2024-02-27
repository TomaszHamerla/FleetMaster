package com.example.controller;

import com.example.converter.UserToUserDtoConverter;
import com.example.model.user.User;
import com.example.model.user.dto.UserDto;
import com.example.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserToUserDtoConverter converter;

    @GetMapping
    List<UserDto> getUsers() {
        return service.getUsers().stream()
                .map(converter::convert)
                .toList();
    }

    @GetMapping("/{id}")
    UserDto getUser(@PathVariable int id) {
        User user = service.getUserById(id);
        return converter.convert(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);
        return converter.convert(savedUser);
    }
}
