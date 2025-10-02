package com.ms.notificacao.application.gateways;

import com.ms.notificacao.infraestrutura.messaging.AgendamentoDto;

public interface MessageConsumer {

    void enviaNotificacaoByBroker(AgendamentoDto message);
}
