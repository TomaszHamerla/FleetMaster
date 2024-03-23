package com.example.model.car;

import java.time.LocalDate;

public record CarDto(int id, String brand, String model, int productionYear, LocalDate rentDate) {
}
