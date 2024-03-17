package com.example.repository;

import com.example.model.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Integer> {
}
