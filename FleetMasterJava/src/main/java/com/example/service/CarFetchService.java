package com.example.service;

import com.example.model.car.BrandDto;

import java.util.List;

public interface CarFetchService {
    List<BrandDto> getBrands();
}
