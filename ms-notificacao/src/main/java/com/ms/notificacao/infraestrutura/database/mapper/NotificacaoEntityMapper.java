package com.ms.notificacao.infraestrutura.database.mapper;

import com.ms.notificacao.domain.model.NotificacaoDomain;
import com.ms.notificacao.infraestrutura.database.entities.NotificacaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface NotificacaoEntityMapper {

    NotificacaoEntityMapper INSTANCE = Mappers.getMapper(NotificacaoEntityMapper.class);

    NotificacaoEntity toNotificacaoEntity(NotificacaoDomain notificacaoDomain);

    NotificacaoDomain toNotificacaoDomain(NotificacaoEntity notificacaoEntity);
}
