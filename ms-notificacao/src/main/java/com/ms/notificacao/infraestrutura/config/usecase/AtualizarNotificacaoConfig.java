package com.ms.notificacao.infraestrutura.config.usecase;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.application.usecase.implementations.AtualizarNotificacaoUseCaseImpl;
import com.ms.notificacao.domain.domainService.NotificacaoDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AtualizarNotificacaoConfig {

    @Bean
    public AtualizarNotificacaoUseCaseImpl atualizarNotificacaoUseCase(Notificacao notificacao,
                                                                       NotificacaoDomainService notificacaoDomainService) {
        return new AtualizarNotificacaoUseCaseImpl(notificacao, notificacaoDomainService);
    }
}
