package com.ms.notificacao.infraestrutura.clients.brevo;

import com.ms.notificacao.infraestrutura.clients.brevo.dto.EmailRequestDto;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
public class BrevoEmailClient {

    private final RestTemplate restTemplate;
    private final BrevoProperties brevoProperties;

    public BrevoEmailClient(RestTemplate restTemplate, BrevoProperties brevoProperties) {
        this.restTemplate = restTemplate;
        this.brevoProperties = brevoProperties;
    }

    public String sendEmail(EmailRequestDto request) {
        String url = brevoProperties.getBaseUrl() + brevoProperties.getEndpoint();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("api-key", brevoProperties.getSecret());

        HttpEntity<EmailRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }
}
