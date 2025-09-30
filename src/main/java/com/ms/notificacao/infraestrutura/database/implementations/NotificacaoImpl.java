package com.ms.notificacao.infraestrutura.database.implementations;

import com.ms.notificacao.application.gateways.Notificacao;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import com.ms.notificacao.infraestrutura.database.mapper.NotificacaoEntityMapper;
import com.ms.notificacao.infraestrutura.database.repositories.NotificacaoRepository;
import com.ms.notificacao.infraestrutura.database.specification.NotificacaoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificacaoImpl implements Notificacao {

    private final NotificacaoRepository notificacaoRepository;

    @Override
    public void deletar(NotificacaoDomain notificacaoDomain) {
        var entity = NotificacaoEntityMapper.INSTANCE.toNotificacaoEntity(notificacaoDomain);
        notificacaoRepository.delete(entity);
    }

    @Override
    public void salvar(NotificacaoDomain notificacaoDomain) {
        var entity = NotificacaoEntityMapper.INSTANCE.toNotificacaoEntity(notificacaoDomain);
        notificacaoRepository.save(entity);
    }

    @Override
    public Optional<NotificacaoDomain> buscarPorId(Long id) {
        var entity = notificacaoRepository.findById(id);
        return entity.map(NotificacaoEntityMapper.INSTANCE::toNotificacaoDomain);
    }

    @Override
    public List<NotificacaoDomain> buscar(Long idPaciente, Long idConsulta, Long idNotificacao) {
        var spec = NotificacaoSpecification.filtrar(idPaciente, idConsulta, idNotificacao);
        return notificacaoRepository.findAll(spec)
                .stream()
                .map(NotificacaoEntityMapper.INSTANCE::toNotificacaoDomain)
                .toList();
    }
}
