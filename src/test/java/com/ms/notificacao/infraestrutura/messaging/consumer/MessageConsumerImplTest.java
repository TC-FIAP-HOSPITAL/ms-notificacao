package com.ms.notificacao.infraestrutura.messaging.consumer;

import com.ms.notificacao.application.usecase.InserirNotificacaoUseCase;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import com.ms.notificacao.infraestrutura.clients.brevo.BrevoEmailClient;
import com.ms.notificacao.infraestrutura.clients.usuario.UsuarioClientImpl;
import com.ms.notificacao.infraestrutura.messaging.AgendamentoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.support.converter.MessageConverter;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageConsumerImplTest {

    @InjectMocks
    private InserirNotificacaoUseCase inserirNotificacaoUseCase;

    @Mock
    private MessageConverter messageConverter;

    @Mock
    private UsuarioClientImpl usuarioClient;

    @Mock
    private BrevoEmailClient brevoEmailClient;

    @Mock
    private MessageConsumerImpl messageConsumer;

    @BeforeEach
    void setUp() {
        inserirNotificacaoUseCase = mock(InserirNotificacaoUseCase.class);
        messageConverter = mock(MessageConverter.class);
        usuarioClient = mock(UsuarioClientImpl.class);
        brevoEmailClient = mock(BrevoEmailClient.class);

        messageConsumer = new MessageConsumerImpl(
                inserirNotificacaoUseCase,
                messageConverter,
                usuarioClient,
                brevoEmailClient
        );
    }

//    @Test
//    void testNotification_withToken_sendsEmailAndStoresNotification() {
//        // Arrange
//        String token = "Bearer token123";
//        AgendamentoDto agendamento = new AgendamentoDto(
//                1L, 100L, 200L,
//                OffsetDateTime.parse("2025-10-05T15:00:00Z"),
//                "Consulta", "Confirmada", "Cardiologia",
//                "Motivo teste", "Observações teste"
//        );
//
//        UsuarioDto usuario = new UsuarioDto("100", "João", "joao@email.com", "joaouser", "PACIENTE");
//        when(usuarioClient.buscaUsuarioID(agendamento.pacienteId(), token)).thenReturn(usuario);
//        when(brevoEmailClient.sendEmail(any(EmailRequestDto.class))).thenReturn(HttpStatus.OK.toString());
//
//        messageConsumer.notification(agendamento, token);
//
//        verify(usuarioClient, times(1)).buscaUsuarioID(agendamento.pacienteId(), token);
//
//        ArgumentCaptor<EmailRequestDto> emailCaptor = ArgumentCaptor.forClass(EmailRequestDto.class);
//        verify(brevoEmailClient).sendEmail(emailCaptor.capture());
//        EmailRequestDto capturedEmail = emailCaptor.getValue();
//        assertEquals("João", capturedEmail.to().get(0).name());
//        assertEquals("joao@email.com", capturedEmail.to().get(0).email());
//
//        ArgumentCaptor<NotificacaoDomain> notifCaptor = ArgumentCaptor.forClass(NotificacaoDomain.class);
//        verify(inserirNotificacaoUseCase).inserir(notifCaptor.capture());
//        NotificacaoDomain notif = notifCaptor.getValue();
//        assertEquals(agendamento.id(), notif.getIdConsulta());
//        assertEquals(CanalEnum.PUSH, notif.getCanal());
//        assertEquals(StatusNotificacaoEnum.ENVIADA, notif.getStatus());
//    }

    @Test
    void testNotification_withNullToken_onlyStoresNotification() {
        // Arrange
        AgendamentoDto agendamento = new AgendamentoDto(
                2L, 101L, 201L,
                OffsetDateTime.parse("2025-10-06T10:00:00Z"),
                "Retorno", "Confirmada", "Dermatologia",
                "Motivo teste 2", "Observações teste 2"
        );

        messageConsumer.notification(agendamento, null);

        verify(usuarioClient, never()).buscaUsuarioID(anyLong(), anyString());
        verify(brevoEmailClient, never()).sendEmail(any());

        ArgumentCaptor<NotificacaoDomain> notifCaptor = ArgumentCaptor.forClass(NotificacaoDomain.class);
        verify(inserirNotificacaoUseCase).inserir(notifCaptor.capture());
        NotificacaoDomain notif = notifCaptor.getValue();
        assertEquals(agendamento.id(), notif.getIdConsulta());
    }
}
