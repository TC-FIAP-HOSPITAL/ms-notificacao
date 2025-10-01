package com.ms.notificacao.infraestrutura.dataproviders.database.specification;

import com.ms.notificacao.infraestrutura.database.entities.NotificacaoEntity;
import com.ms.notificacao.infraestrutura.database.specification.NotificacaoSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificacaoSpecificationTest {

    @Mock
    private Root<NotificacaoEntity> root;
    @Mock
    private CriteriaQuery<?> query;
    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Test
    void filtrar_ComTodosParametros_DeveCriarSpecification() {
        Long idPaciente = 1L;
        Long idConsulta = 2L;
        Long idNotificacao = 3L;
        Specification<NotificacaoEntity> spec = NotificacaoSpecification.filtrar(idPaciente, idConsulta, idNotificacao);

        assertNotNull(spec, "A especificação não deveria ser nula");
        spec.toPredicate(root, query, criteriaBuilder);
    }

    @Test
    void filtrar_ApenasComIdPaciente_DeveCriarSpecification() {
        Long idPaciente = 1L;
        Specification<NotificacaoEntity> spec = NotificacaoSpecification.filtrar(idPaciente, null, null);

        assertNotNull(spec, "A especificação não deveria ser nula");
        spec.toPredicate(root, query, criteriaBuilder);
    }

    @Test
    void filtrar_ApenasComIdConsulta_DeveCriarSpecification() {
        Long idConsulta = 2L;
        Specification<NotificacaoEntity> spec = NotificacaoSpecification.filtrar(null, idConsulta, null);

        assertNotNull(spec, "A especificação não deveria ser nula");
        spec.toPredicate(root, query, criteriaBuilder);
    }

    @Test
    void filtrar_ApenasComIdNotificacao_DeveCriarSpecification() {
        Long idNotificacao = 3L;
        Specification<NotificacaoEntity> spec = NotificacaoSpecification.filtrar(null, null, idNotificacao);

        assertNotNull(spec, "A especificação não deveria ser nula");
        spec.toPredicate(root, query, criteriaBuilder);
    }

    @Test
    void filtrar_SemParametros_DeveCriarSpecification() {
        Specification<NotificacaoEntity> spec = NotificacaoSpecification.filtrar(null, null, null);

        assertNotNull(spec, "A especificação não deveria ser nula");
        spec.toPredicate(root, query, criteriaBuilder);
    }

    @Test
    void filtrar_ApenasComIdPaciente_DeveAplicarFiltroCorreto() {
        Long idPaciente = 1L;
        Specification<NotificacaoEntity> spec = NotificacaoSpecification.filtrar(idPaciente, null, null);

        spec.toPredicate(root, query, criteriaBuilder);

        verify(root, times(1)).get("idPaciente");
        verify(root, never()).get("idConsulta");
        verify(root, never()).get("idNotificacao");
    }
}