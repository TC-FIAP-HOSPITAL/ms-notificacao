package com.ms.notificacao.application.gateways;

import com.ms.notificacao.infraestrutura.messaging.AgendamentoDto;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public interface MessageConsumer {

    void notification(@Payload AgendamentoDto agendamentoEvent,
                      @Header("Authorization") String token);
}
