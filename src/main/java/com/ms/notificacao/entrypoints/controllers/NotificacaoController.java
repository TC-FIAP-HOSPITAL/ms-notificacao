package com.ms.notificacao.entrypoints.controllers;

import com.fiap.ms.notificacaoDomain.NotificacoesApi;
import com.fiap.ms.notificacaoDomain.gen.model.NotificacaoAtualizarRequestDto;
import com.fiap.ms.notificacaoDomain.gen.model.NotificacaoRequestDto;
import com.fiap.ms.notificacaoDomain.gen.model.NotificacaoResponseDto;
import com.ms.notificacao.application.usecase.AtualizarNotificacaoUseCase;
import com.ms.notificacao.application.usecase.BuscarNotificacaoUseCase;
import com.ms.notificacao.application.usecase.DeletarNotificacaoUseCase;
import com.ms.notificacao.application.usecase.InserirNotificacaoUseCase;
import com.ms.notificacao.domain.model.NotificacaoDomain;
import com.ms.notificacao.entrypoints.controllers.presenter.NotificacaoPresenter;
import com.ms.notificacao.infraestrutura.config.security.Role;
import com.ms.notificacao.infraestrutura.config.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
public class NotificacaoController implements NotificacoesApi {

    private final AtualizarNotificacaoUseCase atualizarNotificacaoUseCase;
    private final BuscarNotificacaoUseCase buscarNotificacaoUseCase;
    private final InserirNotificacaoUseCase inserirNotificacaoUseCase;
    private final DeletarNotificacaoUseCase deletarNotificacaoUseCase;
    private final SecurityUtil securityUtil;

    public NotificacaoController(AtualizarNotificacaoUseCase atualizarNotificacaoUseCase,
                                 BuscarNotificacaoUseCase buscarNotificacaoUseCase,
                                 InserirNotificacaoUseCase inserirNotificacaoUseCase,
                                 DeletarNotificacaoUseCase deletarNotificacaoUseCase,
                                 SecurityUtil securityUtil) {
        this.atualizarNotificacaoUseCase = atualizarNotificacaoUseCase;
        this.buscarNotificacaoUseCase = buscarNotificacaoUseCase;
        this.inserirNotificacaoUseCase = inserirNotificacaoUseCase;
        this.deletarNotificacaoUseCase = deletarNotificacaoUseCase;
        this.securityUtil = securityUtil;
    }

    @Override
    public ResponseEntity<Void> _atualizarNotificacao(Long idNotificacao, NotificacaoAtualizarRequestDto notificacaoAtualizarRequestDto) {
        var domain = NotificacaoPresenter.toDomain(notificacaoAtualizarRequestDto);
        atualizarNotificacaoUseCase.atualizar(idNotificacao, domain);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<NotificacaoResponseDto>> _buscarNotificacoes(Long idPaciente, Long idConsulta, Long idNotificacao) {
        List<NotificacaoDomain> domains = buscarNotificacaoUseCase.buscar(idPaciente, idConsulta, idNotificacao);
        List<NotificacaoResponseDto> dtos = NotificacaoPresenter.toDtoList(domains);
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<Void> _criarNotificacao(NotificacaoRequestDto notificacaoRequestDto) {
        var domain = NotificacaoPresenter.toDomain(notificacaoRequestDto);
        inserirNotificacaoUseCase.inserir(domain);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> _removerNotificacao(Long idNotificacao) {
        deletarNotificacaoUseCase.deletar(idNotificacao);
        return ResponseEntity.noContent().build();
    }
}
