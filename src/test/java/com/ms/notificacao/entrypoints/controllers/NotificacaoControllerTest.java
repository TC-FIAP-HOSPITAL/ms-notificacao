package com.ms.notificacao.entrypoints.controllers;

import com.fiap.ms.notificacaoDomain.gen.model.NotificacaoAtualizarRequestDto;
import com.fiap.ms.notificacaoDomain.gen.model.NotificacaoRequestDto;
import com.ms.notificacao.application.usecase.AtualizarNotificacaoUseCase;
import com.ms.notificacao.application.usecase.BuscarNotificacaoUseCase;
import com.ms.notificacao.application.usecase.DeletarNotificacaoUseCase;
import com.ms.notificacao.application.usecase.InserirNotificacaoUseCase;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificacaoControllerTest {

    @InjectMocks
    private NotificacaoController notificacaoController;

    @Mock
    private AtualizarNotificacaoUseCase atualizarNotificacaoUseCase;

    @Mock
    private BuscarNotificacaoUseCase buscarNotificacaoUseCase;

    @Mock
    private InserirNotificacaoUseCase inserirNotificacaoUseCase;

    @Mock
    private DeletarNotificacaoUseCase deletarNotificacaoUseCase;

    @Test
    void _atualizarNotificacao_sucesso() {
        doNothing().when(atualizarNotificacaoUseCase).atualizar(anyLong(), any());
        notificacaoController._atualizarNotificacao(1L, new NotificacaoAtualizarRequestDto());
        verify(atualizarNotificacaoUseCase, times(1)).atualizar(anyLong(), any());
    }

    @Test
    void _buscarNotificacoes_sucesso(){
        when(buscarNotificacaoUseCase.buscar(anyLong(), anyLong(), anyLong())).thenReturn(List.of(new NotificacaoDomain()));
        notificacaoController._buscarNotificacoes(1L, 1L, 1L);
        verify(buscarNotificacaoUseCase, times(1)).buscar(anyLong(), anyLong(), anyLong());
    }

    @Test
    void _criarNotificacao_sucesso(){
        doNothing().when(inserirNotificacaoUseCase).inserir(any());
        notificacaoController._criarNotificacao(new NotificacaoRequestDto());
        verify(inserirNotificacaoUseCase, times(1)).inserir(any());
    }

    @Test
    void _removerNotificacao_sucesso() {
        doNothing().when(deletarNotificacaoUseCase).deletar(anyLong());
        notificacaoController._removerNotificacao(1L);
        verify(deletarNotificacaoUseCase, times(1)).deletar(anyLong());
    }
}
