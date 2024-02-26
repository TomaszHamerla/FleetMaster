package com.example.controller;

import com.example.exception.UserNotFoundException;
import com.example.model.User;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService service;
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
    void getUsersSuccess() throws Exception {
        //given
        given(service.getUsers()).willReturn(users);
        var json = """
                [
                {
                "id": 1,
                "name": "Jhon",
                "email": "jhon@gmail.com"
                },
                {
                "id": 2,
                "name": "Doe",
                "email": "doe@gmail.com"
                }
                ]
                """;
        //when + then
        ResultActions resultActions = mockMvc.perform(get("/api/v1/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        JSONAssert.assertEquals(json, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getUserByIdSuccess() throws Exception {
        //given
        given(service.getUserById(1)).willReturn(users.get(0));
        var json = """
                {
                "id": 1,
                "name": "Jhon",
                "email": "jhon@gmail.com"
                }              
                """;
        //when + then
        ResultActions resultActions = mockMvc.perform(get("/api/v1/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        JSONAssert.assertEquals(json, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getUserByIdWithUserNotExists() throws Exception {
        //given
        given(service.getUserById(anyInt())).willThrow(new UserNotFoundException("User not found"));
        var json = """
                {
                "status":404,
                "message":"User not found"
                }
                """;
        //when + then
        ResultActions resultActions = mockMvc.perform(get("/api/v1/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(json));
        JSONAssert.assertEquals(json, resultActions.andReturn().getResponse().getContentAsString(), false);
    }
}