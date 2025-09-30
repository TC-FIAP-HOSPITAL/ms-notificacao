package com.ms.notificacao.infraestrutura.config.usecase;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.application.usecase.implementations.BuscarNotificacaoUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuscarNotificacaoConfig {

    @Bean
    public BuscarNotificacaoUseCaseImpl buscarNotificacaoUseCase(Notificacao notificacao) {
        return new BuscarNotificacaoUseCaseImpl(notificacao);
    }
}
