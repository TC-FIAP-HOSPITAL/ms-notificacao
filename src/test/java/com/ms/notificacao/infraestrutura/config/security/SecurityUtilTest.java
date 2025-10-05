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
    void isAdmin_shouldReturnTrueWhenAdminAuthorityPresent() {
        var authentication = new TestingAuthenticationToken("admin", "token",
                List.of(new SimpleGrantedAuthority(Role.ADMIN.toAuthority())));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThat(securityUtil.isAdmin()).isTrue();
    }

    @Test
    void getRole_shouldReturnPacienteWhenNoAuthentication() {
        assertThat(securityUtil.getRole()).isEqualTo(Role.PACIENTE);
    }

    @Test
    void getRole_shouldMapAuthorityToRole() {
        var authentication = new TestingAuthenticationToken("medico", "token",
                List.of(new SimpleGrantedAuthority(Role.MEDICO.toAuthority())));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThat(securityUtil.getRole()).isEqualTo(Role.MEDICO);
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
}