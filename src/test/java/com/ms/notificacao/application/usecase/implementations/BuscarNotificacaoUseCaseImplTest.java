package com.ms.notificacao.application.usecase.implementations;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarNotificacaoUseCaseImplTest {

    @InjectMocks
    private BuscarNotificacaoUseCaseImpl buscarNotificacaoUseCase;

    @Mock
    private Notificacao notificacao;

    @Test
    void buscar_sucesso() {
        when(notificacao.buscar(null, null, null)).thenReturn(List.of(new NotificacaoDomain()));
        buscarNotificacaoUseCase.buscar(null, null, null);
        verify(notificacao, times(1)).buscar(null, null, null);
    }
}
