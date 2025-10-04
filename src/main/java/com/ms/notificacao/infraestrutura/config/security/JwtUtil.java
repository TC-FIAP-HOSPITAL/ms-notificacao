package com.ms.notificacao.infraestrutura.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;
    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 1 day

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, String userId) {
        Date now = new Date();
        Date expiry = new Date(System.currentTimeMillis() + EXPIRATION);
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(Claims claims) {
        return claims.getSubject();
    }

    public Role extractRole(Claims claims) {
        String roleClaim = claims.get("role", String.class);
        if (roleClaim != null && !roleClaim.isBlank()) {
            return Role.valueOf(roleClaim.toUpperCase());
        }

        String subject = claims.getSubject();
        if (subject != null && !subject.isBlank()) {
            String normalized = subject.startsWith("ROLE_") ? subject.substring(5) : subject;
            return Role.valueOf(normalized.toUpperCase());
        }

        return null;
    }

    public String extractUserId(Claims claims) {
        return claims.get("userId", String.class);
    }

    public Date extractExpirationDate(Claims claims) {
        return claims.getExpiration();
    }

    public Date extractExpirationDateFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
