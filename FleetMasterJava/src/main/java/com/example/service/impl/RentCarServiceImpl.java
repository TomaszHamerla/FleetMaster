package com.example.service.impl;

import com.example.model.car.BrandDto;
import com.example.model.car.Car;
import com.example.model.car.ModelDto;
import com.example.model.user.User;
import com.example.repository.UserRepository;
import com.example.service.interfaces.CarFetchService;
import com.example.service.interfaces.RentCarService;
import com.example.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentCarServiceImpl implements RentCarService {
    private final UserRepository repository;
    private final CarFetchService carFetchService;
    private final UserService userService;

    @Override
    public User rentCar(int userId, int brandId, int modelId) {
        BrandDto brandDto = getBrand(brandId);
        ModelDto modelDto = getModel(brandId, modelId);
        User user = userService.getUserById(userId);

        Car car = new Car();
        car.setBrand(brandDto.name());
        car.setModel(modelDto.name());
        car.setProductionYear(2015);  //only cars since 2015 available

        car.setUser(user);
        user.getCars().add(car);
        return repository.save(user);
    }

    private ModelDto getModel(int brandId, int modelId) {
        return carFetchService.getModels(brandId).stream()
                .filter(m -> m.id() == modelId)
                .toList()
                .getFirst();
    }

    private BrandDto getBrand(int brandId) {
        return carFetchService.getBrands().stream()
                .filter(b -> b.id() == brandId)
                .toList()
                .getFirst();
    }
}
