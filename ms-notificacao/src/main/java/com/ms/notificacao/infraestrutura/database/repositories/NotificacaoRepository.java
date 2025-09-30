package com.ms.notificacao.infraestrutura.database.repositories;

import com.ms.notificacao.infraestrutura.database.entities.NotificacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotificacaoRepository extends JpaRepository<NotificacaoEntity, Long>, JpaSpecificationExecutor<NotificacaoEntity> {

}
