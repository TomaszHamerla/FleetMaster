package com.example.controller;

import org.json.JSONArray;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CarControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
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
    void getBrandsSuccess() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("http://localhost:8080/api/v1/cars/brands")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(contentAsString);
        assertThat(jsonArray.length()).isNotZero();
    }
    @Test
    void getModelsWithCorrectBrandIdSuccess() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("http://localhost:8080/api/v1/cars/brands/models/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(contentAsString);
        assertThat(jsonArray.length()).isNotZero();
    }
    @Test
    void getModelsWithBrandIdNotExists() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/cars/brands/models/1123123")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Given brand id not exists"));
    }
}
