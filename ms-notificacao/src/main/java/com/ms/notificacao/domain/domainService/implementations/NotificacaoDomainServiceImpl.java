package com.ms.notificacao.domain.domainService.implementations;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.domain.domainService.NotificacaoDomainService;
import com.ms.notificacao.domain.exception.ObjetoNaoExisteException;
import com.ms.notificacao.domain.model.NotificacaoDomain;

public class NotificacaoDomainServiceImpl implements NotificacaoDomainService {

    private final Notificacao notificacao;

    public NotificacaoDomainServiceImpl(Notificacao notificacao) {
        this.notificacao = notificacao;
    }

    @Override
    public NotificacaoDomain buscarNotificacaoPorId(Long id) {
        return notificacao.buscarPorId(id)
                .orElseThrow(() -> new ObjetoNaoExisteException("Notificação não está cadastrado."));
    }
}
