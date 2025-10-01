package com.ms.notificacao.domain.domainService.implementations;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.domain.exception.ObjetoNaoExisteException;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificacaoDomainServiceImplTest {

    @InjectMocks
    private NotificacaoDomainServiceImpl notificacaoDomainService;

    @Mock
    private Notificacao notificacao;

    @Test
    void buscarNotificacaoPorId_sucesso() {
        when(notificacao.buscarPorId(anyLong())).thenReturn(Optional.of(new NotificacaoDomain()));
        notificacaoDomainService.buscarNotificacaoPorId(1L);
        verify(notificacao, times(1)).buscarPorId(anyLong());
    }

    @Test
    void buscarNotificacaoPorId_exception() {
        when(notificacao.buscarPorId(anyLong())).thenReturn(Optional.empty());
        assertThrows(ObjetoNaoExisteException.class, () -> notificacaoDomainService.buscarNotificacaoPorId(1L));
    }
}
