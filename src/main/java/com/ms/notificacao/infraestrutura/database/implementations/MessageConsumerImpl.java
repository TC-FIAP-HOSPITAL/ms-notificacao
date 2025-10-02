package com.ms.notificacao.infraestrutura.database.implementations;

import com.ms.notificacao.application.gateways.MessageConsumer;
import com.ms.notificacao.application.usecase.InserirNotificacaoUseCase;
import com.ms.notificacao.domain.enums.CanalEnum;
import com.ms.notificacao.domain.enums.StatusNotificacaoEnum;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import com.ms.notificacao.infraestrutura.messaging.AgendamentoDto;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import org.slf4j.Logger;

@Service
public class MessageConsumerImpl implements MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumerImpl.class);

    public final InserirNotificacaoUseCase inserirNotificacaoUseCase;

    public MessageConsumerImpl(InserirNotificacaoUseCase inserirNotificacaoUseCase) {
        this.inserirNotificacaoUseCase = inserirNotificacaoUseCase;
    }

    @Override
    @RabbitListener(queues = "notificacao-queue")
    public void enviaNotificacaoByBroker(final AgendamentoDto message) {

        if(Objects.nonNull(message)) {
            logger.info("Recebida mensagem para pacienteId={} | consultaId={} | dataAgendamento={} | status={} | tipoAtendimento={}",
                    message.pacienteId(), message.id(), message.dataAgendamento(), message.status(), message.tipoAtendimento()
            );

            NotificacaoDomain domain = new NotificacaoDomain();
            domain.setIdConsulta(message.id());
            domain.setIdPaciente(message.pacienteId());
            domain.setCanal(CanalEnum.PUSH);
            domain.setStatus(StatusNotificacaoEnum.ENVIADA);
            domain.setDataAgendamentoEnvio(message.dataAgendamento());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
            String msg = String.format(
                    "Olá! Você tem uma %s de %s com status %s marcada para o dia %s. Consulte os detalhes no aplicativo.",
                    message.tipoAtendimento().toLowerCase(), message.especialidade().toUpperCase(), message.status(), message.dataAgendamento().format(formatter)
            );
            domain.setMensagem(msg);

            inserirNotificacaoUseCase.inserir(domain);
        }
    }
}
