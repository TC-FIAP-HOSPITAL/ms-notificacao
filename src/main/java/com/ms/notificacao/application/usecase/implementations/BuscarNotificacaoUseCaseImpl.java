package com.ms.notificacao.application.usecase.implementations;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.application.usecase.BuscarNotificacaoUseCase;
import com.ms.notificacao.domain.model.NotificacaoDomain;

import java.util.List;

public class BuscarNotificacaoUseCaseImpl implements BuscarNotificacaoUseCase {

    private final Notificacao notificacao;

    public BuscarNotificacaoUseCaseImpl(Notificacao notificacao) {
        this.notificacao = notificacao;
    }

    @Override
    public List<NotificacaoDomain> buscar(Long idPaciente, Long idConsulta, Long idNotificacao) {
        return notificacao.buscar(idPaciente, idConsulta, idNotificacao);
    }
}
