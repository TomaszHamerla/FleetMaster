package com.example.service;

import com.example.configuration.ConfigProperty;
import com.example.model.car.BrandDto;
import com.example.model.car.BrandResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class CarFetchServiceImpl implements CarFetchService {
    private final RestClient restClient;

    public CarFetchServiceImpl(RestClient.Builder builder, ConfigProperty prop) {
        this.restClient = builder
                .baseUrl(prop.getCarApiBaseUrl())
                .build();
    }

    @Override
    public List<BrandDto> getBrands() {
        BrandResponse body = restClient.get()
                .uri("/makes?year=2015")
                .retrieve()
                .body(BrandResponse.class);

        return body.data().stream()
                .map(b -> new BrandDto(b.id(), b.name()))
                .toList();
    }
}
