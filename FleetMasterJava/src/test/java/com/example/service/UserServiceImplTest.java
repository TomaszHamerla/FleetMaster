package com.example.service;

import com.example.exception.UserNotFoundException;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository repository;
    @InjectMocks
    UserServiceImpl service;
    List<User> users;

    @BeforeEach
    void setUp() {
        User u1 = new User();
        u1.setId(1);
        u1.setUsername("Jhon");
        u1.setPassword("123");
        u1.setEmail("jhon@gmail.com");

        User u2 = new User();
        u2.setId(2);
        u2.setUsername("Doe");
        u2.setPassword("456");
        u2.setEmail("doe@gmail.com");

        users = List.of(u1, u2);
    }

    @Test
    void getUsersTestSuccess() {
        //given
        given(repository.findAll()).willReturn(users);

        //when
        List<User> usersList = service.getUsers();

        //then
        assertThat(usersList.equals(users));
        verify(repository, times(1)).findAll();
    }

    @Test
    void getUserByIdWithIdExistsShouldReturnUser() {
        //given
        given(repository.findById(1)).willReturn(Optional.of(users.get(0)));

        //when
        User user = service.getUserById(1);

        //then
        assertThat(user.equals(users.get(0)));
        verify(repository, times(1)).findById(1);
    }

    @Test
    void getUserByIdWithIdNotExistsShouldThrowUserNotFoundException() {
        //given
        given(repository.findById(anyInt())).willReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> service.getUserById(1));

        //then
        assertThat(thrown)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found");
        verify(repository,times(1)).findById(1);
    }
}