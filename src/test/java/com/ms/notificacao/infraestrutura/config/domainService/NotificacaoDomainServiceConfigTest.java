package com.ms.notificacao.infraestrutura.config.domainService;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.domain.domainService.implementations.NotificacaoDomainServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificacaoDomainServiceConfigTest {

    @Mock
    private Notificacao notificacaoMock;

    @InjectMocks
    private NotificacaoDomainServiceConfig config;

    @Test
    void notificacaoDomainService_shouldReturnBean() {
        NotificacaoDomainServiceImpl bean = config.notificacaoDomainService(notificacaoMock);

        assertNotNull(bean, "O bean NotificacaoDomainServiceImpl não deve ser nulo");
    }
}