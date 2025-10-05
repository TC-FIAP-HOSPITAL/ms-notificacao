package com.ms.notificacao.infraestrutura.config.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @Mock
    private AuthenticationManager authenticationManagerMock;

    @Mock
    private AuthenticationEntryPoint  authenticationEntryPoint;

    @Mock
    private AccessDeniedHandler acessDeniedHandler;

    @Mock
    private HttpSecurity httpSecurity;

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig(jwtAuthenticationFilter);
    }

    @Test
    void securityConfig_shouldCreateAuthenticationManagerAndPasswordEncoder() throws Exception {
        // Arrange
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManagerMock);

        // Act
        AuthenticationManager authenticationManager = securityConfig.authenticationManager(authenticationConfiguration);
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        // Assert
        assertNotNull(authenticationManager, "AuthenticationManager não deve ser nulo");
        assertNotNull(passwordEncoder, "PasswordEncoder não deve ser nulo");
        assertNotNull(jwtAuthenticationFilter, "JwtAuthenticationFilter não deve ser nulo");
    }

    @Test
    void securityConfig_shouldCreateSecurityFilterChain() throws Exception {
        // Mock dos objetos necessários
        HttpSecurity http = mock(HttpSecurity.class, RETURNS_SELF);

        // Mock de métodos encadeados que são chamados dentro do filterChain
        when(http.csrf(any())).thenReturn(http);
        when(http.authorizeHttpRequests(any())).thenReturn(http);
        when(http.addFilterBefore(any(), eq(UsernamePasswordAuthenticationFilter.class))).thenReturn(http);

        // Mock do build() para retornar um SecurityFilterChain
        DefaultSecurityFilterChain mockFilterChain = mock(DefaultSecurityFilterChain.class);
        when(http.build()).thenReturn(mockFilterChain);

        // Act
        SecurityFilterChain filterChain = securityConfig.filterChain(
                http,
                authenticationEntryPoint,
                acessDeniedHandler
        );

        // Assert
        assertNotNull(filterChain, "SecurityFilterChain não deve ser nulo");

        // Verifica se métodos do HttpSecurity foram chamados corretamente
        verify(http).csrf(any());
        verify(http).authorizeHttpRequests(any());
        verify(http).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        verify(http).build();
    }

}
