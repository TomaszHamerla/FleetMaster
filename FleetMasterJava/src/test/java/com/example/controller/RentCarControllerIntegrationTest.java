package com.example.controller;

import com.example.model.car.CarRequest;
import com.example.model.car.CarReturnResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RentCarControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    String token;

    @BeforeEach
    void setUp() throws Exception {
        ResultActions resultActions = mockMvc.perform(post("http://localhost:8080/api/v1/users/login")
                .with(httpBasic("Jhon", "123")));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(contentAsString);
        this.token = "Bearer " + jsonObject.get("token").toString();
    }

    @Test
    void rentCarSuccess() throws Exception {
        var carRequest = new CarRequest(1, 2, 3);
        String json = objectMapper.writeValueAsString(carRequest);

        mockMvc.perform(post("http://localhost:8080/api/v1/cars/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());

        mockMvc.perform(get("http://localhost:8080/api/v1/users/1/cars")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2].brand").value("Audi"))            //user with id 1 already got 2 car
                .andExpect(jsonPath("$[2].model").value("A5"))
                .andExpect(jsonPath("$[2].rentDate").value(LocalDate.now().toString()));
    }
    @Test
    void returnCarSuccess() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/cars/1/users/1/return")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(0))
                .andExpect(jsonPath("$.currency").value("PLN"));
    }
    @Test
    void returnCarWithWrongCarIdReturn404() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/cars/1252525/users/1/return")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Given id not found !"));
    }
}