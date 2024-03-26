package com.example.controller;

import com.example.converter.UserToUserDtoConverter;
import com.example.model.car.CarRequest;
import com.example.model.car.CarReturnResult;
import com.example.model.user.User;
import com.example.model.user.dto.UserDto;
import com.example.service.interfaces.RentCarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cars")
@RequiredArgsConstructor
public class RentCarController {
    private final RentCarService rentCarService;
    private final UserToUserDtoConverter converter;
    @PostMapping("/rent")
    UserDto rentCar(@RequestBody CarRequest carRequest){
        User user = rentCarService.rentCar(carRequest.userId(), carRequest.brandId(), carRequest.modelId());
        return converter.convert(user);
    }
    @DeleteMapping("/{carId}/users/{userId}/return")
    CarReturnResult returnCar(@PathVariable int carId,@PathVariable int userId){
        return rentCarService.returnCar(userId,carId);
    }

}
