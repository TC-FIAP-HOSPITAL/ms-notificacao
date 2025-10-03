package com.ms.notificacao.infraestrutura.clients;

import com.ms.notificacao.infraestrutura.config.BrevoProperties;
import com.ms.notificacao.infraestrutura.dto.EmailRequestDto;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class EmailClient {

    private final RestTemplate restTemplate;
    private final BrevoProperties brevoProperties;

    public EmailClient(RestTemplate restTemplate, BrevoProperties brevoProperties) {
        this.restTemplate = restTemplate;
        this.brevoProperties = brevoProperties;
    }

    public String sendEmail(EmailRequestDto request) {
        String url = "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", brevoProperties.getApiKey());

        HttpEntity<EmailRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }

}
