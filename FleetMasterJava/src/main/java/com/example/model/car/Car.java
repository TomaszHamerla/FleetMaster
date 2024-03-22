package com.example.model.car;

import com.example.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String brand;
    private String model;
    private int productionYear;
    private LocalDate rentDate = LocalDate.now();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
