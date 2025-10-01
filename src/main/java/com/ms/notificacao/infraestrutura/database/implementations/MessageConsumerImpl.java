package com.ms.notificacao.infraestrutura.database.implementations;

import com.ms.notificacao.application.gateways.MessageConsumer;
import com.ms.notificacao.infraestrutura.messaging.AgendamentoDto;

public class MessageConsumerImpl implements MessageConsumer {

    @Override
    public void consume(AgendamentoDto message) {
        System.out.println("Estou ouvindo as mensagem da bagaça ao lado ");
    }
}
