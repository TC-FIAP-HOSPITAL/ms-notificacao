package com.ms.notificacao.infraestrutura.database.implementations;

import com.ms.notificacao.application.gateways.UsuarioClientPort;
import com.ms.notificacao.infraestrutura.dto.UsuarioResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

@Service
public class UsuarioClientImpl implements UsuarioClientPort {

    private final RestTemplate restTemplate;

    public UsuarioClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UsuarioResponseDto buscaUsuarioID(Long id, String jwtToken) {
        String url = "http://localhost:9088/v1/users/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken); // mais simples que headers.set("Authorization", ...)

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UsuarioResponseDto.class
        ).getBody();
    }
}
