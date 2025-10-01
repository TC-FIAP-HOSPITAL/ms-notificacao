package com.ms.notificacao.application.usecase.implementations;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.domain.domainService.NotificacaoDomainService;
import com.ms.notificacao.domain.enums.CanalEnum;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AtualizarNotificacaoUseCaseImplTest {

    @InjectMocks
    private AtualizarNotificacaoUseCaseImpl atualizarNotificacaoUseCase;

    @Mock
    private Notificacao notificacao;

    @Mock
    private NotificacaoDomainService notificacaoDomainService;

    @Test
    void atualizar_sucesso() {
        NotificacaoDomain notificacaoDomain = new NotificacaoDomain();
        notificacaoDomain.setIdNotificacao(1L);
        notificacaoDomain.setMensagem("Teste");
        notificacaoDomain.setCanal(CanalEnum.EMAIL);
        notificacaoDomain.setDataAgendamentoEnvio(OffsetDateTime.now());

        when(notificacaoDomainService.buscarNotificacaoPorId(1L)).thenReturn(notificacaoDomain);
        doNothing().when(notificacao).salvar(any());

        atualizarNotificacaoUseCase.atualizar(1L, notificacaoDomain);

        verify(notificacao, times(1)).salvar(notificacaoDomain);
        verify(notificacaoDomainService, times(1)).buscarNotificacaoPorId(1L);
    }
}
