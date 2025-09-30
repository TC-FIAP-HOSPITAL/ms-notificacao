package com.ms.notificacao.application.gateways;

import com.ms.notificacao.domain.model.NotificacaoDomain;

import java.util.List;
import java.util.Optional;

public interface Notificacao {

    void deletar(NotificacaoDomain notificacaoDomain);

    void salvar(NotificacaoDomain notificacaoDomain);

    Optional<NotificacaoDomain> buscarPorId(Long id);

    List<NotificacaoDomain> buscar(Long idPaciente, Long idConsulta, Long idNotificacao);
}
