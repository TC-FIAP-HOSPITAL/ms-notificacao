package com.ms.notificacao.application.usecase;

import com.ms.notificacao.domain.model.NotificacaoDomain;

public interface AtualizarNotificacaoUseCase {

    void atualizar(Long id, NotificacaoDomain domain);
}
