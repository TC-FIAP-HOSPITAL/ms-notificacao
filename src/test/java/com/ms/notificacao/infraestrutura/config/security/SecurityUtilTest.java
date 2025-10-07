package com.ms.notificacao.infraestrutura.config.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SecurityUtilTest {

    private SecurityUtil securityUtil;

    @BeforeEach
    void setUp() {
        securityUtil = new SecurityUtil();
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getAuthentication_shouldReturnAuthentication() {
        var auth = new TestingAuthenticationToken("user", "token");
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThat(securityUtil.getAuthentication()).isEqualTo(auth);
    }

    @Test
    void getCurrentUsername_shouldReturnUsername() {
        var auth = new TestingAuthenticationToken("user123", "token");
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThat(securityUtil.getCurrentUsername()).isEqualTo("user123");
    }

    @Test
    void getCurrentUsername_shouldReturnNullWhenNoAuth() {
        assertThat(securityUtil.getCurrentUsername()).isNull();
    }

    @Test
    void getJwtToken_shouldReturnCredentials() {
        var auth = new TestingAuthenticationToken("user", "jwt-token");
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThat(securityUtil.getJwtToken()).isEqualTo("jwt-token");
    }

    @Test
    void getJwtToken_shouldReturnNullWhenNoAuth() {
        assertThat(securityUtil.getJwtToken()).isNull();
    }

    @Test
    void isAdmin_shouldReturnTrueWhenAdminAuthorityPresent() {
        var auth = new TestingAuthenticationToken("admin", "token",
                List.of(new SimpleGrantedAuthority(Role.ADMIN.toAuthority())));
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThat(securityUtil.isAdmin()).isTrue();
    }

    @Test
    void isAdmin_shouldReturnFalseWhenNoAdminAuthority() {
        var auth = new TestingAuthenticationToken("user", "token",
                List.of(new SimpleGrantedAuthority(Role.PACIENTE.toAuthority())));
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThat(securityUtil.isAdmin()).isFalse();
    }

    @Test
    void getRole_shouldReturnPacienteWhenNoAuthentication() {
        assertThat(securityUtil.getRole()).isEqualTo(Role.PACIENTE);
    }

    @Test
    void getRole_shouldMapAuthorityToRole() {
        var auth = new TestingAuthenticationToken("medico", "token",
                List.of(new SimpleGrantedAuthority(Role.MEDICO.toAuthority())));
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThat(securityUtil.getRole()).isEqualTo(Role.MEDICO);
    }

    @Test
    void getRole_shouldReturnPacienteWhenAuthoritiesEmpty() {
        var auth = new TestingAuthenticationToken("user", "token");
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThat(securityUtil.getRole()).isEqualTo(Role.PACIENTE);
    }

    @Test
    void getUserId_shouldExtractFromPrincipal() {
        MyUserDetails userDetails = MyUserDetails.builder()
                .userId(99L)
                .username("user")
                .password("pass")
                .authorities(List.of(new SimpleGrantedAuthority(Role.ENFERMEIRO.toAuthority())))
                .build();

        SecurityContextHolder.getContext()
                .setAuthentication(new TestingAuthenticationToken(userDetails, "token", userDetails.getAuthorities()));

        assertThat(securityUtil.getUserId()).isEqualTo(99L);
    }

    @Test
    void getUserId_shouldReturnNullWhenPrincipalNotMyUserDetails() {
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("user", "token"));

        assertThat(securityUtil.getUserId()).isNull();
    }

    @Test
    void getUserId_shouldReturnNullWhenNotAuthenticated() {
        SecurityContextHolder.getContext().setAuthentication(null);

        assertThat(securityUtil.getUserId()).isNull();
    }

    @Test
    void getUserId_shouldReturnNullWhenAuthNotAuthenticated() {
        var auth = new TestingAuthenticationToken("user", "token");
        auth.setAuthenticated(false);
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThat(securityUtil.getUserId()).isNull();
    }
}
