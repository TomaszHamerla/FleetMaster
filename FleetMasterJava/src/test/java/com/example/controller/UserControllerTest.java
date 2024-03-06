package com.example.controller;

import com.example.exception.TooLongValueException;
import com.example.exception.UserNotFoundException;
import com.example.model.user.Role;
import com.example.model.user.User;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false) // Turn off Spring Security
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
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

    @Test
    void createUserTestSuccess() throws Exception {
        //given
        User user = users.get(0);
        user.setRole(Role.ADMIN);
        var json = objectMapper.writeValueAsString(user);
        given(service.save(user)).willReturn(user);

        //whne + then
        mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jhon"))
                .andExpect(jsonPath("$.email").value("jhon@gmail.com"));
    }

    @Test
    void createUserWithEmptyDataShouldReturnStatusCode400() throws Exception {
        //given
        User user = users.get(0);
        user.setUsername("");
        var json = objectMapper.writeValueAsString(user);
        given(service.save(user)).willReturn(user);

        //when + then
        mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message", containsString("Username is required")));
    }
    @Test
    void createUserWithUsernameOverThen35CharsShouldReturnStatus400() throws Exception {
        //given
        User user = users.get(0);
        user.setUsername("asdasdasdasasasasasasasasasasassasas");
        var json = objectMapper.writeValueAsString(user);
        given(service.save(user)).willThrow(new  TooLongValueException("Username or email too long for type character varying(35)"));

        //when + then
        mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username or email too long for type character varying(35)"));
    }
}