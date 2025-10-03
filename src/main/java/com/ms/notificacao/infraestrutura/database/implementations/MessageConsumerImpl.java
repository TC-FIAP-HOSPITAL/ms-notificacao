package com.ms.notificacao.infraestrutura.database.implementations;

import com.ms.notificacao.application.gateways.MessageConsumer;
import com.ms.notificacao.application.gateways.UsuarioClientPort;
import com.ms.notificacao.application.usecase.InserirNotificacaoUseCase;
import com.ms.notificacao.domain.enums.CanalEnum;
import com.ms.notificacao.domain.enums.StatusNotificacaoEnum;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import com.ms.notificacao.infraestrutura.clients.EmailClient;
import com.ms.notificacao.infraestrutura.dto.EmailRequestDto;
import com.ms.notificacao.infraestrutura.dto.UsuarioResponseDto;
import com.ms.notificacao.infraestrutura.messaging.AgendamentoDto;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;

@Service
public class MessageConsumerImpl implements MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumerImpl.class);

    private final InserirNotificacaoUseCase inserirNotificacaoUseCase;
    private final UsuarioClientPort usuarioClientPort;
    private final EmailClient emailSender;

    public MessageConsumerImpl(InserirNotificacaoUseCase inserirNotificacaoUseCase, UsuarioClientPort usuarioClientPort, EmailClient emailSender) {
        this.inserirNotificacaoUseCase = inserirNotificacaoUseCase;
        this.usuarioClientPort = usuarioClientPort;
        this.emailSender = emailSender;
    }

    @Override
    @RabbitListener(queues = "notificacao-queue")
    public void enviaNotificacaoByBroker(final AgendamentoDto message) {

        if(Objects.nonNull(message)) {
            logger.info("Recebida mensagem para pacienteId={} | consultaId={} | dataAgendamento={} | status={} | tipoAtendimento={}",
                    message.pacienteId(), message.id(), message.dataAgendamento(), message.status(), message.tipoAtendimento()
            );

            UsuarioResponseDto userResponse = usuarioClientPort.buscaUsuarioID(message.pacienteId(), "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6IjEiLCJpYXQiOjE3NTk0NTA3MjUsImV4cCI6MTc1OTUzNzEyNX0.T--5u7m7qSf1JwTx0CT1NXGmLe7JKc6mAqAsFibAV2Y");

            if(userResponse.email() != null){
                emailSender(message, userResponse);
            }

            createNotificationDatabase(message);
        }
    }


    private void emailSender(AgendamentoDto message, UsuarioResponseDto userResponse) {
        // Formata a data da consulta
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        var dataFormatada = message.dataAgendamento().format(formatter);

        // Monta a mensagem do e-mail
        var msg = String.format(
                "Olá %s! Você tem uma %s de %s com status %s marcada para o dia %s. Consulte os detalhes no aplicativo.",
                userResponse.name(),
                message.tipoAtendimento().toLowerCase(),
                message.especialidade().toUpperCase(),
                message.status(),
                dataFormatada
        );

        // Cria o DTO do e-mail
        var emailRequest = new EmailRequestDto(
                new EmailRequestDto.Sender("Sistema de Notificação", "no-reply@minhaempresa.com"),
                List.of(new EmailRequestDto.Recipient(userResponse.name(), userResponse.email())),
                message.tipoAtendimento(),
                msg,
                new EmailRequestDto.Recipient("Suporte", "suporte@minhaempresa.com")
        );

        // Envia o e-mail
        emailSender.sendEmail(emailRequest);
    }

    private void createNotificationDatabase(AgendamentoDto  message) {
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
