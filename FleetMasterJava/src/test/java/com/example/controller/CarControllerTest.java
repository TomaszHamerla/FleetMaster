package com.example.controller;

import com.example.model.car.BrandDto;
import com.example.service.CarFetchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
@AutoConfigureMockMvc(addFilters = false) //Turn off spring security
@ActiveProfiles("test")
class CarControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    CarFetchService carFetchService;

    @Test
    void getBrandsTestSuccess() throws Exception {
        //given
        List<BrandDto> brandDtos = List.of(new BrandDto(1, "BMW"), new BrandDto(2, "Audi"), new BrandDto(3, "Opel"));
        given(carFetchService.getBrands()).willReturn(brandDtos);
        var json = """
                [
                {
                "id": 1,
                "name":"BMW"
                },
                {
                "id": 2,
                "name":"Audi"
                },
                {
                "id": 3,
                "name":"Opel"
                }
                ]
                """;
        //when + then
        mockMvc.perform(get("/cars").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
}