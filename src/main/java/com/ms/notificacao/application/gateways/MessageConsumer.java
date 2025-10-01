package com.ms.notificacao.application.gateways;

import com.ms.notificacao.infraestrutura.messaging.AgendamentoDto;

public interface MessageConsumer {

    void consume(AgendamentoDto message);
}
