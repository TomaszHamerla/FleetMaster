package com.example.service;

import com.example.configuration.ConfigProperty;
import com.example.exception.BrandNotFoundException;
import com.example.exception.CarApiException;
import com.example.model.car.BrandDto;
import com.example.model.car.BrandResponse;
import com.example.model.car.ModelDto;
import com.example.model.car.ModelResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Slf4j
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
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new CarApiException(response.getStatusText());
                }))
                .body(ModelResponse.class);

        List<ModelDto> models = body.data().stream()
                .map(m -> new ModelDto(m.name()))
                .toList();

        validArrResult(models);
        return models;
    }

    private void validArrResult(List<ModelDto> models) {
        if (models.isEmpty())
            throw new BrandNotFoundException("Given brand id not exists");
        log.info("Correct brandId");
    }
}
