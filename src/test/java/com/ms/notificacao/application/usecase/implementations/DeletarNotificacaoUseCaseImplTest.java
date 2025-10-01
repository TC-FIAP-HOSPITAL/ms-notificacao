package com.ms.notificacao.application.usecase.implementations;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.domain.domainService.NotificacaoDomainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeletarNotificacaoUseCaseImplTest {

    @InjectMocks
    private DeletarNotificacaoUseCaseImpl deletarNotificacaoUseCase;

    @Mock
    private Notificacao notificacao;

    @Mock
    private NotificacaoDomainService notificacaoDomainService;

    @Test
    void deletar_sucesso() {
        doNothing().when(notificacao).deletar(any());
        deletarNotificacaoUseCase.deletar(1L);
        verify(notificacao, times(1)).deletar(any());
    }
}
