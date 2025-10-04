package com.ms.notificacao.application.gateways;

import org.springframework.amqp.core.Message;

public interface MessageConsumer {

    void enviaNotificacaoByBroker(Message message);
}
