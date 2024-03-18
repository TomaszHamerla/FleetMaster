package com.example.controller;

import com.example.model.car.BrandDto;
import com.example.model.car.ModelDto;
import com.example.service.CarFetchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarFetchService carFetchService;
    @GetMapping
    List<BrandDto>getBrands(){
        return carFetchService.getBrands();
    }
    @GetMapping("/{brandId}")
    List<ModelDto>getModelsByBrandId(@PathVariable int brandId){
        return carFetchService.getModels(brandId);
    }
}
