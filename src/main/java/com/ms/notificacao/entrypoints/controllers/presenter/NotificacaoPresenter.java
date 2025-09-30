package com.ms.notificacao.entrypoints.controllers.presenter;

import com.fiap.ms.notificacaoDomain.gen.model.NotificacaoAtualizarRequestDto;
import com.fiap.ms.notificacaoDomain.gen.model.NotificacaoRequestDto;
import com.fiap.ms.notificacaoDomain.gen.model.NotificacaoResponseDto;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import com.ms.notificacao.entrypoints.controllers.mappers.NotificacaoDtoMapper;

import java.util.List;

public class NotificacaoPresenter {

    public static NotificacaoDomain toDomain(NotificacaoRequestDto dto) {
        return NotificacaoDtoMapper.INSTANCE.toDomain(dto);
    }

    public static List<NotificacaoResponseDto> toDtoList(List<NotificacaoDomain> domains) {
        return NotificacaoDtoMapper.INSTANCE.toListDto(domains);
    }

    public static NotificacaoDomain toDomain(NotificacaoAtualizarRequestDto dto) {
        return NotificacaoDtoMapper.INSTANCE.toNotificacaoAtualizarDomain(dto);
    }
}
