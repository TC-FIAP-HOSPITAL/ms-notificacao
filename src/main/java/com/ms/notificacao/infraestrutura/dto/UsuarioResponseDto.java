package com.ms.notificacao.infraestrutura.dto;

import java.time.LocalDateTime;

public record UsuarioResponseDto(
        String id,
        String name,
        String email,
        String username,
        String role,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}