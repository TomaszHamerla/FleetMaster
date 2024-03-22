package com.example.service;

import com.example.configuration.ConfigProperty;
import com.example.exception.CarApiException;
import com.example.model.car.BrandDto;
import com.example.model.car.BrandResponse;
import com.example.model.car.ModelDto;
import com.example.model.car.ModelResponse;
import com.example.service.impl.CarFetchServiceImpl;
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
        assertThrows(CarApiException.class, () -> carFetchService.getBrands());
    }

    @Test
    void getModelsTestSuccess() throws JsonProcessingException {
        //given
        var models = List.of(new ModelDto(1,2,"x3"), new ModelDto(2,2,"x4"), new ModelDto(3,2,"x5"));
        var modelResponse = new ModelResponse(models);

        //when
        server.expect(requestTo("/models?year=2015&make_id=1"))
                .andRespond(withSuccess(mapper.writeValueAsString(modelResponse), MediaType.APPLICATION_JSON));

        //then
        List<ModelDto> response = carFetchService.getModels(1);
        assertThat(response).isEqualTo(models);
    }

    @Test
    void getModelsByBrandIdWithIdNotExistsThrowsCarApiException() {
        //when
        server.expect(requestTo("/models?year=2015&make_id=12232"))
                .andRespond(withResourceNotFound());
        //then
        assertThrows(CarApiException.class, () -> carFetchService.getModels(12232));
    }
    @Test
    void getBrandsWithPaginationSuccess() throws JsonProcessingException {
        //given
        var brands = List.of(new BrandDto(1, "Audi"), new BrandDto(2, "BWM"), new BrandDto(3, "Opel"));
        var brandsResponse = new BrandResponse(brands);
        //when
        server.expect(requestTo("/makes?page=1&limit=3&year=2015"))
                .andRespond(withSuccess(mapper.writeValueAsString(brandsResponse),MediaType.APPLICATION_JSON));
        //then
        List<BrandDto> response = carFetchService.getBrands(1, 3);
        assertThat(response).isEqualTo(brands);
    }
    @Test
    void getBrandsWithPaginationNotFoundThrowsCarApiException(){
        //when
        server.expect(requestTo("/makes?page=1&limit=4546643&year=2015"))
                .andRespond(withResourceNotFound());
        //then
        assertThrows(CarApiException.class,()->carFetchService.getBrands(1,4546643));
    }
}