package com.ms.notificacao.application.usecase.implementations;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.application.usecase.InserirNotificacaoUseCase;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import com.ms.notificacao.domain.rules.ValidarCamposObrigatoriosRule;

public class InserirNotificacaoUseCaseImpl implements InserirNotificacaoUseCase {

    private final Notificacao notificacao;

    public InserirNotificacaoUseCaseImpl(Notificacao notificacao) {
        this.notificacao = notificacao;
    }

    @Override
    public void inserir(NotificacaoDomain domain) {
        ValidarCamposObrigatoriosRule.validarCamposObrigatorios(domain.getMensagem());
        notificacao.salvar(domain);
    }
}
