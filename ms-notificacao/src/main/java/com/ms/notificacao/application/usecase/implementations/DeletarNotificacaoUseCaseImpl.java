package com.ms.notificacao.application.usecase.implementations;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.application.usecase.DeletarNotificacaoUseCase;
import com.ms.notificacao.domain.domainService.NotificacaoDomainService;
import com.ms.notificacao.domain.model.NotificacaoDomain;

public class DeletarNotificacaoUseCaseImpl implements DeletarNotificacaoUseCase {

    private final Notificacao notificacao;
    private final NotificacaoDomainService notificacaoDomainService;

    public DeletarNotificacaoUseCaseImpl(Notificacao notificacao,
                                         NotificacaoDomainService notificacaoDomainService) {
        this.notificacao = notificacao;
        this.notificacaoDomainService = notificacaoDomainService;
    }

    @Override
    public void deletar(Long id) {
        NotificacaoDomain domain = notificacaoDomainService.buscarNotificacaoPorId(id);
        notificacao.deletar(domain);
    }
}
