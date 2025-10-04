package com.ms.notificacao.infraestrutura.config.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims = jwtUtil.extractClaims(token);
        String username = jwtUtil.extractUsername(claims);
        Role role = jwtUtil.extractRole(claims);
        String userIdClaim = jwtUtil.extractUserId(claims);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Role resolvedRole = role != null ? role : Role.PACIENTE;
            Collection<? extends GrantedAuthority> authorities = List
                    .of(new SimpleGrantedAuthority(resolvedRole.toAuthority()));

            Long userId = null;
            if (userIdClaim != null && !userIdClaim.isBlank()) {
                try {
                    userId = Long.valueOf(userIdClaim);
                } catch (NumberFormatException ignored) {
                    // leave userId as null when claim is not numeric
                }
            }

            MyUserDetails principal = MyUserDetails.builder()
                    .userId(userId)
                    .username(username)
                    .authorities(authorities)
                    .build();

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,
                    token, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}