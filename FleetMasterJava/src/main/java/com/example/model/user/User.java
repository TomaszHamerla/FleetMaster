package com.example.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
