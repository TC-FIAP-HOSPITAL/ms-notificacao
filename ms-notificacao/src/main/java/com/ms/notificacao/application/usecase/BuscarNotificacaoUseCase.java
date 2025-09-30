package com.ms.notificacao.application.usecase;

import com.ms.notificacao.domain.model.NotificacaoDomain;

import java.util.List;

public interface BuscarNotificacaoUseCase {

    List<NotificacaoDomain> buscar(Long idPaciente, Long idConsulta, Long idNotificacao);
}
