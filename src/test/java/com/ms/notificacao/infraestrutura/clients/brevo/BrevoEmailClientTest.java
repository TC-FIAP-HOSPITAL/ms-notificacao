package com.ms.notificacao.infraestrutura.clients.brevo;

import com.ms.notificacao.infraestrutura.clients.brevo.dto.EmailRequestDto;
import com.ms.notificacao.infraestrutura.clients.brevo.dto.Recipient;
import com.ms.notificacao.infraestrutura.clients.brevo.dto.Sender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BrevoEmailClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private BrevoProperties brevoProperties;

    @InjectMocks
    private BrevoEmailClient brevoEmailClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmail_shouldReturnResponseBody() {
        // Arrange
        String expectedResponse = "{\"message\":\"Email sent successfully\"}";
        EmailRequestDto request = new EmailRequestDto(
                new Sender("from@example.com", "Sender Name"),
                List.of(new Recipient("to@example.com", "Recipient Name")),
                "Test Subject",
                "Test Content"
        );

        String url = "https://api.brevo.com/v3/smtp/email";
        when(brevoProperties.getBaseUrl()).thenReturn("https://api.brevo.com");
        when(brevoProperties.getEndpoint()).thenReturn("/v3/smtp/email");
        when(brevoProperties.getSecret()).thenReturn("dummy-api-key");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("api-key", "dummy-api-key");

        HttpEntity<EmailRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(responseEntity);

        // Act
        String actualResponse = brevoEmailClient.sendEmail(request);

        // Assert
        assertEquals(expectedResponse, actualResponse);

        // Verify that RestTemplate was called once
        verify(restTemplate, times(1)).exchange(eq(url), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
    }
}