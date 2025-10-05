package com.ms.notificacao.infraestrutura.clients.usuario;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "usuario-service", url = "${usuario.service.url}")
public interface UsuarioClientImpl {

    @GetMapping("v1/users/{id}")
    UsuarioDto buscaUsuarioID(@PathVariable("id") Long id,
                              @RequestHeader("Authorization") String bearerToken);

}
