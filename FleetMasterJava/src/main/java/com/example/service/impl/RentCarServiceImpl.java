package com.example.service.impl;

import com.example.exception.CarIdNotFound;
import com.example.exception.UserCarRentalBalanceException;
import com.example.model.car.BrandDto;
import com.example.model.car.Car;
import com.example.model.car.CarReturnResult;
import com.example.model.car.ModelDto;
import com.example.model.user.User;
import com.example.repository.UserRepository;
import com.example.service.interfaces.CarFetchService;
import com.example.service.interfaces.RentCarService;
import com.example.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentCarServiceImpl implements RentCarService {
    private final UserRepository repository;
    private final CarFetchService carFetchService;
    private final UserService userService;

    @Override
    public User rentCar(int userId, int brandId, int modelId) {
        User user = userService.getUserById(userId);
        if (user.isUserBlocked())
            throw new UserCarRentalBalanceException("User with given id is blocked !");
        BrandDto brandDto = getBrand(brandId);
        ModelDto modelDto = getModel(brandId, modelId);

        Car car = new Car();
        car.setBrand(brandDto.name());
        car.setModel(modelDto.name());
        car.setProductionYear(2015);  //only cars since 2015 available

        car.setUser(user);
        user.getCars().add(car);
        return repository.save(user);
    }
    @Override
    public CarReturnResult returnCar(int userId, int carId) {
        User user = userService.getUserById(userId);
        List<Car> cars = user.getCars().stream()
                .filter(c -> c.getId() == carId)
                .toList();
        if (cars.isEmpty())
            throw new CarIdNotFound("Given id not found !");

        Car car = cars.getFirst();
        double amount = getAmount(car,user);
        user.getCars().remove(car);
        validUserRentalBalance(user);
        repository.save(user);
        return new CarReturnResult(amount,"PLN");
    }
    private void validUserRentalBalance(User user){
        var balance = user.getCarRentalBalance();
        if (balance>10000)
            user.setUserBlocked(true);
    }

    private double getAmount(Car car,User user) {
        LocalDate now = LocalDate.now();
        LocalDate rentDate = car.getRentDate();
        int days = rentDate.until(now).getDays();
        double amount = days*199.99;
        user.setCarRentalBalance(user.getCarRentalBalance()+amount);
        repository.save(user);
        return amount;
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
