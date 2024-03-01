package com.example.controller;

import com.example.model.user.Role;
import com.example.model.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    String token;

    @BeforeEach
    void setUp() throws Exception {
        ResultActions jhon = mockMvc.perform(post("http://localhost:8080/api/v1/users/login")
                .with(httpBasic("Jhon", "123")));
        MvcResult mvcResult = jhon.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(contentAsString);
        this.token = "Bearer " + jsonObject.get("token").toString();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
        // Reset H2 database before calling this test case.
    void findAllUsersSuccess() throws Exception {
        findAllUsers(2);
    }

    @Test
    void findUserByIdSuccess() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/users/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jhon"))
                .andExpect(jsonPath("$.email").value("jhon@gmail.com"));
    }

    @Test
    void findUserByIdNotFound() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/users/55")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
        // Reset H2 database before calling this test case.
    void createUserSuccess() throws Exception {
        User user = new User();
        user.setUsername("Tom");
        user.setPassword("123");
        user.setRole(Role.USER);
        user.setEmail("tom@gmail.com");
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("http://localhost:8080/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.email").value("tom@gmail.com"));

        findAllUsers(3);
    }

    @Test
    void createUserWithInvalidInputs() throws Exception {
        User user = new User();
        // empty fields
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("http://localhost:8080/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json).accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
        ).andExpect(status().isBadRequest());
    }

    private void findAllUsers(int userListSize) throws Exception {
        ResultActions resultActions = mockMvc.perform(get("http://localhost:8080/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(contentAsString);
        int length = jsonArray.length();
        assertThat(length).isEqualTo(userListSize);
    }
}
