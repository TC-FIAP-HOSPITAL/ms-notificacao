package com.ms.notificacao.infraestrutura.messaging.consumer;

import com.ms.notificacao.application.gateways.MessageConsumer;
import com.ms.notificacao.application.usecase.InserirNotificacaoUseCase;
import com.ms.notificacao.domain.enums.CanalEnum;
import com.ms.notificacao.domain.enums.StatusNotificacaoEnum;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import com.ms.notificacao.infraestrutura.clients.brevo.BrevoEmailClient;
import com.ms.notificacao.infraestrutura.clients.brevo.dto.EmailRequestDto;
import com.ms.notificacao.infraestrutura.clients.brevo.dto.Recipient;
import com.ms.notificacao.infraestrutura.clients.brevo.dto.Sender;
import com.ms.notificacao.infraestrutura.clients.usuario.UsuarioClientImpl;
import com.ms.notificacao.infraestrutura.clients.usuario.UsuarioDto;
import com.ms.notificacao.infraestrutura.messaging.AgendamentoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class MessageConsumerImpl implements MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumerImpl.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

    private final InserirNotificacaoUseCase inserirNotificacaoUseCase;
    private final MessageConverter messageConverter;
    private final UsuarioClientImpl usuarioClientPort;
    private final BrevoEmailClient brevoEmailClient;

    public MessageConsumerImpl(InserirNotificacaoUseCase inserirNotificacaoUseCase,
                               MessageConverter messageConverter,
                               UsuarioClientImpl usuarioClientPort, BrevoEmailClient brevoEmailClient) {
        this.inserirNotificacaoUseCase = inserirNotificacaoUseCase;
        this.messageConverter = messageConverter;
        this.usuarioClientPort = usuarioClientPort;
        this.brevoEmailClient = brevoEmailClient;
    }

    @Override
    @RabbitListener(queues = "notificacao-queue")
    public void notification(@Payload AgendamentoDto agendamentoEvent,
                             @Header ("Authorization") String tokenAuthenticator) {

        //TODO: REALIZAR A IMPLEMENTAÇÃO NO PROXIMO TC4 PARA ENVIAR EMAIL, SMS PARA PACIENTE
//        if(tokenAuthenticator != null){
//            UsuarioDto usuario = usuarioClientPort.buscaUsuarioID(agendamentoEvent.pacienteId(), tokenAuthenticator);
//            enviarEmail(agendamentoEvent, usuario);
//        }else{
//            logger.warn("Não foi possivel enviar email devido bearer token estar null");
//        }

        armazenaNotificacao(agendamentoEvent);
    }

//    private String enviarEmail(AgendamentoDto agendamento, UsuarioDto usuario) {
//        String dataFormatada = agendamento.dataAgendamento().format(FORMATTER);
//
//        String conteudo = String.format(
//                "Olá %s! Você tem uma %s de %s com status %s marcada para o dia %s. Consulte os detalhes no aplicativo.",
//                usuario.name(),
//                agendamento.tipoAtendimento().toLowerCase(),
//                agendamento.especialidade().toUpperCase(),
//                agendamento.status(),
//                dataFormatada
//        );
//
//        EmailRequestDto emailRequest = new EmailRequestDto(
//                new Sender("SISTEMA AGENDAMENTO", "myservice.techn@gmail.com"),
//                List.of(new Recipient(usuario.email(), usuario.name())),
//                agendamento.tipoAtendimento(),
//                conteudo
//        );
//
//        String response = brevoEmailClient.sendEmail(emailRequest);
//
//        if(HttpStatus.UNAUTHORIZED.equals(response)){
//            logger.error("Brevo API KEY expirada, altere a api key");
//        }
//        logger.info("E-mail enviado para {} com status {}", usuario.email(), response);
//        return response;
//    }

    private void armazenaNotificacao(AgendamentoDto agendamento) {
        NotificacaoDomain notificacao = new NotificacaoDomain();
        notificacao.setIdConsulta(agendamento.id());
        notificacao.setIdPaciente(agendamento.pacienteId());
        notificacao.setCanal(CanalEnum.PUSH);
        notificacao.setStatus(StatusNotificacaoEnum.ENVIADA);
        notificacao.setDataAgendamentoEnvio(agendamento.dataAgendamento());

        String msg = String.format(
                "Olá! Você tem uma %s de %s com status %s marcada para o dia %s. Consulte os detalhes no aplicativo.",
                agendamento.tipoAtendimento().toLowerCase(),
                agendamento.especialidade().toUpperCase(),
                agendamento.status(),
                agendamento.dataAgendamento().format(FORMATTER)
        );

        notificacao.setMensagem(msg);
        inserirNotificacaoUseCase.inserir(notificacao);

        logger.info("Notificação salva no banco para pacienteId={} | consultaId={}", agendamento.pacienteId(), agendamento.id());
    }
}

