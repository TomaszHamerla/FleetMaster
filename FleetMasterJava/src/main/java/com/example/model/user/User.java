package com.example.model.user;

import com.example.model.car.Car;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Username is required")
    @Column(unique = true, length = 35)
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @Column(unique = true, length = 35)
    @NotBlank(message = "Email is required")
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonIgnore
    private double carRentalBalance = 0;
    @JsonIgnore
    private boolean userBlocked = false; //if carRentalBalance is over then 10 000 then user gonna be blocked
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Car> cars = new ArrayList<>();
}
