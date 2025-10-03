package com.ms.notificacao.infraestrutura.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "brevo")
public class BrevoProperties {

    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
