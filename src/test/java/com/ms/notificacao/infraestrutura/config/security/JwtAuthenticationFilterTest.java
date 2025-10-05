package com.ms.notificacao.infraestrutura.config.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_shouldAuthenticateWhenTokenValid() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Claims claims = mock(Claims.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.validateToken("token")).thenReturn(true);
        when(jwtUtil.extractClaims("token")).thenReturn(claims);
        when(jwtUtil.extractUsername(claims)).thenReturn("username");
        when(jwtUtil.extractRole(claims)).thenReturn(Role.ENFERMEIRO);
        when(jwtUtil.extractUserId(claims)).thenReturn("7");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNotNull();
        assertThat(authentication.getPrincipal()).isInstanceOf(MyUserDetails.class);
        assertThat(authentication.getAuthorities()).extracting("authority")
                .containsExactly(Role.ENFERMEIRO.toAuthority());
    }

    @Test
    void doFilterInternal_shouldSkipWhenTokenInvalid() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.validateToken("token")).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, never()).extractClaims(any());
        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }
}