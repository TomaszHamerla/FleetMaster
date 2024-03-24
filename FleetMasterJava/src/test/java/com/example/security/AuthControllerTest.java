package com.example.security;

import com.example.controller.AuthController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    JwtProvider jwtProvider;

    @Test
    void loginUserWithCorrectData() throws Exception {
        //given
        var token = "myToken";
        given(jwtProvider.createToken(any())).willReturn(token);
        //when + then
        mockMvc.perform(post("/api/v1/users/login")
                        .with(httpBasic("Jhon", "123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.token").value(token));
    }
    @Test
    void loginUserWithIncorrectUsernameShouldReturnStatusUnauthorized() throws Exception {
        //given
        given(jwtProvider.createToken(any())).willThrow(new BadCredentialsException("username or password is incorrect"));
        //when + then
        mockMvc.perform(post("/api/v1/users/login")
                .with(httpBasic("IncorrectName", "123")))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message",containsString("username or password is incorrect")));
    }
}