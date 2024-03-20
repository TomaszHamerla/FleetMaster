package com.example.service;

import com.example.model.car.BrandDto;
import com.example.model.car.ModelDto;

import java.util.List;

public interface CarFetchService {
    List<BrandDto> getBrands();
    List<ModelDto> getModels(int brandId);
}