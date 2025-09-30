package com.ms.notificacao.infraestrutura.config.usecase;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.application.usecase.implementations.InserirNotificacaoUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InserirNotificacaoConfig {

    @Bean
    public InserirNotificacaoUseCaseImpl inserirNotificacaoUseCase(Notificacao notificacao) {
        return new InserirNotificacaoUseCaseImpl(notificacao);
    }
}
