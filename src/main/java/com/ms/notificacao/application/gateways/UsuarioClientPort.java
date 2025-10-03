package com.ms.notificacao.application.gateways;


import com.ms.notificacao.infraestrutura.dto.UsuarioResponseDto;

public interface UsuarioClientPort {

    UsuarioResponseDto buscaUsuarioID(Long id, String jwtToken);
}
