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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Component
public class MessageConsumerImpl implements MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumerImpl.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

    private final InserirNotificacaoUseCase inserirNotificacaoUseCase;
    private final UsuarioClientPort usuarioClientPort;
    private final MessageConverter messageConverter;
    private final EmailClient emailClient;

    public MessageConsumerImpl(InserirNotificacaoUseCase inserirNotificacaoUseCase,
                               UsuarioClientPort usuarioClientPort,
                               MessageConverter messageConverter,
                               EmailClient emailClient) {
        this.inserirNotificacaoUseCase = inserirNotificacaoUseCase;
        this.usuarioClientPort = usuarioClientPort;
        this.messageConverter = messageConverter;
        this.emailClient = emailClient;
    }

    @Override
    @RabbitListener(queues = "notificacao-queue")
    public void enviaNotificacaoByBroker(final Message message) {
        try {
            AgendamentoDto agendamento = (AgendamentoDto) messageConverter.fromMessage(message);
            if (Objects.isNull(agendamento)) {
                logger.warn("Mensagem recebida é nula — ignorando processamento.");
                return;
            }

            String token = extractToken(message);
            if (token == null) {
                logger.warn("Token de autorização ausente no header da mensagem — notificações podem falhar.");
            }

            logger.info("Recebida mensagem para pacienteId={} | consultaId={} | dataAgendamento={} | status={} | tipoAtendimento={}",
                    agendamento.pacienteId(),
                    agendamento.id(),
                    agendamento.dataAgendamento(),
                    agendamento.status(),
                    agendamento.tipoAtendimento()
            );

            // Busca usuário e envia notificação por e-mail
            UsuarioResponseDto usuario = usuarioClientPort.buscaUsuarioID(agendamento.pacienteId(), token);
            if (usuario != null && usuario.email() != null) {
                enviarEmail(agendamento, usuario);
            } else {
                logger.warn("Usuário não encontrado ou sem e-mail cadastrado. ID: {}", agendamento.pacienteId());
            }

            // Cria registro no banco
            criarNotificacao(agendamento);

        } catch (Exception ex) {
            logger.error("Erro ao processar mensagem da fila de notificação", ex);
        }
    }

    private String extractToken(Message message) {
        Object authHeader = message.getMessageProperties().getHeaders().get("Authorization");
        if (authHeader instanceof String auth) {
            return auth.startsWith("Bearer ") ? auth.substring(7) : auth;
        }
        return null;
    }

    private void enviarEmail(AgendamentoDto agendamento, UsuarioResponseDto usuario) {
        String dataFormatada = agendamento.dataAgendamento().format(FORMATTER);

        String conteudo = String.format(
                "Olá %s! Você tem uma %s de %s com status %s marcada para o dia %s. Consulte os detalhes no aplicativo.",
                usuario.name(),
                agendamento.tipoAtendimento().toLowerCase(),
                agendamento.especialidade().toUpperCase(),
                agendamento.status(),
                dataFormatada
        );

        EmailRequestDto emailRequest = new EmailRequestDto(
                new EmailRequestDto.Sender("SISTEMA AGENDAMENTO", "ghustavo516@gmail.com"),
                List.of(new EmailRequestDto.Recipient(usuario.email(), usuario.name())),
                agendamento.tipoAtendimento(),
                conteudo
        );

        emailClient.sendEmail(emailRequest);
        logger.info("E-mail enviado para {}", usuario.email());
    }

    private void criarNotificacao(AgendamentoDto agendamento) {
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
