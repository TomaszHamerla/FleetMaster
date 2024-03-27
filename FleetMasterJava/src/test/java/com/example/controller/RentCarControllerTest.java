package com.example.controller;

import com.example.exception.UserCarRentalBalanceException;
import com.example.model.car.CarRequest;
import com.example.service.interfaces.RentCarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RentCarController.class)
@AutoConfigureMockMvc(addFilters = false) // Turn off Spring Security
@ActiveProfiles("test")
public class RentCarControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    RentCarService rentCarService;

    @Test
    void rentCarWhenUserIsBlocked() throws Exception {
        //given
        var carRequest = new CarRequest(1, 2, 3);
        String carJson = objectMapper.writeValueAsString(carRequest);
        given(rentCarService.rentCar(anyInt(),anyInt(),anyInt())).willThrow(new UserCarRentalBalanceException("User with given id is blocked !"));
        var json = """
                  {
                "status":405,
                "message":"User with given id is blocked !"
                }
                """;
        //when + then
        mockMvc.perform(post("/api/v1/cars/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(carJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().json(json));
    }
}
