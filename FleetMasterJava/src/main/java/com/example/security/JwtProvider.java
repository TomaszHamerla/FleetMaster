package com.example.security;

import com.example.model.user.MyUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtEncoder jwtEncoder;

    public String createToken(Authentication authentication) {
        Instant now = Instant.now();
        long eightHours = 8 * 3600;
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        String authorities = principal.user().getRole().toString();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(eightHours))
                .subject(authentication.getName())
                .claim("authorities", authorities)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
