package com.example.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class AuthController {
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    AuthResponse login(Authentication authentication) {
        var token = jwtProvider.createToken(authentication);
        return new AuthResponse(HttpStatus.OK.value(), token);
    }
}
