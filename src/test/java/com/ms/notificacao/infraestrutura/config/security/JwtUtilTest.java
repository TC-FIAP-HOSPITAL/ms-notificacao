package com.ms.notificacao.infraestrutura.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {

    private static final String SECRET = "0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF";

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", SECRET);
    }

    @AfterEach
    void tearDown() {
        jwtUtil = null;
    }

    @Test
    void generateAndValidateToken_shouldReturnValidTokenWithClaims() {
        String token = jwtUtil.generateToken("user@example.com", "42");

        assertThat(jwtUtil.validateToken(token)).isTrue();

        Claims claims = jwtUtil.extractClaims(token);
        assertThat(jwtUtil.extractUsername(claims)).isEqualTo("user@example.com");
        assertThat(jwtUtil.extractUserId(claims)).isEqualTo("42");
        assertThat(jwtUtil.extractExpirationDate(claims)).isAfter(new Date());
    }

    @Test
    void validateToken_shouldReturnFalseForDifferentSecret() {
        String token = jwtUtil.generateToken("user", "1");

        JwtUtil otherUtil = new JwtUtil();
        ReflectionTestUtils.setField(otherUtil, "jwtSecret", SECRET + "DIFF");

        assertThat(otherUtil.validateToken(token)).isFalse();
    }

    @Test
    void extractRole_shouldFallbackToSubjectWhenRoleClaimMissing() {
        Claims claims = Jwts.claims();
        claims.setSubject("ROLE_ADMIN");

        Role role = jwtUtil.extractRole(claims);

        assertThat(role).isEqualTo(Role.ADMIN);
    }

    @Test
    void extractRole_shouldReturnNullWhenNotPresent() {
        Claims claims = Jwts.claims();

        Role role = jwtUtil.extractRole(claims);

        assertThat(role).isNull();
    }
}