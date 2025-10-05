package com.ms.notificacao.infraestrutura.clients.brevo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrevoPropertiesTest {
    @Test
    void testGettersAndSetters() {
        // Arrange
        BrevoProperties properties = new BrevoProperties();
        String baseUrl = "https://api.brevo.com";
        String secret = "dummy-api-key";
        String endpoint = "/v3/smtp/email";

        // Act
        properties.setBaseUrl(baseUrl);
        properties.setSecret(secret);
        properties.setEndpoint(endpoint);

        // Assert
        assertEquals(baseUrl, properties.getBaseUrl());
        assertEquals(secret, properties.getSecret());
        assertEquals(endpoint, properties.getEndpoint());
    }
}