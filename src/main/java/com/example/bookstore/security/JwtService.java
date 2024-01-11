package com.example.bookstore.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("QJQlI8dWpwcRExmLOXjVMOh4eqxTsw2LPhlKeEkh3RI")
    private String signatureKey;
    @Value("1")
    private Integer hours;

    private SecretKey key;

    @PostConstruct
    private void postConstruct() {
        key = Keys.hmacShaKeyFor(signatureKey.getBytes());
    }

    public String generateToken(String username) {
        JwtBuilder builder = Jwts.builder();
        Date currentDate = new Date(System.currentTimeMillis());
        return builder
                .subject(username)
                .issuedAt(currentDate)
                .expiration(new Date(currentDate.getTime() + Duration.ofHours(hours).toMillis()))
                .signWith(key)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            JwtParser parser = Jwts.parser()
                    .verifyWith(key)
                    .build();
            Jws<Claims> claims = parser.parseSignedClaims(token);
            Date expiration = claims.getPayload().getExpiration();
            return expiration.before(new Date());
        } catch (JwtException ex) {
            throw new JwtException("Invalid JWT Token");
        }
    }

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        JwtParser parser = Jwts.parser().verifyWith(key).build();
        Claims payload = parser.parseSignedClaims(token).getPayload();
        return claimsResolver.apply(payload);
    }
}
