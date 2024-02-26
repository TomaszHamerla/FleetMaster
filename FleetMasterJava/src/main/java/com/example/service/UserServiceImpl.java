package com.example.service;

import com.example.converter.UserToUserDtoConverter;
import com.example.exception.UserNotFoundException;
import com.example.model.User;
import com.example.model.dto.UserDto;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserToUserDtoConverter userToUserDtoConverter;

    @Override
    public List<UserDto> getUsers() {
        return repository.findAll().stream()
                .map(userToUserDtoConverter::convert)
                .toList();
    }

    @Override
    public UserDto getUserById(int id) {
        User userFromDb = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userToUserDtoConverter.convert(userFromDb);
    }
}
