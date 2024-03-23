package com.example.converter;

import com.example.model.car.Car;
import com.example.model.car.CarDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarListToCarDtoList implements Converter<List<Car>,List<CarDto>> {
    @Override
    public List<CarDto> convert(List<Car> cars) {
       return cars.stream()
                .map(car->new CarDto(car.getId(), car.getBrand(), car.getModel(), car.getProductionYear(), car.getRentDate()))
                .toList();
    }
}
