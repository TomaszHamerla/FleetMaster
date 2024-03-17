package com.example.service;

import com.example.configuration.ConfigProperty;
import com.example.exception.CarApiException;
import com.example.model.car.BrandDto;
import com.example.model.car.BrandResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RestClientTest(CarFetchServiceImpl.class)
class CarFetchServiceImplTest {
    @Autowired
    MockRestServiceServer server;
    @Autowired
    CarFetchServiceImpl carFetchService;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    ConfigProperty configProperty;

    @Test
    void getBrandsTestSuccess() throws JsonProcessingException {
        //given
        var brands = List.of(new BrandDto(1, "Audi"), new BrandDto(2, "BWM"), new BrandDto(3, "Opel"));
        var brandsResponse = new BrandResponse(brands);

        //when
        server.expect(requestTo("/makes?year=2015"))
                .andRespond(withSuccess(mapper.writeValueAsString(brandsResponse), MediaType.APPLICATION_JSON));

        //then
        List<BrandDto> response = carFetchService.getBrands();
        assertThat(response).isEqualTo(brands);
    }

    @Test
    void getBrandsTestWithCarApiDoesNotWorkShouldTrowCarApiException() {
        //when
        server.expect(requestTo("/makes?year=2015"))
                .andRespond(withBadRequest());
        //then
        assertThrows(CarApiException.class,()->carFetchService.getBrands());
    }
}