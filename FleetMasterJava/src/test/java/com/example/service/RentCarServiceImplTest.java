package com.example.service;

import com.example.exception.CarIdNotFound;
import com.example.model.car.BrandDto;
import com.example.model.car.Car;
import com.example.model.car.CarReturnResult;
import com.example.model.car.ModelDto;
import com.example.model.user.Role;
import com.example.model.user.User;
import com.example.repository.UserRepository;
import com.example.service.impl.RentCarServiceImpl;
import com.example.service.interfaces.CarFetchService;
import com.example.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class RentCarServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    CarFetchService carFetchService;
    @Mock
    UserService userService;
    @InjectMocks
    RentCarServiceImpl service;

    @Test
    void rentCarSuccess() {
        //given
        var brandDtos = List.of(new BrandDto(1, "Audi"), new BrandDto(2, "BMW"));
        var modelDtos = List.of(new ModelDto(1, 1, "A3"), new ModelDto(2, 1, "A4"));
        User user = new User();
        user.setPassword("123");
        user.setUsername("User");
        user.setEmail("user@gmail.com");
        user.setRole(Role.ADMIN);
        given(carFetchService.getBrands()).willReturn(brandDtos);
        given(carFetchService.getModels(anyInt())).willReturn(modelDtos);
        given(userService.getUserById(anyInt())).willReturn(user);
        given(userRepository.save(any())).willReturn(user);

        //when
        User savedUser = service.rentCar(1, 1, 1);

        //then
        var car = savedUser.getCars().getFirst();
        assertThat(car.getBrand()).isEqualTo("Audi");
        assertThat(car.getModel()).isEqualTo("A3");
        assertThat(car.getUser()).isEqualTo(user);
        assertThat(car.getProductionYear()).isEqualTo(2015);
        assertThat(car.getRentDate()).isEqualTo(LocalDate.now());
    }
    @Test
    void returnCarSuccess(){
        //given
        Car car = new Car();
        car.setId(1);
        car.setBrand("Audi");
        car.setModel("A3");
        car.setRentDate(LocalDate.now().minusDays(10));

        User user = new User();
        user.setPassword("123");
        user.setUsername("User");
        user.setEmail("user@gmail.com");
        user.setRole(Role.ADMIN);
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        user.setCars(cars);
        given(userService.getUserById(anyInt())).willReturn(user);
        given(userRepository.save(user)).willReturn(user);

        //when
        CarReturnResult carResult = service.returnCar(1, 1);

        //then
        assertThat(carResult.amount()).isEqualTo(199.99*10);
        assertThat(carResult.currency()).isEqualTo("PLN");
        assertThat(user.getCarRentalBalance()).isEqualTo(199.99*10);
        assertThat(user.getCars()).isEmpty();
    }
    @Test
    void returnCarWithCarInNotFoundShouldThrowCarIdNotFound(){
        //given
        User user = new User();
        user.setPassword("123");
        user.setUsername("User");
        user.setEmail("user@gmail.com");
        user.setRole(Role.ADMIN);
        given(userService.getUserById(anyInt())).willReturn(user);

        //when
        Exception exception = catchException(() -> service.returnCar(1, 1));

        //then
        assertThat(exception).isInstanceOf(CarIdNotFound.class)
                .hasMessage("Given id not found !");
        verify(userRepository,never()).save(any());
    }
}