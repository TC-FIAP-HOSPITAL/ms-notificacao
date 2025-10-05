package com.ms.notificacao.infraestrutura.clients.usuario;

public record UsuarioDto(
        String id,
        String name,
        String email,
        String username,
        String role
) { }