package com.example.controller;

import com.example.model.car.BrandDto;
import com.example.model.car.ModelDto;
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

import static org.mockito.ArgumentMatchers.anyInt;
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
        mockMvc.perform(get("/api/v1/cars").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
    @Test
    void getModelsByBrandIdTestSuccess() throws Exception {
        //given
        List<ModelDto> modelDtos = List.of(new ModelDto("x1"), new ModelDto("x2"), new ModelDto("x3"));
        given(carFetchService.getModels(anyInt())).willReturn(modelDtos);
        var json = """
                [
                {
                "name":"x1"
                },
                {
                "name":"x2"
                },
                {
                "name":"x3"
                }
                ]
                """;
        //when + then
        mockMvc.perform(get("/api/v1/cars/brands/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
}