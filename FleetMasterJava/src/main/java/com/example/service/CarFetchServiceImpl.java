package com.example.service;

import com.example.configuration.ConfigProperty;
import com.example.exception.CarApiException;
import com.example.model.car.BrandDto;
import com.example.model.car.BrandResponse;
import com.example.model.car.ModelDto;
import com.example.model.car.ModelResponse;
import org.springframework.http.HttpStatusCode;
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
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new CarApiException(response.getStatusText());
                }))
                .body(BrandResponse.class);

        return body.data().stream()
                .map(b -> new BrandDto(b.id(), b.name()))
                .toList();
    }

    @Override
    public List<ModelDto> getModels(int brandId) {
        ModelResponse body = restClient.get()
                .uri("/models?year=2015&make_id=" + brandId)
                .retrieve()
                .body(ModelResponse.class);

        return body.data().stream()
                .map(m -> new ModelDto(m.name()))
                .toList();
    }
}
