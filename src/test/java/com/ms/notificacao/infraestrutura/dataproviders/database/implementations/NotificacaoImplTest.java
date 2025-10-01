package com.ms.notificacao.infraestrutura.dataproviders.database.implementations;

import com.ms.notificacao.domain.model.NotificacaoDomain;
import com.ms.notificacao.infraestrutura.database.entities.NotificacaoEntity;
import com.ms.notificacao.infraestrutura.database.implementations.NotificacaoImpl;
import com.ms.notificacao.infraestrutura.database.repositories.NotificacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificacaoImplTest {

    @InjectMocks
    private NotificacaoImpl notificacao;

    @Mock
    private NotificacaoRepository notificacaoRepository;

    @Test
    void deletar_sucesso() {
        NotificacaoEntity notificacaoEntity = new NotificacaoEntity();
        doNothing().when(notificacaoRepository).delete(notificacaoEntity);
        notificacao.deletar(new NotificacaoDomain());
        verify(notificacaoRepository, times(1)).delete(notificacaoEntity);
    }

    @Test
    void salvar_sucesso() {
        NotificacaoEntity notificacaoEntity = new NotificacaoEntity();
        when(notificacaoRepository.save(any())).thenReturn(notificacaoEntity);
        notificacao.salvar(new NotificacaoDomain());
        verify(notificacaoRepository, times(1)).save(any());
    }

    @Test
    void buscarPorId_sucesso() {
        NotificacaoEntity notificacaoEntity = new NotificacaoEntity();
        when(notificacaoRepository.findById(1L)).thenReturn(Optional.of(notificacaoEntity));
        notificacao.buscarPorId(1L);
        verify(notificacaoRepository, times(1)).findById(1L);
    }

    @Test
    void buscar_sucesso() {
        NotificacaoEntity notificacaoEntity = new NotificacaoEntity();
        when(notificacaoRepository.findAll(any(Specification.class))).thenReturn(List.of(notificacaoEntity));
        notificacao.buscar(1L, 1L, 1L);
        verify(notificacaoRepository, times(1)).findAll(any(Specification.class));
    }
}
