package com.ms.notificacao.entrypoints.controllers.mappers;

import com.fiap.ms.notificacaoDomain.gen.model.NotificacaoAtualizarRequestDto;
import com.fiap.ms.notificacaoDomain.gen.model.NotificacaoRequestDto;
import com.fiap.ms.notificacaoDomain.gen.model.NotificacaoResponseDto;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface NotificacaoDtoMapper {

    NotificacaoDtoMapper INSTANCE = Mappers.getMapper(NotificacaoDtoMapper.class);

    @Mapping(target = "idNotificacao", ignore = true)
    @Mapping(target = "status", ignore = true)
    NotificacaoDomain toDomain(NotificacaoRequestDto notificacaoRequestDto);

    @Mapping(target = "idNotificacao", ignore = true)
    @Mapping(target = "idPaciente", ignore = true)
    @Mapping(target = "idConsulta", ignore = true)
    @Mapping(target = "status", ignore = true)
    NotificacaoDomain toNotificacaoAtualizarDomain(NotificacaoAtualizarRequestDto notificacaoAtualizarRequestDto);

    NotificacaoResponseDto toNotificacaoResponseDto(NotificacaoDomain notificacaoDomain);

    List<NotificacaoResponseDto> toListDto(List<NotificacaoDomain> notificacaoDomains);
}
