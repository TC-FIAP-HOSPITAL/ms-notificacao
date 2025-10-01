package com.ms.notificacao.application.usecase.implementations;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.application.usecase.InserirNotificacaoUseCase;
import com.ms.notificacao.domain.model.NotificacaoDomain;
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
public class InserirNotificacaoUseCaseImplTest {

    @InjectMocks
    private InserirNotificacaoUseCaseImpl inserirNotificacaoUseCase;

    @Mock
    private Notificacao notificacao;

    @Test
    void inserir_sucesso() {
        NotificacaoDomain notificacaoDomain = new NotificacaoDomain();
        notificacaoDomain.setMensagem("notificacao");
        doNothing().when(notificacao).salvar(any());
        inserirNotificacaoUseCase.inserir(notificacaoDomain);
        verify(notificacao, times(1)).salvar(any());
    }
}
