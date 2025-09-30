package com.ms.notificacao.application.usecase.implementations;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.application.usecase.AtualizarNotificacaoUseCase;
import com.ms.notificacao.domain.domainService.NotificacaoDomainService;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import com.ms.notificacao.domain.rules.ValidarCamposObrigatoriosRule;

public class AtualizarNotificacaoUseCaseImpl implements AtualizarNotificacaoUseCase {

    private final Notificacao notificacao;
    private final NotificacaoDomainService notificacaoDomainService;

    public AtualizarNotificacaoUseCaseImpl(Notificacao notificacao,
                                           NotificacaoDomainService notificacaoDomainService) {
        this.notificacao = notificacao;
        this.notificacaoDomainService = notificacaoDomainService;
    }

    @Override
    public void atualizar(Long id, NotificacaoDomain domain) {
        ValidarCamposObrigatoriosRule.validarCamposObrigatorios(domain.getMensagem());

        NotificacaoDomain notificacaoDomain = notificacaoDomainService.buscarNotificacaoPorId(id);

        notificacaoDomain.setMensagem(domain.getMensagem());
        notificacaoDomain.setCanal(domain.getCanal());
        notificacaoDomain.setDataAgendamentoEnvio(domain.getDataAgendamentoEnvio());

        notificacao.salvar(notificacaoDomain);
    }
}
