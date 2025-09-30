package com.ms.notificacao.infraestrutura.config.domainService;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.domain.domainService.implementations.NotificacaoDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificacaoDomainServiceConfig {

    @Bean
    public NotificacaoDomainServiceImpl notificacaoDomainService(Notificacao notificacao) {
        return new NotificacaoDomainServiceImpl(notificacao);
    }
}
