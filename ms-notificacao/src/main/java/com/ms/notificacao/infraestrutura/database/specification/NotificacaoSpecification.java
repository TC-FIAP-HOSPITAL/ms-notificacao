package com.ms.notificacao.infraestrutura.database.specification;

import com.ms.notificacao.infraestrutura.database.entities.NotificacaoEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class NotificacaoSpecification {

    public static Specification<NotificacaoEntity> filtrar(Long idPaciente, Long idConsulta, Long idNotificacao) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(idPaciente != null) {
                predicates.add(criteriaBuilder.equal(root.get("idPaciente"), idPaciente));
            }

            if(idConsulta != null) {
                predicates.add(criteriaBuilder.equal(root.get("idConsulta"), idConsulta));
            }

            if(idNotificacao != null) {
                predicates.add(criteriaBuilder.equal(root.get("idNotificacao"), idNotificacao));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
